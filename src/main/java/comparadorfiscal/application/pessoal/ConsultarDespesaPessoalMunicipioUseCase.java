package comparadorfiscal.application.pessoal;

import comparadorfiscal.domain.pessoal.ClassificadorSituacaoDespesaPessoal;
import comparadorfiscal.domain.pessoal.SituacaoFiscalDespesaPessoal;

import java.math.BigDecimal;
import java.util.List;

public class ConsultarDespesaPessoalMunicipioUseCase {

    private static final String COLUNA_PERCENTUAL_RCL = "% sobre a RCL Ajustada";
    private static final String COLUNA_VALOR = "Valor";

    private static final String CONTA_DESPESA_PESSOAL = "DespesaComPessoalTotal";
    private static final String CONTA_LIMITE_ALERTA = "LimiteDeAlertaDespesaComPessoalTotal";
    private static final String CONTA_LIMITE_PRUDENCIAL = "LimitePrudencialDespesaComPessoalTotal";
    private static final String CONTA_LIMITE_MAXIMO = "LimiteMaximoDespesaComPessoalTotal";
    private static final String CONTA_RCL_AJUSTADA = "ReceitaCorrenteLiquidaAjustada";

    private final BuscarLinhasRgfPort buscarLinhasRgfPort;
    private final ClassificadorSituacaoDespesaPessoal classificador;

    public ConsultarDespesaPessoalMunicipioUseCase(
            BuscarLinhasRgfPort buscarLinhasRgfPort,
            ClassificadorSituacaoDespesaPessoal classificador) {
        this.buscarLinhasRgfPort = buscarLinhasRgfPort;
        this.classificador = classificador;
    }

    public DespesaPessoalMunicipioResultado consultar(Integer codIbge, Integer exercicio, Integer periodo) {

        List<LinhaRgf> linhas = buscarLinhasRgfPort.buscarPorMunicipio(codIbge, exercicio, periodo);

        if (linhas == null || linhas.isEmpty()) {
            throw new IllegalArgumentException("Nenhum dado RGF encontrado para o município informado.");
        }

        BigDecimal percentualDtp = buscarValor(linhas, CONTA_DESPESA_PESSOAL, COLUNA_PERCENTUAL_RCL);
        BigDecimal limiteAlerta = buscarValor(linhas, CONTA_LIMITE_ALERTA, COLUNA_PERCENTUAL_RCL);
        BigDecimal limitePrudencial = buscarValor(linhas, CONTA_LIMITE_PRUDENCIAL, COLUNA_PERCENTUAL_RCL);
        BigDecimal limiteMaximo = buscarValor(linhas, CONTA_LIMITE_MAXIMO, COLUNA_PERCENTUAL_RCL);

        BigDecimal rclAjustada = buscarValor(linhas, CONTA_RCL_AJUSTADA, COLUNA_VALOR);
        BigDecimal despesaTotalComPessoal = buscarValor(linhas, CONTA_DESPESA_PESSOAL, COLUNA_VALOR);

        SituacaoFiscalDespesaPessoal situacao = classificador.classificar(
                percentualDtp, limiteAlerta, limitePrudencial, limiteMaximo);

        LinhaRgf primeiraLinha = linhas.get(0);

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

    private BigDecimal buscarValor(List<LinhaRgf> linhas, String codConta, String coluna) {
        return linhas.stream()
                .filter(linha -> linha.codConta().equals(codConta) && linha.coluna().equals(coluna))
                .findFirst()
                .map(LinhaRgf::valor)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Valor não encontrado para conta '%s' e coluna '%s'", codConta, coluna)
                ));
    }
}