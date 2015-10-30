/*********************** VPC & INSTANCE**************************/
/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/28 19:05:35                           */
/*==============================================================*/


drop table if exists acl_rule;
drop table if exists acl_subnet;
drop table if exists acl;
drop table if exists routing_rule;
drop table if exists subnet_routing_table;
drop table if exists port;
drop table if exists vpn_connection_point;
drop table if exists subnet;
drop table if exists nat;
drop table if exists public_ip;
drop table if exists public_ip_resource;
drop table if exists network;
drop table if exists instance_server;
drop table if exists instance;
drop table if exists server;
drop table if exists routing_table;
drop table if exists gw_router;
drop table if exists vpc;
drop table if exists tenant;
drop table if exists hypervisor;

/*==============================================================*/
/* Table: acl                                                   */
/*==============================================================*/
create table acl
(
   acl_id               varchar(64) not null,
   name                 varchar(64),
   vpc_id               varchar(64),
   tenant_id            varchar(64) comment '租户编码',
   status               varchar(64),
   primary key (acl_id)
);

alter table acl comment '访问控制表';

/*==============================================================*/
/* Table: acl_rule                                              */
/*==============================================================*/
create table acl_rule
(
   acl_rule_id          varchar(64) not null,
   name                 varchar(64),
   tenant_id            varchar(64),
   acl_id               varchar(64),
   direction            varchar(64),
   port_range_min       varchar(16),
   port_range_max       varchar(16),
   protocol             varchar(16),
   priority             varchar(16),
   prefix               varchar(64),
   strategy             varchar(64),
   primary key (acl_rule_id)
);

alter table acl_rule comment 'acl规则表';

/*==============================================================*/
/* Table: acl_subnet                                            */
/*==============================================================*/
create table acl_subnet
(
   acl_subnet_id        varchar(64) not null,
   acl_id               varchar(64),
   tenant_id            varchar(64),
   subnet_id            varchar(64),
   name                 varchar(64),
   status               varchar(64),
   primary key (acl_subnet_id)
);

alter table acl_subnet comment 'acl关联子网';

/*==============================================================*/
/* Table: gw_router                                             */
/*==============================================================*/
create table gw_router
(
   gw_router_id         varchar(64) not null,
   tenant_id            varchar(64) comment '租户编码',
   name                 varchar(64) comment '路由名称',
   vpc_id               varchar(64),
   admin_state_up       boolean comment '是否启用管理员状态',
   status               varchar(64) comment '路由状态，是否激活',
   bandwidth_tx         int comment '网关路由缓存上行带宽',
   bandwidth_rx         int comment '网关路由缓存下行带宽',
   router_type          varchar(64) comment '路由类型',
   primary key (gw_router_id)
);

alter table gw_router comment '路由信息表';

/*==============================================================*/
/* Table: nat                                                   */
/*==============================================================*/
create table nat
(
   nat_id               varchar(64) not null,
   public_ip_id         varchar(64) comment '创建ip的编号',
   public_ip            varchar(64) comment 'public ip',
   tenant_id            varchar(64),
   type                 varchar(16) comment '映射类型dnat,snat',
   igw_router_id        varchar(64),
   protocol             varchar(16) comment '协议类型',
   fixed_ip             varchar(64) comment '虚拟机ip',
   source_port          varchar(16) comment '源地址端口',
   destination_port     varchar(16) comment '目标地址端口',
   status               varchar(16),
   primary key (nat_id)
);

alter table nat comment '地址转换表';

/*==============================================================*/
/* Table: network                                               */
/*==============================================================*/
create table network
(
   network_id           varchar(64) not null,
   name                 varchar(64),
   shared               boolean,
   tenant_id            varchar(64),
   admin_state_up       boolean,
   router_external      boolean,
   subnets              varchar(128),
   status               varchar(16),
   provider_segmentation_id varchar(64),
   provider_physical_network varchar(64),
   provider_network_type varchar(64),
   primary key (network_id)
);

alter table network comment 'vpc网络';

