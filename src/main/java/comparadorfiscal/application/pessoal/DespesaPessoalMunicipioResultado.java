package comparadorfiscal.application.pessoal;

import comparadorfiscal.domain.pessoal.SituacaoFiscalDespesaPessoal;

import java.math.BigDecimal;

public record DespesaPessoalMunicipioResultado(
        String municipio,
        String uf,
        Integer populacao,
        BigDecimal rclAjustada,
        BigDecimal despesaTotalComPessoal,
        BigDecimal percentualDtp,
        BigDecimal limiteAlerta,
        BigDecimal limitePrudencial,
        BigDecimal limiteMaximo,
        SituacaoFiscalDespesaPessoal situacao
) {
}
