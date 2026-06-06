## Mapeamento do Indicador: Despesa Total com Pessoal

Fonte: SICONFI  
Relatório: RGF  
Anexo: RGF-Anexo 01  
Esfera: M  
Poder: E  
Periodicidade: Q  

### Contas necessárias

| Campo de negócio | cod_conta | coluna |
|---|---|---|
| Receita Corrente Líquida | ReceitaCorrenteLiquidaLimiteLegal | Valor |
| Receita Corrente Líquida Ajustada | ReceitaCorrenteLiquidaAjustada | Valor |
| Despesa Total com Pessoal | DespesaComPessoalTotal | Valor |
| Percentual da DTP | DespesaComPessoalTotal | % sobre a RCL Ajustada |
| Limite de Alerta | LimiteDeAlertaDespesaComPessoalTotal | % sobre a RCL Ajustada |
| Limite Prudencial | LimitePrudencialDespesaComPessoalTotal | % sobre a RCL Ajustada |
| Limite Máximo | LimiteMaximoDespesaComPessoalTotal | % sobre a RCL Ajustada |

### Regra inicial de classificação

Se DTP% < limite de alerta:
situação = Regular

Se DTP% >= limite de alerta e < limite prudencial:
situação = Em alerta

Se DTP% >= limite prudencial e < limite máximo:
situação = Acima do prudencial

Se DTP% >= limite máximo:
situação = Acima do limite legal

## Validação em múltiplos municípios

Foram testados dois municípios catarinenses no RGF Anexo 01, exercício 2024, período 3, Poder Executivo.

Municípios:
- Tubarão/SC: 4218707
- Florianópolis/SC: 4205407

Resultado:
Os mesmos cod_conta foram encontrados nos dois municípios.

Decisão:
O backend deverá filtrar os dados da API usando cod_conta + coluna, e não o texto do campo conta.

Justificativa:
O campo cod_conta é mais estável e adequado para regra de negócio. O campo conta é descritivo e pode conter variações textuais.