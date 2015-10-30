/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/23 19:48:59                           */
/*==============================================================*/

drop table if exists property;

drop table if exists vdu_flavour;

drop table if exists workflow;

drop table if exists connection_point;

drop table if exists monitoring_param;

drop table if exists end_point;

drop table if exists vld;

drop table if exists member_vld;

drop table if exists assurance_param;

drop table if exists completion;

drop table if exists dependency;

drop table if exists member_vnf;

drop table if exists network_forwarding_path;

drop table if exists forwarding_graph;

drop table if exists network_interface;

drop table if exists vdu;

drop table if exists template_flavour;

drop table if exists template;



/*==============================================================*/
/* Table: assurance_param                                       */
/*==============================================================*/
create table assurance_param
(
   id                   int not null primary key auto_increment,
   param_id             varchar(64),
   value                int,
   vnf_flavour_id       int
);


/*==============================================================*/
/* Table: completion                                            */
/*==============================================================*/
create table completion
(
   id                   int not null primary key auto_increment,
   command              varchar(128),
   expectation          varchar(64),
   member_vnf_id        int
);


/*==============================================================*/
/* Table: connection_point                                      */
/*==============================================================*/
create table connection_point
(
   id                   int not null primary key auto_increment,
   name                 varchar(64),
   description          varchar(128),
   template_id          int
);


/*==============================================================*/
/* Table: dependency                                            */
/*==============================================================*/
create table dependency
(
   id                   int not null primary key auto_increment,
   source               varchar(64),
   targe                varchar(64),
   member_vnf_id        int
);


/*==============================================================*/
/* Table: end_point                                             */
/*==============================================================*/
create table end_point
(
   id                   int not null primary key auto_increment,
   end_point_id         varchar(64) not null,
   description          varchar(128),
   template_id          int
);

/*==============================================================*/
/* Table: forwarding_graph                                      */
/*==============================================================*/
create table forwarding_graph
(
   id                   int not null primary key auto_increment,
   forwarding_graph_id  varchar(64) not null,
   direction            varchar(64),
   ns_flavour_id        int
);

/*==============================================================*/
/* Table: member_vld                                            */
/*==============================================================*/
create table member_vld
(
   id                   int not null primary key auto_increment,
   member_vld_id        varchar(128) not null,
   ns_flavour_id        int
);



/*==============================================================*/
/* Table: member_vnf                                            */
/*==============================================================*/
create table member_vnf
(
   id                   int not null primary key auto_increment,
   member_vnf_id        varchar(64) not null,
   vnf_flavour          varchar(64),
   ns_flavour_id        int
);



/*==============================================================*/
/* Table: monitoring_param                                      */
/*==============================================================*/
create table monitoring_param
(
   id                   int not null primary key auto_increment,
   param_id             varchar(64) not null,
   description          varchar(128),
   template_id          int
);


/*==============================================================*/
/* Table: network_forwarding_path                               */
/*==============================================================*/
create table network_forwarding_path
(
   id                   int not null primary key auto_increment,
   end_point_src        varchar(64),
   forwarding_policy    varchar(64),
   vld                  varchar(64),
   dst_vnf_connection_point varchar(64),
   src_vnf              varchar(64),
   src_vnf_connection_point varchar(64),
   forwarding_graph_id  int
);

/*==============================================================*/
/* Table: network_interface                                     */
/*==============================================================*/
create table network_interface
(
   id                   int not null primary key auto_increment,
   name                 varchar(64),
   description          varchar(128),
   connection_point_ref varchar(128),
   vdu_id               int
);


/*==============================================================*/
/* Table: property                                              */
/*==============================================================*/
create table property
(
   id                   int not null primary key auto_increment,
   property_key                  varchar(64),
   property_value                varchar(64),
   network_interface_id int
);



/*==============================================================*/
/* Table: template                                              */
/*==============================================================*/
create table template
(
   id                   int not null primary key auto_increment,
   template_id          varchar(64) not null comment '对应模板中nsd_id或vnfd_id',
   name                 varchar(64),
   provider             varchar(64),
   description         varchar(128),
   version              varchar(16),
   type                 varchar(64),
   internal_connectivity varchar(64)
);
 ALTER TABLE template  ADD UNIQUE KEY(template_id); 
