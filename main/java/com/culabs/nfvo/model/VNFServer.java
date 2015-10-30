package com.culabs.nfvo.model;

import com.culabs.nfvo.model.nsd.Completions;
import com.culabs.nfvo.model.nsd.Dependency;

/**
 * 
 * @ClassName: VNFInstance
 * @Description: TODO
 * @author 
 * @date 2015年5月5日 上午11:05:23
 * @version 1.0
 */
public class VNFServer
{
	private String serverId;
	private String parent;
	private String nsinstanceId;
	private String serverName;
	private String vnfName;
	private String mac;
	private String ip;
	private String poolId;
	private String vipId;
	private String memberId;
	/**
	 * 0: service not start successfully 1: service start successfully
	 */
	private String status = "-1";
	private String vnfId;
	private Integer instancesNum;
	private Integer sequence;
	private String volumeId;
	private Integer volumeSize;
	private String imageRef;
	private final VMFlavor flavor = new VMFlavor();;
	private String usedPort;
	private Dependency dependency;
	private Completions completions;

	public VNFServer()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public VNFServer(String serverId, Dependency dependency)
	{
		super();
		this.serverId = serverId;
		this.dependency = dependency;
	}

	public VNFServer(String serverId, String vnfId, Dependency dependency)
	{
		super();
		this.serverId = serverId;
		this.vnfId = vnfId;
		this.dependency = dependency;
	}

	public String getServerId()
	{
		return serverId;
	}

	public void setServerId(String serverId)
	{
		this.serverId = serverId;
	}

	public String getParent()
	{
		return parent;
	}

	public void setParent(String parent)
	{
		this.parent = parent;
	}

	public String getNsinstanceId()
	{
		return nsinstanceId;
	}

	public void setNsinstanceId(String nsinstanceId)
	{
		this.nsinstanceId = nsinstanceId;
	}

	public String getServerName()
	{
		return serverName;
	}

	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}

	public String getMac()
	{
		return mac;
	}

	public void setMac(String mac)
	{
		this.mac = mac;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getPoolId()
	{
		return poolId;
	}

	public void setPoolId(String poolId)
	{
		this.poolId = poolId;
	}

	public String getVipId()
	{
		return vipId;
	}

	public void setVipId(String vipId)
	{
		this.vipId = vipId;
	}

	public String getMemberId()
	{
		return memberId;
	}

	public void setMemberId(String memberId)
	{
		this.memberId = memberId;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getVnfId()
	{
		return vnfId;
	}

	public void setVnfId(String vnfId)
	{
		this.vnfId = vnfId;
	}

	public Integer getInstancesNum()
	{
		return instancesNum;
	}

	public void setInstancesNum(Integer instancesNum)
	{
		this.instancesNum = instancesNum;
	}

	public Integer getSequence()
	{
		return sequence;
	}

	public void setSequence(Integer sequence)
	{
		this.sequence = sequence;
	}

	public String getVolumeId()
	{
		return volumeId;
	}

	public void setVolumeId(String volumeId)
	{
		this.volumeId = volumeId;
	}

	public Integer getVolumeSize()
	{
		return volumeSize;
	}

	public void setVolumeSize(Integer volumeSize)
	{
		this.volumeSize = volumeSize;
	}

	public String getImageRef()
	{
		return imageRef;
	}

	public void setImageRef(String imageRef)
	{
		this.imageRef = imageRef;
	}

	public VMFlavor getFlavor()
	{
		return flavor;
	}

	public String getUsedPort()
	{
		return usedPort;
	}

	public void setUsedPort(String usedPort)
	{
		this.usedPort = usedPort;
	}

	public Dependency getDependency()
	{
		return dependency;
	}

	public void setDependency(Dependency dependency)
	{
		this.dependency = dependency;
	}

	public Completions getCompletions()
	{
		return completions;
	}

	public void setCompletions(Completions completions)
	{
		this.completions = completions;
	}
	

	public String getVnfName() {
		return vnfName;
	}

	public void setVnfName(String vnfName) {
		this.vnfName = vnfName;
	}

	@Override
	public String toString()
	{
		return "VNFServer [serverId=" + serverId + ", parent=" + parent + ", nsinstanceId=" + nsinstanceId
				+ ", serverName=" + serverName + ", vnfName=" + vnfName + ", ip=" + ip + ", poolId="
				+ poolId + ", vipId=" + vipId + ", memberId=" + memberId + ", mac=" + mac + ", status=" 
				+ status + ", vnfId=" + vnfId + ", instancesNum=" + instancesNum + ", sequence=" + sequence 
				+ ", volumeId=" + volumeId + ", volumeSize=" + volumeSize + ", imageRef=" + imageRef 
				+ ", flavor=" + flavor	+ ", dependency.source=" + dependency.getSource() + ", dependency.target=" 
				+ dependency.getTarget() + ", completions=" + completions + "]";
	}

	public static class VMFlavor
	{
		private String flavorRef;
		private String storage;
		private String cpu;
		private String memory;

		public String getFlavorRef()
		{
			return flavorRef;
		}

		public void setFlavorRef(String flavorRef)
		{
			this.flavorRef = flavorRef;
		}

		public String getStorage()
		{
			return storage;
		}

		public void setStorage(String storage)
		{
			this.storage = storage;
		}

		public String getCpu()
		{
			return cpu;
		}

		public void setCpu(String cpu)
		{
			this.cpu = cpu;
		}

		public String getMemory()
		{
			return memory;
		}

		public void setMemory(String memory)
		{
			this.memory = memory;
		}

		@Override
		public String toString()
		{
			return "VMFlavor [flavorRef=" + flavorRef + ", storage=" + storage + ", cpu=" + cpu + ", memory=" + memory
					+ "]";
		}
	}

}
