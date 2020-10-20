CREATE TABLE IPS_PRODUCTOS 
(
  PRODUCT_ID VARCHAR2(4) NOT NULL 
, TYPE VARCHAR2(20) NOT NULL 
, PRICE FLOAT DEFAULT 0 NOT NULL 
, NAME VARCHAR2(40) NOT NULL 
, CONSTRAINT IPS_PRODUCTOS_PK PRIMARY KEY 
  (
    PRODUCT_ID 
  )
  ENABLE 
)
