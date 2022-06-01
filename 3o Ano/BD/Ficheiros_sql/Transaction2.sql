DROP PROCEDURE IF EXISTS terminarConsulta;

DELIMITER $$
create procedure terminarConsulta(in codigo VARCHAR(45), in valor DOUBLE, in idConsulta int, in avaliacao int, in idMassagista int)
BEGIN
	declare data DATETIME;
    declare numConsultas int;
    declare avalAtual int;
    declare avalFinal int;
    declare Error boolean default 0;
	declare continue handler for SQLEXCEPTION set Error =1;
    start transaction;
    
	-- Criar Fatura
    set data = NOW();
	insert into Fatura (codigo, data, valor_c_iva, Consulta_idConsulta)
		values (codigo, data, valor, idConsulta);
	
	-- Atribuir avaliação à Massagista
    set avalAtual = (select avaliacao from Massagista 
		where idMassagista = idMassagista);
	set numConsultas = (select count(*) from Consulta
		where Consulta.Massagista_idMassagista = idMassagista);
    set avalFinal = ((avalAtual * numConsultas) + avaliacao) / (numConsultas + 1);
    update Massagista
		set avaliacao = avalFinal where idMassagista = idMassagista;
    
    if Error=1 then begin rollback; 
		select('Errors found. Rolling back');
		end;
	else commit;
    end if;
END $$
DELIMITER ;
