CREATE TABLE IPS_VENTAS_PRODUCTOS
(
  VENTA_ID VARCHAR2(4) NOT NULL 
, PRODUCT_ID VARCHAR2(4) NOT NULL
, RECOGER NUMBER 
, MONTAR NUMBER 
, UNIDADES NUMBER NOT NULL
, CONSTRAINT IPS_VENTAS_PRODUCTOS_PK PRIMARY KEY 
  (
    VENTA_ID 
  , PRODUCT_ID 
  )
  ENABLE 
, CONSTRAINT IPS_VENTAS_PRODUCTOS_CHK1 CHECK 
  (
    RECOGER=1 OR RECOGER=0
  ) 
  ENABLE
, CONSTRAINT IPS_VENTAS_PRODUCTOS_CHK2 CHECK 
  (
    MONTAR=1 OR MONTAR=0
  ) 
ENABLE
, CONSTRAINT IPS_VENTAS_PRODUCTOS_FK1 FOREIGN KEY (VENTA_ID)
	 REFERENCES IPS_VENTAS (VENTA_ID) 
ENABLE
, CONSTRAINT IPS_VENTAS_PRODUCTOS_FK2 FOREIGN KEY (PRODUCT_ID)
	  REFERENCES IPS_PRODUCTOS (PRODUCT_ID)
ENABLE
, CONSTRAINT IPS_VENTAS_PRO_CHK3 CHECK 
  (
    UNIDADES>=0
  ) 
ENABLE
)