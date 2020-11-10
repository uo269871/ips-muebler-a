CREATE TABLE IPS_TRANSPORTISTAS
( 
  DNI VARCHAR2(20) NOT NULL
, ID_EMPLEADO VARCHAR(20) NOT NULL
, CONSTRAINT IPS_TRANSPROTISTA_PK PRIMARY KEY 
  (
    DNI
  ) 
  ENABLE
, CONSTRAINT IPS_TRANSPORTISTAS_FK1 FOREIGN KEY (ID_EMPLEADO)
	 REFERENCES IPS_EMPLEADOS (ID) 
ENABLE
)