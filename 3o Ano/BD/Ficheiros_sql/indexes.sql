
CREATE INDEX idx_nome_cliente
ON Cliente (nome);

CREATE INDEX idx_nome_filial
ON Filial (nome);

CREATE INDEX idx_cliente_consulta
ON Consulta(Cliente_idCliente);

CREATE INDEX idx_massagista_consulta
ON Consulta(Massagista_idMassagista);