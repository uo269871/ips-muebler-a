CREATE TABLE IPS_VENTAS
(
  VENTA_ID VARCHAR2(36) NOT NULL 
, CLIENT_ID VARCHAR2(36)
, FECHA_ENTREGA DATE NOT NULL 
, CONSTRAINT IPS_VENTAS_PK PRIMARY KEY 
  (
    VENTA_ID 
  )
  ENABLE 
, CONSTRAINT IPS_VENTAS_FK1 FOREIGN KEY (CLIENT_ID)
	  REFERENCES IPS_CLIENTES (CLIENT_ID) ENABLE
)
