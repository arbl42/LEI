USE spaonthemove;

create role ADMINISTRADOR;
create role MASSAGISTA;
create role CLIENTE;

grant EXECUTE on FUNCTION spaonthemove.numCon to CLIENTE;
grant EXECUTE on FUNCTION spaonthemove.numCon to MASSAGISTA;
grant EXECUTE on FUNCTION spaonthemove.massagistasEspecializacaoFilial to CLIENTE;
grant EXECUTE on PROCEDURE spaonthemove.massagistasCliente to CLIENTE;
grant EXECUTE on PROCEDURE spaonthemove.massagistasCliente to MASSAGISTA;

grant ALL PRIVILEGES on spaonthemove.* to ADMINISTRADOR with GRANT option;

create user 'John Cena' identified by '433';
grant ADMINISTRADOR to 'John Cena';

create user 'Chuck Norris' identified by '999';
grant MASSAGISTA to 'Chuck Norris';


create user 'Elvis Presley' identified by '456';
grant CLIENTE to 'Elvis Presley';

