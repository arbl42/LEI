DROP PROCEDURE IF EXISTS massagistasCliente;

DELIMITER $$
create procedure massagistasCliente(in idCli int)
	BEGIN
		select nome from Massagista, Consulta
        where Consulta.Cliente_idCliente = idCli
        and Consulta.Massagista_idMassagista = Massagista.idMassagista;
	END$$
DELIMITER ;

-- call massagistasCliente(1);