/*==============================================================*/
/* Table: port                                                  */
/*==============================================================*/
create table port
(
   port_id              varchar(64) not null,
   name                 varchar(64),
   admin_state_up       boolean,
   tenant_id            varchar(64),
   mac_address          varchar(64),
   fixed_ips            char(10),
   subnet_id            varchar(64),
   ip_address           varchar(64) comment '端口IP地址',
   security_groups      varchar(64),
   network_id           varchar(64),
   allowed_address_pair_ip varchar(64),
   allowed_address_pair_mac varchar(64),
   opt_name             varchar(64) comment '额外DHCP选项(name)',
   opt_value            varchar(64) comment '额外DHCP选项(value)',
   device_owner         varchar(64),
   device_id            varchar(64),
   status               varchar(64),
   binding_host_id      varchar(64),
   binding_vif_details_port_filter boolean,
   binding_vif_details_ovs_hybrid_plug boolean,
   binding_vif_type     varchar(64),
   bandwidth_tx         varchar(64),
   bandwidth_rx         varchar(64),
   primary key (port_id)
);

/*==============================================================*/
/* Table: public_ip                                             */
/*==============================================================*/
create table public_ip
(
   public_ip_id         varchar(64) not null comment '创建ip的编号',
   public_ip_resource_id varchar(64),
   name                 varchar(64) comment 'ip名称',
   tenant_id            varchar(64),
   default_snat_source  boolean comment '是否进行源地址转换',
   floating_ip_address  varchar(64) comment '具体ip地址',
   type                 varchar(64) comment '网关路由类型igw/vpngw',
   gw_router_id         varchar(64) comment '网关路由ID',
   status               varchar(16),
   admin_state_up       boolean,
   primary key (public_ip_id)
);

alter table public_ip comment '单独ip信息表';

/*==============================================================*/
/* Table: public_ip_resource                                    */
/*==============================================================*/
create table public_ip_resource
(
   public_ip_resource_id varchar(64) not null,
   tenant_id            varchar(64) comment '租户编码',
   begin                varchar(64),
   end                  varchar(64),
   mask                 varchar(16),
   gateway_ip           varchar(64),
   ip                   varchar(64),
   primary key (public_ip_resource_id)
);

alter table public_ip_resource comment 'ip资源表';

/*==============================================================*/
/* Table: routing_rule                                          */
/*==============================================================*/
create table routing_rule
(
   routing_rule_id      varchar(64) not null,
   routing_table_id     varchar(64),
   name                 varchar(64),
   prefix               varchar(64),
   nexthop              varchar(64),
   gw_router_id         varchar(64),
   status               varchar(16),
   type                 varchar(16),
   primary key (routing_rule_id)
);

alter table routing_rule comment '路由规则表';

/*==============================================================*/
/* Table: routing_table                                         */
/*==============================================================*/
create table routing_table
(
   routing_table_id     varchar(64) not null,
   name                 varchar(64),
   vpc_id               varchar(64),
   routing_table_index  int,
   tenant_id            varchar(64),
   status               varchar(64),
   primary key (routing_table_id)
);

alter table routing_table comment '路由表';

/*==============================================================*/
/* Table: server                                                */
/*==============================================================*/
create table server
(
   server_id            varchar(64) not null,
   name                 varchar(64),
   tenant_id            varchar(64),
   security_groups_name varchar(64),
   networks_uuid        varchar(64),
   networks_port        varchar(64),
   networks_fixed_ip    varchar(64),
   links_href           varchar(512),
   links_rel            varchar(64),
   address_mac          varchar(64),
   address_ip_version   varchar(64),
   address_ip_address   varchar(64),
   vm_state             varchar(64),
   instance_name       varchar(64),
   hypervisor_host_name  varchar(64),
   flavor_id            varchar(64),
   flavor_link_href     varchar(512),
   flavor_link_rel      varchar(64),
   host_id              varchar(64),
   status               varchar(64),
   metadata             varchar(64),
   personality          varchar(64),
   user_data            varchar(64),
   block_device_mapping_v2 varchar(64),
   device_name          varchar(64),
   source_type          varchar(64),
   availability_zone    varchar(64),
   destination_type     varchar(64),
   delete_on_termination boolean,
   guest_format         varchar(64),
   boot_index           int,
   config_drive         boolean,
   disk_config          varchar(64),
   create_time          datetime,
   primary key (server_id)
);

