package comparadorfiscal.domain.pessoal;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ClassificadorSituacaoDespesaPessoalTest {


    @Test
    void deveClassificarComoRegular() {
        ClassificadorSituacaoDespesaPessoal classificador = new ClassificadorSituacaoDespesaPessoal();

        BigDecimal percentualDtp = new BigDecimal("45.88");
        BigDecimal limiteAlerta = new BigDecimal("48.6");
        BigDecimal limitePrudencial = new BigDecimal("51.3");
        BigDecimal limiteMaximo = new BigDecimal("54");

        SituacaoFiscalDespesaPessoal situacao = classificador.classificar(
                percentualDtp,
                limiteAlerta,
                limitePrudencial,
                limiteMaximo);
        assertThat(situacao).isEqualTo(SituacaoFiscalDespesaPessoal.REGULAR);

    }

    @Test
    void deveClassificarComoEmAlerta() {

        ClassificadorSituacaoDespesaPessoal classificador = new ClassificadorSituacaoDespesaPessoal();

        BigDecimal percentualDtp = new BigDecimal("48.6");
        BigDecimal limiteAlerta = new BigDecimal("48.6");
        BigDecimal limitePrudencial = new BigDecimal("51.3");
        BigDecimal limiteMaximo = new BigDecimal("54");

        SituacaoFiscalDespesaPessoal situacao = classificador.classificar(
                percentualDtp,
                limiteAlerta,
                limitePrudencial,
                limiteMaximo);
        assertThat(situacao).isEqualTo(SituacaoFiscalDespesaPessoal.EM_ALERTA);

    }

    @Test
    void deveClassificarComoAcimaDoPrudencial() {

        ClassificadorSituacaoDespesaPessoal classificador = new ClassificadorSituacaoDespesaPessoal();

        BigDecimal percentualDtp = new BigDecimal("51.3");
        BigDecimal limiteAlerta = new BigDecimal("48.6");
        BigDecimal limitePrudencial = new BigDecimal("51.3");
        BigDecimal limiteMaximo = new BigDecimal("54");

        SituacaoFiscalDespesaPessoal situacao = classificador.classificar(
                percentualDtp,
                limiteAlerta,
                limitePrudencial,
                limiteMaximo);
        assertThat(situacao).isEqualTo(SituacaoFiscalDespesaPessoal.ACIMA_DO_PRUDENCIAL);
    }

    @Test
    void deveClassificarComoAcimaDoLimite() {

        ClassificadorSituacaoDespesaPessoal classificador = new ClassificadorSituacaoDespesaPessoal();

        BigDecimal percentualDtp = new BigDecimal("54");
        BigDecimal limiteAlerta = new BigDecimal("48.6");
        BigDecimal limitePrudencial = new BigDecimal("51.3");
        BigDecimal limiteMaximo = new BigDecimal("54");

        SituacaoFiscalDespesaPessoal situacao = classificador.classificar(
                percentualDtp,
                limiteAlerta,
                limitePrudencial,
                limiteMaximo);
        assertThat(situacao).isEqualTo(SituacaoFiscalDespesaPessoal.ACIMA_DO_LIMITE);
    }
}
