CREATE TABLE IPS_PEDIDO
(
  PEDIDO_ID VARCHAR2(36) NOT NULL 
, STATE VARCHAR2(40) NOT NULL 
, TOTAL_PRICE FLOAT NOT NULL 
, CONSTRAINT IPS_PEDIDO_PK1 PRIMARY KEY 
  (
    PEDIDO_ID 
  )
  ENABLE 
)
