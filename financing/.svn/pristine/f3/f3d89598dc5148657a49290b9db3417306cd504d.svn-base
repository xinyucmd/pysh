-- Create table
create table WEALTH_PRDT_BASE
(
  id             number(10),
  prdt_no        varchar2(10),
  prdt_name      varchar2(100),
  standard_amt   varchar2(100),
  advance_redeem varchar2(100),
  del_flg        varchar2(2),
  sts            varchar2(2),
  start_date     varchar2(10),
  end_date       varchar2(10),
  create_op      varchar2(10),
  create_date    varchar2(10),
  cmt            varchar2(1000)
)
;
-- Add comments to the table 
comment on table WEALTH_PRDT_BASE
  is '财富产品配置表';
-- Drop table
drop table WEALTH_MAIN_ACCOUNT cascade constraints;
-- Create table
create table WEALTH_MAIN_ACCOUNT
(
  account_no   VARCHAR2(50),
  cif_no       VARCHAR2(50),
  cif_name     VARCHAR2(50),
  account_type VARCHAR2(50),
  status       VARCHAR2(50),
  open_date    VARCHAR2(50),
  open_op      VARCHAR2(50),
  close_date   VARCHAR2(50),
  close_op     VARCHAR2(50),
  cmt          VARCHAR2(50)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table 
comment on table WEALTH_MAIN_ACCOUNT
  is '财富账户总表';
  -- Create table
create table WEALTH_SUB_ACCOUNT
(
  account_no   varchar2(50),
  sub_no       varchar2(50),
  pact_no      varchar2(50),
  cif_no       varchar2(50),
  cif_name     varchar2(50),
  cash_amt     varchar2(50),
  renew_amt    varchar2(50),
  rate         varchar2(50),
  prdt_no      varchar2(50),
  prdt_name    varchar2(50),
  term         varchar2(10),
  payment_type varchar2(50),
  income_amt   varchar2(50),
  redeem_amt   varchar2(50),
  if_wxd       varchar2(50),
  end_date     varchar2(10),
  start_date   varchar2(10),
  sts          varchar2(2),
  open_date    varchar2(10),
  open_op      varchar2(10),
  close_date   varchar2(10),
  close_op     varchar2(10),
  cmt          varchar2(500)
)
;
-- Add comments to the table 
comment on table WEALTH_SUB_ACCOUNT
  is '财富账户子表';
  -- Create table
create table WEALTH_ALERTS_ITEM
(
  id            NUMBER(10),
  alert_method  varchar2(10),
  alert_type    varchar2(10),
  adv_days      varchar2(10),
  alert_content varchar2(1000),
  del_flg       varchar2(10),
  create_op     varchar2(10),
  create_date   varchar2(10),
  cmt           varchar2(1000)
)
;
-- Add comments to the table 
comment on table WEALTH_ALERTS_ITEM
  is '财富提醒配置表';
-- Create table
create table WEALTH_FINANCE_DETAILS
(
  flow_no       number(10),
  account_no    varchar2(50),
  sub_no        varchar2(50),
  flow_type     varchar2(5),
  flow_abstract varchar2(500),
  flow_amt      varchar2(50),
  flow_date     varchar2(10),
  op_no         varchar2(10),
  cmt           varchar2(500)
)
;
-- Add comments to the table 
comment on table WEALTH_FINANCE_DETAILS
  is '财富流水明细表';
  -- Create table
create table WEALTH_ALERTS_DATA
(
  id            number(10),
  alert_method  varchar2(5),
  alert_type    varchar2(5),
  mobile        varchar2(20),
  alert_content varchar2(1000),
  send_sts      varchar2(5),
  create_op     varchar2(10),
  create_date   varchar2(20),
  cmt           varchar2(500)
)
;
-- Add comments to the table 
comment on table WEALTH_ALERTS_DATA
  is '短信发送记录';
-- Create table
-- Create table
create table WEALTH_REDEM
(
  pact_no      VARCHAR2(50),
  cif_no       VARCHAR2(50),
  cif_name     VARCHAR2(50),
  sts          VARCHAR2(5),
  redem_type   VARCHAR2(5),
  redem_amount VARCHAR2(50),
  pay_amt      VARCHAR2(50),
  create_date  VARCHAR2(50),
  create_op    VARCHAR2(50),
  cmt          VARCHAR2(500),
  id           VARCHAR2(50)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table 
comment on table WEALTH_REDEM
  is '财富赎回表';

