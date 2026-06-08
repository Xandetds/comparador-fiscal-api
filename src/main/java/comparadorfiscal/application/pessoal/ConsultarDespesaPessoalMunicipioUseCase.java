package comparadorfiscal.application.pessoal;

import comparadorfiscal.domain.pessoal.ClassificadorSituacaoDespesaPessoal;
import comparadorfiscal.domain.pessoal.SituacaoFiscalDespesaPessoal;

import java.math.BigDecimal;
import java.util.List;

public class ConsultarDespesaPessoalMunicipioUseCase {
    private final BuscarLinhasRgfPort buscarLinhasRgfPort;
    private final ClassificadorSituacaoDespesaPessoal classificador;

    public ConsultarDespesaPessoalMunicipioUseCase(
            BuscarLinhasRgfPort buscarLinhasRgfPort,
            ClassificadorSituacaoDespesaPessoal classificador){
        this.buscarLinhasRgfPort = buscarLinhasRgfPort;
        this.classificador = classificador;
    }

    public DespesaPessoalMunicipioResultado consultar(Integer codIbge, Integer exercicio, Integer periodo) {

        List<LinhaRgf> linhas = buscarLinhasRgfPort.buscarPorMunicipio(codIbge, exercicio, periodo);

        if (linhas == null || linhas.isEmpty()) {
            throw new IllegalArgumentException("Nenhum dado RGF encontrado para o município informado.");
        }

        LinhaRgf primeiraLinha = linhas.get(0);

        BigDecimal percentualDtp = null;
        BigDecimal limiteAlerta = null;
        BigDecimal limitePrudencial = null;
        BigDecimal limiteMaximo = null;

        BigDecimal rclAjustada = null;
        BigDecimal despesaTotalComPessoal = null;


        for (LinhaRgf linha : linhas) {
            if (linha.codConta().equals("DespesaComPessoalTotal")
                    && linha.coluna().equals("% sobre a RCL Ajustada")) {
                percentualDtp = linha.valor();
            } else if (linha.codConta().equals("LimiteDeAlertaDespesaComPessoalTotal")
                    && linha.coluna().equals("% sobre a RCL Ajustada")) {
                limiteAlerta = linha.valor();
            } else if (linha.codConta().equals("LimitePrudencialDespesaComPessoalTotal")
                    && linha.coluna().equals("% sobre a RCL Ajustada")) {
                limitePrudencial = linha.valor();
            } else if (linha.codConta().equals("LimiteMaximoDespesaComPessoalTotal")
                    && linha.coluna().equals("% sobre a RCL Ajustada")) {
                limiteMaximo = linha.valor();
            } else if (linha.codConta().equals("ReceitaCorrenteLiquidaAjustada")
                    && linha.coluna().equals("Valor")) {
                rclAjustada = linha.valor();
            } else if (linha.codConta().equals("DespesaComPessoalTotal")
                    && linha.coluna().equals("Valor")) {
                despesaTotalComPessoal = linha.valor();
            }
        }
            SituacaoFiscalDespesaPessoal situacao =
                    classificador.classificar(percentualDtp, limiteAlerta, limitePrudencial, limiteMaximo);


            return new DespesaPessoalMunicipioResultado(
                    primeiraLinha.instituicao(),
                    primeiraLinha.uf(),
                    primeiraLinha.populacao(),
                    rclAjustada,
                    despesaTotalComPessoal,
                    percentualDtp,
                    limiteAlerta,
                    limitePrudencial,
                    limiteMaximo,
                    situacao
            );

        }

    }
