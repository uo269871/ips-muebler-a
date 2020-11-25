CREATE TABLE IPS_CLIENTES 
(
  DNI VARCHAR2(9) 
, NAME VARCHAR2(40) NOT NULL 
, ADDRESS VARCHAR2(40) NOT NULL 
, CLIENT_ID VARCHAR2(36) NOT NULL 
, CONSTRAINT IPS_CLIENTES_PK PRIMARY KEY 
  (
    CLIENT_ID
  )
  ENABLE 
)
