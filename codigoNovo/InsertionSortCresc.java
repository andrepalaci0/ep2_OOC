import java.util.*;
public class InsertionSortCresc implements SortingStrategy {
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
    public void ordena(List <Produto> produtos, int ini, int fim) {
        // tem q alterar oq ele recebe, pra ele ordenar por um tipo generico pré
        // definido
    
        for(int i = 1; i < produtos.size(); i++) {
            Produto chave = produtos.get(i);
            int j = i - 1;

            while(j >= 0 && compara(produtos.get(j), chave, criterio) > 0) {
                produtos.set(j + 1, produtos.get(j));
                j--;
            }

            produtos.set(j + 1, chave);

        }
            
    }

    private int compara(Produto p1, Produto p2, String criterio) {

        if(criterio.equals(CRIT_DESC_CRESC)) {
            return p1.getDescricao().compareToIgnoreCase(p2.getDescricao());
        } else if(criterio.equals(CRIT_PRECO_CRESC)) {
            return Double.compare(p1.getPreco(), p2.getPreco());
        } else if(criterio.equals(CRIT_ESTOQUE_CRESC)){
            return Integer.compare(p1.getQtdEstoque(), p2.getQtdEstoque());
        } else {
            throw new IllegalArgumentException("Criterio invalido!");
        }

    }

}
