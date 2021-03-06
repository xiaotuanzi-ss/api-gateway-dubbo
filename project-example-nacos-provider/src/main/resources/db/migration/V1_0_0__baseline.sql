CREATE TABLE DEMO_ORDER (
	ORDER_ID VARCHAR2(50) NOT NULL,
	ORDER_CODE VARCHAR2(50) NOT NULL,
	ORDER_NAME VARCHAR2(50),
	UPDATE_DATE TIMESTAMP,
	PRIMARY KEY ("ORDER_ID")
);

COMMENT ON COLUMN DEMO_ORDER.ORDER_ID IS '订单ID';
COMMENT ON COLUMN DEMO_ORDER.ORDER_CODE IS '订单代码';
COMMENT ON COLUMN DEMO_ORDER.ORDER_NAME IS '订单名称';
COMMENT ON COLUMN DEMO_ORDER.UPDATE_DATE IS '修改时间';