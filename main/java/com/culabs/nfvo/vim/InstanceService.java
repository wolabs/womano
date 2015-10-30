package com.culabs.nfvo.vim;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.config.RestConfigHandler;
import com.culabs.nfvo.core.DBOperator;
import com.culabs.nfvo.model.KVPair;
import com.culabs.nfvo.model.RestUrl;
import com.culabs.nfvo.model.db.DBInstance;
import com.culabs.nfvo.model.db.DBInstanceServer;
import com.culabs.nfvo.model.db.DBNetwork;
import com.culabs.nfvo.model.db.DBServer;
import com.culabs.nfvo.util.NFVOUtils;

public class InstanceService extends Thread{

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceService.class);
    private static final int ERROR = 500;
    private static final int SUCCESS = 200;
    
    public InstanceService(){
    }
    
    @Override
    public void run() {
        createInstance();
    }
    
    public String createInstance() {
        RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createInstance");
        KVPair<String, Object> kvPair = restUrl.getPairs();
        JSONObject result = new JSONObject();

        try {
            String tenantId = (String) kvPair.getPairVal("tenant_id");
            String maxCount = (String) kvPair.getPairVal("max_count"); 
            String minCount = (String) kvPair.getPairVal("min_count"); 
            String securityGroupsName = (String) kvPair.getPairVal("security_groups_name");
            String networkConfigId = (String) kvPair.getPairVal("network_uuid"); 
            
            //初始化instance表，状态置为initial
            String instanceId = storeInstance((String) kvPair.getPairVal("instance_name"), (String) kvPair.getPairVal("template_id"));
            
            //创建ellis/hss/scsf/is-scsf/xdms虚拟机，并将云主机与instance关联
            LOGGER.info("start creating ellis");
            
            String ellisId = createVM(restUrl, tenantId, (String)kvPair.getPairVal("ellis_imageRef"), (String)kvPair.getPairVal("ellis_flavorRef"), 
                             (String) kvPair.getPairVal("ellis_name"), maxCount, minCount, securityGroupsName, networkConfigId);
            storeInstanceServer(instanceId, ellisId);
            
            LOGGER.info("ellis has been created, sleep 30 seconds, waiting for creating xdms");
            sleep(60*1000);
            LOGGER.info("start creating xdms");
            
            String xdmsId = createVM(restUrl, tenantId, (String)kvPair.getPairVal("xdms_imageRef"), (String)kvPair.getPairVal("xdms_flavorRef"), 
                             (String) kvPair.getPairVal("xdms_name"), maxCount, minCount, securityGroupsName, networkConfigId);
            storeInstanceServer(instanceId, xdmsId);
            
            LOGGER.info("xdms has been created, sleep 30 seconds, waiting for creating hss");
            sleep(60*1000);
            LOGGER.info("start creating hss");
            
            String hssId = createVM(restUrl, tenantId, (String)kvPair.getPairVal("hss_imageRef"), (String)kvPair.getPairVal("hss_flavorRef"), 
                             (String) kvPair.getPairVal("hss_name"), maxCount, minCount, securityGroupsName, networkConfigId);
            storeInstanceServer(instanceId, hssId);
            
            LOGGER.info("hss has been created, sleep 30 seconds, waiting for creating i/s-scsf");
            sleep(60*1000); 
            LOGGER.info("start creating  i/s-scsf");
            
            String isscsfId = createVM(restUrl, tenantId, (String)kvPair.getPairVal("isscsf_imageRef"), (String)kvPair.getPairVal("isscsf_flavorRef"), 
                    (String) kvPair.getPairVal("isscsf_name"), maxCount, minCount, securityGroupsName, networkConfigId);
            storeInstanceServer(instanceId, isscsfId);
            
            LOGGER.info("i/s-scsf has been created, sleep 30 seconds, waiting for creating p-scsf");
            sleep(60*1000);
            LOGGER.info("start creating p-scsf");
            
            String pscsfId = createVM(restUrl, tenantId, (String)kvPair.getPairVal("pscsf_imageRef"), (String)kvPair.getPairVal("pscsf_flavorRef"), 
                             (String) kvPair.getPairVal("pscsf_name"), maxCount, minCount, securityGroupsName, networkConfigId);
            storeInstanceServer(instanceId, pscsfId);
            
            LOGGER.info("all the vm has been created successfully!");
            
            //更新instance状态为ready,表示云主机创建完成，等待服务启动
            DBInstance initialInstance = DBOperator.queryInstanceById(instanceId);
            initialInstance.setStatus("ready");
            DBOperator.updateInstance(initialInstance);
            
            result.put(SUCCESS, "createInstance success,instance current status is ready,waiting for service start...");
        } catch (Exception e) {
            e.printStackTrace();
            result.put(ERROR, e.getMessage());
        }
        return result.toString();
    }
    
    private String storeInstance(String instanceName, String templateId) throws Exception{
        DBInstance dbInstance = new DBInstance();
        dbInstance.setInstance_name(instanceName);
        dbInstance.setStatus("initial");
        dbInstance.setDescription("vIMS 实例化");
        dbInstance.setCreate_time(new Date());
        dbInstance.setTemplate_id(templateId);
        String instanceID = UUID.randomUUID().toString();
        dbInstance.setInstance_id(instanceID);
        DBOperator.createInstance(dbInstance);
        return instanceID;
    }

    
    private void storeInstanceServer(String instanceId, String serverId) throws Exception{
        if (NFVOUtils.isEmpty(serverId))
        {
            throw new Exception("create server failed: serverId  empty!");
        }
        if (NFVOUtils.isEmpty(serverId) || NFVOUtils.isEmpty(instanceId))
        {
            throw new Exception("create server failed: instanceID or serverId can not be empty!");
        }
        DBInstanceServer dbInstanceServer = new DBInstanceServer();
        dbInstanceServer.setInstance_id(instanceId);
        dbInstanceServer.setServer_id(serverId);
        DBOperator.createInstanceServer(dbInstanceServer);
    }

    private String createVM(RestUrl restUrl, String tenantId, String imageRef, String flavorRef, String name, String maxCount, 
            String minCount, String securityGroupsName, String networkConfigId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tenant_id", tenantId);
            jsonObject.put("imageRef", imageRef);
            jsonObject.put("flavorRef", flavorRef);
            jsonObject.put("name", name);
            jsonObject.put("max_count", maxCount);
            jsonObject.put("min_count", minCount);
            Map<String,Object> networkMap = new HashMap<String,Object>();
            String networkUUID = null;
            if (!NFVOUtils.isEmpty(networkConfigId)) 
            {
                networkUUID = networkConfigId;
                networkMap.put("uuid", networkConfigId);
            }
            else 
            {
                List<DBNetwork> networkList = DBOperator.queryNetworkList();
                networkUUID = networkList.get(0).getNetwork_id();
                networkMap.put("uuid", networkUUID);
            }
            JSONArray networkArray = new JSONArray();
            networkArray.add(networkMap);
            jsonObject.put("networks", networkArray);
            Map<String,Object> securityGroupMap = new HashMap<String,Object>();
            securityGroupMap.put("name", securityGroupsName);
            JSONArray securityGroupArray = new JSONArray();
            securityGroupArray.add(securityGroupMap);
            jsonObject.put("security_groups", securityGroupArray);
            JSONObject outJsonObject = new JSONObject();
            outJsonObject.put("server", jsonObject);
            restUrl.setReqjson(outJsonObject.toString());            
            HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
            Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("server");
            if (NFVOUtils.isEmpty(secondLevelMap)) {
                LOGGER.error("createServer error :" + respMap.get("NeutronError"));
                return null;
            }

            DBServer dbServer = new DBServer();
            dbServer.setName(name);
            dbServer.setNetworks_uuid(networkUUID);
            dbServer.setTenant_id(tenantId);
            dbServer.setSecurity_groups_name(securityGroupsName);
            dbServer.setFlavor_id(flavorRef);
            dbServer.setServer_id((String) secondLevelMap.get("id"));
            dbServer.setDisk_config((String) secondLevelMap.get("OS-DCF:diskConfig"));
            dbServer.setCreate_time(new Date());
            DBOperator.createServer(dbServer);
            return dbServer.getServer_id();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
