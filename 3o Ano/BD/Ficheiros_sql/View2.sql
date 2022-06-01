DROP VIEW IF EXISTS faturacaoClientes;

create view faturacaoClientes as
	select nome,SUM(valor_c_iva)
		from Cliente
		inner join Consulta
			on Cliente.idCliente = Consulta.Cliente_idCliente
		inner join Fatura
			on Consulta.idConsulta = Fatura.Consulta_idConsulta
		group by nome
		order by nome asc;