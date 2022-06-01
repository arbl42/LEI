DROP PROCEDURE IF EXISTS addCliente;

DELIMITER $$
create procedure addCliente(in morada VARCHAR(45), in nome VARCHAR(45), in idade int, in nascimento datetime,
							   in genero VARCHAR(1), in Filial_idFilial int, in contacto VARCHAR(45))
BEGIN
	declare idCliente int;
    declare Error boolean default 0;
	declare continue handler for SQLEXCEPTION set Error =1;
    start transaction;
    
	-- Criar Cliente
	insert into Cliente (morada, nome, idade, DOB, genero, Filial_idFilial) 
	values (morada, nome, idade, nascimento, genero, Filial_idFilial);
	set idCliente = last_insert_id();
	
	-- Associar Cliente aos seus contactos
	insert into Contacto_cliente (contacto, idCliente) 
	values (contacto, idCliente);
    
    if Error=1 then begin rollback; 
		select('Errors found. Rolling back');
		end;
	else commit;
    end if;
END $$
DELIMITER ;