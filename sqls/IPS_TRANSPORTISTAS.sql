CREATE TABLE IPS_TRANSPORTISTAS
( 
  DNI VARCHAR2(20) NOT NULL
, ID_EMPLEADO VARCHAR(20) NOT NULL
, NOMBRE VARCHAR2(20)
, TELEFONO NUMBER
, HORA_ENTRADA NUMBER
, MINUTO_ENTRADA NUMBER
, HORA_SALIDA NUMBER
, MINUTO_SALIDA NUMBER
, CONSTRAINT IPS_TRANSPROTISTA_PK PRIMARY KEY 
  (
    DNI
  ) 
  ENABLE
, CONSTRAINT IPS_TRANSPORTISTAS_FK1 FOREIGN KEY (ID_EMPLEADO)
	 REFERENCES IPS_EMPLEADOS (ID_EMPLEADO) 
ENABLE
, CONSTRAINT IPS_TRANSPORTISTA_CHK_MINUTO CHECK 
  (
    MINUTO_ENTRADA between 0 and 59 and MINUTO_SALIDA between 0 and 59
  ) 
  ENABLE
, CONSTRAINT IPS_TRANSPORTISTA_CHK_HORA CHECK 
  (
    HORA_ENTRADA between 0 and 23 and HORA_SALIDA between 0 and 23
  )
  ENABLE
)