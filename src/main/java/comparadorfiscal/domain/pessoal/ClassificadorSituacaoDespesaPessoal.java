package comparadorfiscal.domain.pessoal;

import java.math.BigDecimal;

public class ClassificadorSituacaoDespesaPessoal {

    public SituacaoFiscalDespesaPessoal classificar(
            BigDecimal percentualDtp,
            BigDecimal limiteAlerta,
            BigDecimal limitePrudencial,
            BigDecimal limiteMaximo) {

        if (percentualDtp.compareTo(limiteAlerta) < 0) {
            return SituacaoFiscalDespesaPessoal.REGULAR;
        } else if (percentualDtp.compareTo(limitePrudencial) < 0) {
            return SituacaoFiscalDespesaPessoal.EM_ALERTA;
        } else if (percentualDtp.compareTo(limiteMaximo) < 0) {
            return SituacaoFiscalDespesaPessoal.ACIMA_DO_PRUDENCIAL;
        }
        return SituacaoFiscalDespesaPessoal.ACIMA_DO_LIMITE_LEGAL;


    }
}

