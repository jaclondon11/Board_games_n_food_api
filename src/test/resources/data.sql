INSERT INTO Mesa (CODIGO) 
VALUES ('01');
INSERT INTO Mesa (CODIGO) 
VALUES ('02');
INSERT INTO Mesa (CODIGO) 
VALUES ('03');
INSERT INTO Mesa (CODIGO) 
VALUES ('04');

INSERT INTO Reserva (
FECHA_INICIO_RESERVA, FECHA_FIN_RESERVA,CANTIDAD_PERSONAS,TITULAR,JUEGO)
VALUES(NULL,NULL,4,'TITULAR_1','EXPLODING_KITTENS');

INSERT INTO Mesas_Por_Reserva (
ID_MESA, ID_RESERVA)
VALUES(1,1);