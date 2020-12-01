CREATE TABLE IPS_PRESUPUESTOS 
(
  PRESUPUESTO_ID VARCHAR2(36) NOT NULL 
, CLIENT_ID VARCHAR2(36)
, FECHA_CADUCIDAD DATE NOT NULL 
, CONSTRAINT IPS_PRESUPUESTOS_PK PRIMARY KEY 
  (
    PRESUPUESTO_ID 
  )
  ENABLE 
, CONSTRAINT IPS_PRESUPUESTOS_FK FOREIGN KEY (CLIENT_ID)
	  REFERENCES IPS_CLIENTES (CLIENT_ID) ENABLE
)
