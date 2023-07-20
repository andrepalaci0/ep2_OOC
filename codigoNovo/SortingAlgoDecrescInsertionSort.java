import java.util.*;
public class SortingAlgoDecrescInsertionSort implements SortingAlgoStrategy
{
    public void ordena(List <Produto> produtos, int ini, int fim) {
        // tem q alterar oq ele recebe, pra ele ordenar por um tipo generico pré
        // definido

        for (int i = ini; i <= fim; i++) {

            Produto x = produtos.get(i);
            int j = (i - 1);

            while (j >= ini) {

                if (criterio.equals(CRIT_DESC_CRESC)) {

                    if (x.getDescricao().compareToIgnoreCase(produtos.get(j).getDescricao()) < 0) {

                        produtos.set(j + 1, produtos.get(j)); // substitui produtos[j + 1] = produtos[j];
                        j--;
                    } else
                        break;
                } else if (criterio.equals(CRIT_PRECO_CRESC)) {

                    if (x.getPreco() < produtos.get(j).getPreco()) {

                        produtos.set(j + 1, produtos.get(j)); // substitui produtos[j + 1] = produtos[j];
                        j--;
                    } else
                        break;
                } else if (criterio.equals(CRIT_ESTOQUE_CRESC)) {

                    if (x.getQtdEstoque() < produtos.get(j).getQtdEstoque()) {

                        produtos.set(j + 1, produtos.get(j)); // substitui produtos[j + 1] = produtos[j];
                        j--;
                    } else
                        break;
                } else
                    throw new RuntimeException("Criterio invalido!");
            }

            produtos.set(j + 1, x); // substitui produtos[j + 1] = x;
        }
    }
}