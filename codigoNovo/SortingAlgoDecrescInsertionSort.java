public class SortingAlgoDecrescInsertionSort implements SortingAlgoStrategy
{
    public void ordena(List<Produto> produtos, int ini, int fim) { // confere se isso ta ok - ana
        // tem que alterar o que ele recebe, para ele ordenar por um tipo genérico pré definido
    
        for (int i = ini; i <= fim; i++) {
            Produto x = produtos.get(i);
            int j = (i - 1);
    
            while (j >= ini) {
                if (criterio.equals(CRIT_DESC_DECRESC)) {
                    if (x.getDescricao().compareToIgnoreCase(produtos.get(j).getDescricao()) > 0) { // Alteração aqui
                        produtos.set(j + 1, produtos.get(j));
                        j--;
                    } else {
                        break;
                    }
                } else if (criterio.equals(CRIT_PRECO_DECRESC)) {
                    if (x.getPreco() > produtos.get(j).getPreco()) { // Alteração aqui
                        produtos.set(j + 1, produtos.get(j));
                        j--;
                    } else {
                        break;
                    }
                } else if (criterio.equals(CRIT_ESTOQUE_DECRESC)) {
                    if (x.getQtdEstoque() > produtos.get(j).getQtdEstoque()) { // Alteração aqui
                        produtos.set(j + 1, produtos.get(j));
                        j--;
                    } else {
                        break;
                    }
                } else {
                    throw new RuntimeException("Criterio invalido!");
                }
            }
            produtos.set(j + 1, x);
        }
    }
}