alter table server comment '云主机';

/*==============================================================*/
/* Table: subnet                                                */
/*==============================================================*/
create table subnet
(
   subnet_id            varchar(64) not null,
   name                 varchar(64),
   network_id           varchar(64),
   tenant_id            varchar(64),
   gateway_ip           varchar(64),
   ip_version           varchar(64),
   cidr                 varchar(64),
   enable_dhcp          boolean,
   allocation_pools_start varchar(64),
   allocation_pools_end varchar(64),
   dns_nameservers      varchar(64),
   host_routes_destination varchar(64),
   host_routes_nexthop  varchar(64),
   ipv6_ra_mode         varchar(64),
   ipv6_address_mode    varchar(64),
   primary key (subnet_id)
);

alter table subnet comment 'vpc子网';

/*==============================================================*/
/* Table: subnet_routing_table                                  */
/*==============================================================*/
create table subnet_routing_table
(
   subnet_routing_table_id varchar(64) not null,
   subnet_id            varchar(64),
   routing_table_id     varchar(64),
   tenant_id            varchar(64),
   name                 varchar(64),
   admin_state_up       boolean,
   status               varchar(16),
   primary key (subnet_routing_table_id)
);

alter table subnet_routing_table comment '路由关联子网表';

/*==============================================================*/
/* Table: tenant                                                */
/*==============================================================*/
create table tenant
(
   tenant_id            varchar(64) not null comment '租户编码',
   tenant_username      varchar(128) comment '租户用户名',
   tenant_password      varchar(64) comment '租户密码',
   primary key (tenant_id)
);

alter table tenant comment '租户表';

/*==============================================================*/
/* Table: vpc                                                   */
/*==============================================================*/
create table vpc
(
   vpc_id               varchar(64) not null,
   tenant_id            varchar(64) comment '租户编码',
   name                 varchar(64) comment '路由名称',
   admin_state_up       boolean comment '是否启用管理员状态',
   status               varchar(64) comment '路由状态，是否激活',
   external_gateway_info varchar(128),
   primary key (vpc_id)
);

alter table vpc comment 'vpc信息表';

/*==============================================================*/
/* Table: vpn_connection_point                                  */
/*==============================================================*/
create table vpn_connection_point
(
   vpn_connection_point_id varchar(64) not null,
   name                 varchar(64) comment 'vpn连接点名称',
   vgw_router_id        varchar(64) comment 'VPN网关路由id',
   public_ip_id         varchar(64) comment '创建ip的编号',
   vpn_type             varchar(16) comment 'VPN类型：GRE',
   tunnel_ip            varchar(64) comment '隧道ip',
   local_ip             varchar(64) comment '本机ip',
   remote_ip            varchar(64) comment '远端ip',
   extra_params_static_routes varchar(64) comment '静态路由配置：172.16.56.166/25',
   extra_params_interface varchar(64) comment '网卡端口：eth0',
   extra_params_remote_prefix varchar(64) comment '远端前缀：192.168.1.0/24',
   extra_params_local_prefix varchar(64) comment '本地前缀：192.168.2.0/24',
   status               varchar(16),
   primary key (vpn_connection_point_id)
);

alter table vpn_connection_point comment 'vpn连接点';

/*==============================================================*/
/* Table: hypervisor                                            */
/*==============================================================*/
create table hypervisor
(
   hypervisor_id        varchar(64) not null,
   hypervisor_hostname  varchar(128),
   tenant_id            varchar(64),
   count                int,
   vcpus_used           int,
   hypervisor_type      varchar(64),
   local_gb_used        int,
   host_ip              varchar(64),
   memory_mb_used       int,
   memory_mb            int,
   current_workload     int,
   vcpus                int,
   cpu_info             longtext,
   running_vms          int,
   free_disk_gb         int,
   hypervisor_version   varchar(64),
   disk_available_least int,
   local_gb             int,
   free_ram_mb          int,
   service_host         varchar(64),
   service_id           varchar(64),
   primary key (hypervisor_id)
);

alter table hypervisor comment '宿主机节点信息';


