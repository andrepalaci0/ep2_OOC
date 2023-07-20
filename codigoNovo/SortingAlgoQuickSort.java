public class SortingAlgoQuickSort implements SortingAlgoStrategy {
    // obviamente esses algoritmos tem q ser adaptados p receber a collection que,
    // teoricamente
    // vai permitir um acesso a essas variaveis
    // nao sei preciso pensar ainda - itu

    public void ordena(int ini, int fim) {

        if (ini < fim) {

            int q = particiona(ini, fim);

            ordena(ini, q);
            ordena(q + 1, fim);
        }

    }

    private int particiona(int ini, int fim) {
        Produto x = produtos[ini];
        int i = (ini - 1);
        int j = (fim + 1);

        while (true) {

            if (criterio.equals(CRIT_DESC_CRESC)) {

                do {
                    j--;

                } while (produtos[j].getDescricao().compareToIgnoreCase(x.getDescricao()) > 0);

                do {
                    i++;

                } while (produtos[i].getDescricao().compareToIgnoreCase(x.getDescricao()) < 0);
            } else if (criterio.equals(CRIT_PRECO_CRESC)) {

                do {
                    j--;

                } while (produtos[j].getPreco() > x.getPreco());

                do {
                    i++;

                } while (produtos[i].getPreco() < x.getPreco());
            }

            else if (criterio.equals(CRIT_ESTOQUE_CRESC)) {

                do {
                    j--;

                } while (produtos[j].getQtdEstoque() > x.getQtdEstoque());

                do {
                    i++;

                } while (produtos[i].getQtdEstoque() < x.getQtdEstoque());

            } else {

                throw new RuntimeException("Criterio invalido!");
            }

            if (i < j) {
                Produto temp = produtos[i];
                produtos[i] = produtos[j];
                produtos[j] = temp;
            } else
                return j;
        }
    }

}
