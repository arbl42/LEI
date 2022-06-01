insert into Utilizador (id, nome, `password`, tipo)
values
	(1, "Ana", "123456", "Gestor"),
	(2, "José", "123456", "Gestor"),
    (3, "Manuel", "123456", "Gestor");
    
insert into Robot (id, password, estado)
values
	(1, "123456", "Livre"),
    (2, "123456", "Ocupado"),
    (3, "123456", "Livre"),
	(4, "123456", "Livre");


insert into Localizacao (id, seccao, prateleira)
values
	(0, 0, 0),
	(1, 0, 1),
    (2, 0, 2),
    (3, 0, 3),
    (4, 1, 1),
    (5, 1, 2),
    (6, 1, 3);
    
insert into CodigoQR (id, numero)
values (1, "96517a93-f037-49d5-8324-599490b560db");

insert into Palete (id, Localizacao_id, CodigoQR_id)
values (1, 2, 1);


insert into OrdemTransporte (id, estado, tipo, Localizacao_id, Robot_id, Gestor_id)
values
	(1, "Pendente", "Entrega", 2, 1, 1),
    (2, "Aceite", "Entrega", 2, 2, 3),
    (3, "Recusado", "Entrega", 4, 4, 3),
    (4, "Pendente", "Recolha", 1, 3, 2);



