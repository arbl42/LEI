DROP FUNCTION IF EXISTS numCon;

DELIMITER $$
create function numCon(idCli int)
returns int
deterministic
	BEGIN
		declare numC int;
		set numC = (select count(*) from Consulta
        where Consulta.Cliente_idCliente = idCli);
        return numC;
	END$$
DELIMITER ;

-- SELECT numCon(20);
