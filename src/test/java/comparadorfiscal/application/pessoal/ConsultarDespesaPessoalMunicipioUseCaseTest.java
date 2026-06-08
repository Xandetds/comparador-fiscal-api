package comparadorfiscal.application.pessoal;

import comparadorfiscal.domain.pessoal.SituacaoFiscalDespesaPessoal;
import org.junit.jupiter.api.Test;
import comparadorfiscal.domain.pessoal.ClassificadorSituacaoDespesaPessoal;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsultarDespesaPessoalMunicipioUseCaseTest {

    @Test
    void deveConsultarDespesaPessoalDeUmMunicipio() {

        BuscarLinhasRgfPort buscarLinhasRgfPort = new BuscarLinhasRgfFake();
        ClassificadorSituacaoDespesaPessoal classificador = new ClassificadorSituacaoDespesaPessoal();

        ConsultarDespesaPessoalMunicipioUseCase useCase =
                new ConsultarDespesaPessoalMunicipioUseCase(buscarLinhasRgfPort, classificador);

        DespesaPessoalMunicipioResultado resultado = useCase.consultar(4218707, 2024, 3);

        assertThat(resultado.uf()).isEqualTo("SC");
        assertThat(resultado.percentualDtp()).isEqualByComparingTo("45.88");
        assertThat(resultado.situacao()).isEqualTo(SituacaoFiscalDespesaPessoal.REGULAR);
        assertThat(resultado.municipio()).isEqualTo("Prefeitura Municipal de Tubarao - SC");
        assertThat(resultado.populacao()).isEqualTo(114389);
        assertThat(resultado.rclAjustada()).isEqualByComparingTo("460814088.73");
        assertThat(resultado.despesaTotalComPessoal()).isEqualByComparingTo("211430626.83");


    }

    private static class BuscarLinhasRgfFake implements BuscarLinhasRgfPort {
        @Override
        public List<LinhaRgf> buscarPorMunicipio(Integer codIbge, Integer exercicio, Integer periodo) {
            return List.of(
                    new LinhaRgf(
                            "Prefeitura Municipal de Tubarao - SC",
                            4218707,
                            "SC",
                            114389,
                            "ReceitaCorrenteLiquidaAjustada",
                            "Valor",
                            new BigDecimal("460814088.73")
                    ),
                    new LinhaRgf(
                            "Prefeitura Municipal de Tubarao - SC",
                            4218707,
                            "SC",
                            114389,
                            "DespesaComPessoalTotal",
                            "% sobre a RCL Ajustada",
                            new BigDecimal("45.88")
                    ),
                    new LinhaRgf(
                            "Prefeitura Municipal de Tubarao - SC",
                            4218707,
                            "SC",
                            114389,
                            "DespesaComPessoalTotal",
                            "Valor",
                            new BigDecimal("211430626.83")
                    ),
                    new LinhaRgf(
                            "Prefeitura Municipal de Tubarao - SC",
                            4218707,
                            "SC",
                            114389,
                            "LimiteDeAlertaDespesaComPessoalTotal",
                            "% sobre a RCL Ajustada",
                            new BigDecimal("48.6")
                    ),
                    new LinhaRgf(
                            "Prefeitura Municipal de Tubarao - SC",
                            4218707,
                            "SC",
                            114389,
                            "LimitePrudencialDespesaComPessoalTotal",
                            "% sobre a RCL Ajustada",
                            new BigDecimal("51.3")
                    ),
                    new LinhaRgf(
                            "Prefeitura Municipal de Tubarao - SC",
                            4218707,
                            "SC",
                            114389,
                            "LimiteMaximoDespesaComPessoalTotal",
                            "% sobre a RCL Ajustada",
                            new BigDecimal("54")
                    )
            );
        }
    }
}