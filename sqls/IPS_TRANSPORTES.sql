CREATE TABLE IPS_TRANSPORTES 
(	
	ID_TRANSPORTE VARCHAR2(36 BYTE) NOT NULL ENABLE, 
	ID_VENTA VARCHAR2(36 BYTE) NOT NULL ENABLE, 
	ID_TRANSPORTISTA VARCHAR2(36 BYTE) NOT NULL ENABLE, 
	DIA_ENTREGA DATE NOT NULL ENABLE, 
	HORA_ENTREGA NUMBER NOT NULL ENABLE, 
	MINUTO_ENTREGA NUMBER NOT NULL ENABLE, 
	ESTADO VARCHAR2(20 BYTE) NOT NULL ENABLE,
	CONSTRAINT IPS_TRANSPORTES_PK PRIMARY KEY (
		ID_TRANSPORTE
	) ENABLE, 
	CONSTRAINT IPS_TRANSPORTES_FK_TRANS FOREIGN KEY (
		ID_TRANSPORTISTA
	)
	REFERENCES IPS_TRANSPORTISTAS (ID) ENABLE, 
	CONSTRAINT IPS_TRANSPORTES_FK_VENTA FOREIGN KEY (ID_VENTA)
	REFERENCES IPS_VENTAS (VENTA_ID) ENABLE
) 
