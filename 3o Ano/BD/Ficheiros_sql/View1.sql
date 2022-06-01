DROP VIEW IF EXISTS consultasPorDecorrer;

create view consultasPorDecorrer as
	select * from Consulta where Consulta.data < NOW();