DROP PROCEDURE IF EXISTS massagistasFilial;

DELIMITER $$
create procedure massagistasFilial(in idFil int)
	BEGIN
		select nome from Massagista
        where Massagista.Filial_idFilial = idFil;
	END$$
DELIMITER ;

-- call massagistasFilial(10);