package comparadorfiscal.application.pessoal;

import java.math.BigDecimal;

public record LinhaRgf(String instituicao,
                       Integer codIbge,
                       String uf,
                       Integer populacao,
                       String codConta,
                       String coluna,
                       BigDecimal valor
) {}
