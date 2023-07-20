public class SortingAlgoInsertionSort implements SortingAlgoStrategy {
    private 
    /*
     * public int particiona(int ini, int fim) {
     * return -1; // esse metodo nao eh util pro insertionSort entao ele so retorna
     * inválido? nao
     * // sei se isso eh valido no strategy
     * // mas teoricamente esse metodo nem deveria ser usado. Pensando em talvez
     * tornar
     * // o particiona() privado ao QuickSort
     * // -itu
     * }
     */


    // tudo aqui tem q ser alterado pra Collection tbm que
    // teoricamente arruma os erros de não termos acesso as veriaveis.

    // tem q pensar que isso aqui vai ser um objeto privado interno
    // à classe gera relatorio sla
    public void ordena(int ini, int fim) {

        for (int i = ini; i <= fim; i++) {

            Produto x = produtos[i];
            int j = (i - 1);

            while (j >= ini) {

                if (criterio.equals(CRIT_DESC_CRESC)) {

                    if (x.getDescricao().compareToIgnoreCase(produtos[j].getDescricao()) < 0) {

                        produtos[j + 1] = produtos[j];
                        j--;
                    } else
                        break;
                } else if (criterio.equals(CRIT_PRECO_CRESC)) {

                    if (x.getPreco() < produtos[j].getPreco()) {

                        produtos[j + 1] = produtos[j];
                        j--;
                    } else
                        break;
                } else if (criterio.equals(CRIT_ESTOQUE_CRESC)) {

                    if (x.getQtdEstoque() < produtos[j].getQtdEstoque()) {

                        produtos[j + 1] = produtos[j];
                        j--;
                    } else
                        break;
                } else
                    throw new RuntimeException("Criterio invalido!");
            }

            produtos[j + 1] = x;
        }
    }
}
