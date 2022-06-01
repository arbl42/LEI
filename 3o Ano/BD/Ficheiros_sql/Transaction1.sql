DROP PROCEDURE IF EXISTS addMassagista;

DELIMITER $$
create procedure addMassagista(in nome VARCHAR(45), in genero VARCHAR(1), in morada VARCHAR(45),
							   in contacto_trabalho VARCHAR(45), in Filial_idFilial int, in especializacao int)
BEGIN
	declare idMassagista int;
    declare Error boolean default 0;
	declare continue handler for SQLEXCEPTION set Error =1;
    start transaction;
    
	-- Criar Massagista
	insert into Massagista (nome, avaliacao, genero, morada, contacto_trabalho, Filial_idFilial) 
	values (nome, 0, genero, morada, contacto_trabalho, Filial_idFilial);
	set idMassagista = last_insert_id();
	
	-- Associar Massagista a especialização
	insert into Massagista_has_Especializacao (Massagista_idMassagista, Especializacao_idEspecializacao) 
	values (idMassagista, especializacao);
    
    if Error=1 then begin rollback; 
		select('Rolling back');
		end;
	else commit;
    end if;
END $$
DELIMITER ;

-- Funcionamento correto:
-- call addMassagista('Maria Scholls', 'F', 'St Boulevard Avenue', '938-415-934', '1', 4);

-- Funcionamento incorreto:
-- call addMassagista('John Doe', 'M', 'Maryland Street', '964-350-755', '2', '24');
