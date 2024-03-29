CREATE TABLE IPS_EMPLEADOS
( 
  ID VARCHAR2(36) NOT NULL
, DNI VARCHAR2(20) NOT NULL
, NOMBRE VARCHAR2(20)
, DIRECCION VARCHAR2(20)
, TELEFONO NUMBER
, HORA_ENTRADA NUMBER
, MINUTO_ENTRADA NUMBER
, HORA_SALIDA NUMBER
, MINUTO_SALIDA NUMBER
, USUARIO VARCHAR2(20)
, PASSWORD VARCHAR2(20)
, CONSTRAINT IPS_EMPLEADO_PK PRIMARY KEY 
  (
    ID
  ) 
  ENABLE
, CONSTRAINT IPS_EMPLEADO_CHK_MINUTO CHECK 
  (
    MINUTO_ENTRADA between 0 and 59 and MINUTO_SALIDA between 0 and 59
  ) 
  ENABLE
, CONSTRAINT IPS_EMPLEADO_CHK_HORA CHECK 
  (
    HORA_ENTRADA between 0 and 23 and HORA_SALIDA between 0 and 23
  )
  ENABLE
)