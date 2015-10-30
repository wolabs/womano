/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/25 19:41:41                           */
/*==============================================================*/


drop table if exists template_nsd;

drop table if exists template_nsd_vnfd;

drop table if exists template_vnfd;

/*==============================================================*/
/* Table: TEMPLATE_NSD                                          */
/*==============================================================*/
create table template_nsd
(
   template_id          varchar(64) not null,
   name                 varchar(64),
   provider             varchar(64),
   description          varchar(256),
   version              varchar(32),
   type                 varchar(32),
   content              longtext,
   primary key (template_id)
);

/*==============================================================*/
/* Table: TEMPLATE_NSD_VNFD                                     */
/*==============================================================*/
create table template_nsd_vnfd
(
   template_id          varchar(64) not null,
   vnfd_id              varchar(64) not null,
   primary key (template_id, vnfd_id)
);

/*==============================================================*/
/* Table: TEMPLATE_VNFD                                         */
/*==============================================================*/
create table template_vnfd
(
   vnfd_id              varchar(64) not null,
   name                 varchar(64),
   provider             varchar(64),
   description          varchar(256),
   version              varchar(32),
   content              longtext,
   primary key (vnfd_id)
);

alter table template_nsd_vnfd add constraint FK_Reference_1 foreign key (template_id)
      references template_nsd (template_id) on delete restrict on update restrict; 

alter table template_nsd_vnfd add constraint FK_Reference_2 foreign key (vnfd_id)
      references template_vnfd (vnfd_id) on delete restrict on update restrict;

