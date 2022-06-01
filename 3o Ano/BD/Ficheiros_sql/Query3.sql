DROP FUNCTION IF EXISTS massagistasEspecializacaoFilial;

DELIMITER $$
create function massagistasEspecializacaoFilial(idEsp int, idFil int)
returns int
deterministic
	BEGIN
		declare res int;
		set res = (
			select count(*) 
			from Massagista
			inner join Massagista_has_Especializacao
				on Massagista.idMassagista = Massagista_has_Especializacao.Massagista_idMassagista
			where
				Massagista_has_Especializacao.Especializacao_idEspecializacao = idEsp
				and Massagista.Filial_idFilial = idFil
		);
        return res;
	END$$
DELIMITER ;

-- select massagistasEspecializacaoFilial(1, 7);