/*==============================================================*/
/* Table: instance                                              */
/*==============================================================*/
create table instance
(
   instance_id          varchar(64) not null comment '实例表id',
   instance_name        varchar(64) comment '实例名称',
   template_id          varchar(64) not null comment '模板id',
   type               varchar(16) comment '实例类型',
   status               varchar(16) comment '实例状态',
   description          varchar(128) comment '实例描述',
   create_time          datetime comment '创建时间',
   primary key (instance_id)
);

alter table instance comment '实例信息表';

/*==============================================================*/
/* Table: instance_server                                       */
/*==============================================================*/
create table instance_server
(
   id                   int not null auto_increment,
   instance_id          varchar(64) not null,
   server_id            varchar(64) not null,
   vnf_id 				varchar(64),
   vnf_name             varchar(64),
   instancenum int(11),
   sequence int(11),
   primary key (id)
);

alter table instance_server comment '实例与云主机关联表';

alter table instance_server add constraint FK_instance_server_instance foreign key (instance_id)
      references instance (instance_id)  on delete cascade;

alter table instance_server add constraint FK_instance_server_server foreign key (server_id)
      references server (server_id)  on delete cascade;

alter table acl add constraint FK_acl_vpc_id foreign key (vpc_id)  references vpc (vpc_id) ;

alter table acl add constraint FK_acl_tenant_id foreign key (tenant_id)
      references tenant (tenant_id) ;

alter table acl_rule add constraint FK_acl_rule_acl_id foreign key (acl_id)
      references acl (acl_id) ;

alter table acl_subnet add constraint FK_acl_subnet_acl_id foreign key (acl_id)
      references acl (acl_id) ;

alter table acl_subnet add constraint FK_acl_subnet_id foreign key (subnet_id)
      references subnet (subnet_id) ;

alter table gw_router add constraint FK_router_tenant_id foreign key (tenant_id)
      references tenant (tenant_id) ;

alter table gw_router add constraint FK_router_vpc_id foreign key (vpc_id)
      references vpc (vpc_id) ;

alter table nat add constraint FK_nat_public_ip_id foreign key (public_ip_id)
      references public_ip (public_ip_id) ;

alter table port add constraint FK_port_network_id foreign key (network_id)
      references network (network_id) ;

alter table port add constraint FK_port_subnet_Id foreign key (subnet_id)
      references subnet (subnet_id) ;

--alter table public_ip add constraint FK_public_ip_resource_id foreign key (public_ip_resource_id)
--      references public_ip_resource (public_ip_resource_id) ;

alter table public_ip_resource add constraint FK_public_ip_res_tenant_id foreign key (tenant_id)
      references tenant (tenant_id) ;

alter table routing_rule add constraint FK_routing_rule_router_id foreign key (gw_router_id)
      references gw_router (gw_router_id) ;

alter table routing_rule add constraint FK_routing_rule_table_id foreign key (routing_table_id)
      references routing_table (routing_table_id) ;

alter table routing_table add constraint FK_routing_table_vpc_id foreign key (vpc_id)
      references vpc (vpc_id) ;

alter table server add constraint FK_server_tenant_id foreign key (tenant_id)
      references tenant (tenant_id) ;

alter table subnet add constraint FK_subnet_network_id foreign key (network_id)
      references network (network_id) ;

alter table subnet_routing_table add constraint FK_subnet_routing_table_id foreign key (routing_table_id)
      references routing_table (routing_table_id) ;

alter table subnet_routing_table add constraint FK_subnet_routing_table_subnet_id foreign key (subnet_id)
      references subnet (subnet_id) ;

alter table vpc add constraint FK_vpc_tenant_id foreign key (tenant_id)
      references tenant (tenant_id) ;

alter table vpn_connection_point add constraint FK_vpn_conpoint_publicip_id foreign key (public_ip_id)
      references public_ip (public_ip_id) ;

alter table vpn_connection_point add constraint FK_vpn_conpoint_route_id foreign key (vgw_router_id)
      references gw_router (gw_router_id) ;

insert into tenant VALUES('4c1944cb12c84985a8a94b49aaea5d2d','admin','admin');      
