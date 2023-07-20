public class SortingAlgoDecrescQuickSort implements SortingAlgoStrategy
{
    public void ordena(List<Produto> produtos, int ini, int fim)
    {
        if (ini < fim) {

            int q = particiona(ini, fim);

            ordena(ini, q);
            ordena(q + 1, fim);
        }
    }

    private int particiona(List<Produto> produtos, int ini, int fim) {
        Produto x = produtos.get(ini);

        int i = ini - 1;
        int j = fim + 1;

        while (true) {
            if (criterio.equals(CRIT_DESC_DECRESC)) {
                do {
                    j--;
                } while (produtos.get(j).getDescricao().compareToIgnoreCase(x.getDescricao()) > 0);

                do {
                    i++;
                } while (produtos.get(i).getDescricao().compareToIgnoreCase(x.getDescricao()) < 0);
            } else if (criterio.equals(CRIT_PRECO_DECRESC)) {
                do {
                    j--;
                } while (produtos.get(j).getPreco() > x.getPreco());

                do {
                    i++;
                } while (produtos.get(i).getPreco() < x.getPreco());
            } else if (criterio.equals(CRIT_ESTOQUE_DECRESC)) {
                do {
                    j--;
                } while (produtos.get(j).getQtdEstoque() > x.getQtdEstoque());

                do {
                    i++;
                } while (produtos.get(i).getQtdEstoque() < x.getQtdEstoque());
            } else {
                throw new RuntimeException("Criterio invalido!");
            }

            if (i < j) {
                Produto temp = produtos.get(i);
                produtos.set(i, produtos.get(j));
                produtos.set(j, temp);
            } else {
                return j;
            }
        }
    }
}