alter table template comment '模板表，存放模板的主要属性';



/*==============================================================*/
/* Table: template_flavour                                      */
/*==============================================================*/
create table template_flavour
(
   id                   int not null primary key auto_increment,
   flavour_id           varchar(64) not null,
   description          varbinary(128),
   type                 varchar(16) comment '区分ns和nfv的flavour,如果不填需要用template_id来判断',
   template_id          int
);
 ALTER TABLE template_flavour  ADD UNIQUE KEY(flavour_id, template_id);  
alter table template_flavour comment 'vnf和ns共用，通过外键template_id关联,可以通过type区分';



/*==============================================================*/
/* Table: vdu                                                   */
/*==============================================================*/
create table vdu
(
   id                   int not null primary key auto_increment,
   vdu_id               varchar(64) not null,
   num_instances        int(8),
   vnf_flavour_id       int
);


/*==============================================================*/
/* Table: vdu_flavour                                           */
/*==============================================================*/
create table vdu_flavour
(
   id                   int not null primary key auto_increment,
   vdu_flavour_id       varchar(64) not null,
   description          varchar(128),
   vm_image             varchar(256),
   storage              varchar(64),
   cpu                  tinyint(4),
   memory               tinyint(4),
   vdu_id               int
);



/*==============================================================*/
/* Table: vld                                                   */
/*==============================================================*/
create table vld
(
   id                   int not null primary key auto_increment,
   name                 varchar(64),
   vld_id               varchar(64) not null,
   provider             varchar(64),
   description          varchar(128),
   version              varchar(64),
   latency_assurance    varchar(64),
   max_end_points       varchar(64),
   template_id          int
);


/*==============================================================*/
/* Table: workflow                                              */
/*==============================================================*/
create table workflow
(
   id                   int not null primary key auto_increment,
   init                 varchar(64),
   terminate            varchar(64),
   graceful_shutdown    varchar(64),
   template_id          int,
   vdu_id               int
);



alter table assurance_param add constraint FK_assu_param_vnf_tmp_flavour_id foreign key (vnf_flavour_id)
      references template_flavour (id)  ;

alter table completion add constraint FK_completion_mem_vnf_id foreign key (member_vnf_id)
      references member_vnf (id)  ;

alter table connection_point add constraint FK_con_point_tmp_id foreign key (template_id)
      references template (id)  ;

alter table dependency add constraint FK_depency_mem_vnf_id foreign key (member_vnf_id)
      references member_vnf (id)  ;

alter table end_point add constraint FK_end_point_tmp_id foreign key (template_id)
      references template (id)  ;

alter table forwarding_graph add constraint FK_forwarding_graph_flavour_id foreign key (ns_flavour_id)
      references template_flavour (id)  ;

alter table member_vld add constraint FK_member_vld_flavour_id foreign key (ns_flavour_id)
      references template (id)  ;

alter table member_vnf add constraint FK_member_tmp_flavour_id foreign key (ns_flavour_id)
      references template_flavour (id)  ;

alter table monitoring_param add constraint FK_moni_param_tmp_id foreign key (template_id)
      references template (id)  ;

alter table network_forwarding_path add constraint FK_net_forward_path_graph_id foreign key (forwarding_graph_id)
      references forwarding_graph (id)  ;

alter table network_interface add constraint FK_network_interface_vdu_id foreign key (vdu_id)
      references vdu (id)  ;

alter table property add constraint FK_prop_net_interface_id foreign key (network_interface_id)
      references network_interface (id)  ;

alter table template_flavour add constraint FK_flavor_tmp_id foreign key (template_id)
      references template (id)  ;

alter table vdu add constraint FK_vdu_vnf_tmp_flavour_id foreign key (vnf_flavour_id)
      references template_flavour (id)  ;

alter table vdu_flavour add constraint FK_vdu_flavour_vdu_id foreign key (vdu_id)
      references vdu (id)  ;

alter table vld add constraint FK_vld_tmp_id foreign key (template_id)
      references template (id)  ;

alter table workflow add constraint FK_workflow_tmp_id foreign key (template_id)
      references template (id)  ;

