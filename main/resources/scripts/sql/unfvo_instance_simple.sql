/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/5/26 10:36:18                           */
/*==============================================================*/


drop table if exists attribute;

drop table if exists member;

drop table if exists vip;

drop table if exists pool;

/*==============================================================*/
/* Table: attribute                                             */
/*==============================================================*/
create table attribute
(
   name                 varchar(64) not null,
   type                 varchar(64) not null,
   value                varchar(128) not null,
   ext1                 varchar(128),
   ext2                 varchar(128),
   primary key (name, type, value)
);

/*==============================================================*/
/* Table: member                                                */
/*==============================================================*/
create table member
(
   id                   varchar(64) not null,
   pool_id              varchar(64) not null,
   address              varchar(32),
   protocol_port        varchar(8),
   tenant_id            varchar(64) not null,
   status               varchar(16),
   primary key (id)
);

/*==============================================================*/
/* Table: pool                                                  */
/*==============================================================*/
create table pool
(
   id                   varchar(64) not null,
   name                 varchar(64),
   subnet_id            varchar(64) not null,
   tenant_id            varchar(64) not null,
   protocol             varchar(16),
   lb_method            varchar(16),
   status               varchar(16),
   description          varchar(128),
   primary key (id)
);

/*==============================================================*/
/* Table: vip                                                   */
/*==============================================================*/
create table vip
(
   id                   varchar(64) not null,
   name                 varchar(32),
   subnet_id            varchar(64) not null,
   tenant_id            varchar(64) not null,
   pool_id              varchar(64) not null,
   adress               varchar(16),
   protocal             varchar(16),
   protocal_port        varchar(8),
   status               varchar(32),
   primary key (id)
);

alter table member add constraint FK_Pool_Member foreign key (pool_id)
      references pool (id) on delete cascade on update cascade;

alter table vip add constraint FK_Pool_VIP foreign key (pool_id)
      references pool (id) on delete cascade on update cascade;

