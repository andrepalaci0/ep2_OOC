import java.util.*;
public class QuickSortDecresc implements SortingStrategy
{
    
    public void ordena(List <Produto> produtos, int ini, int fim) {

        if (ini < fim) {

            int q = particiona(produtos, ini, fim);

            ordena(produtos, ini, q);
            ordena(produtos, q + 1, fim);
        }

    }

    private int particiona(List<Produto> produtos, int ini, int fim) {
        Produto x = produtos.get(ini); // subtitui Produto x = produtos[ini] pq nao da para acessar o indice direto na List
		int i = (ini - 1);
		int j = (fim + 1);

		while (true) {

			if (criterio.equals(CRIT_DESC_CRESC)) {

				do {
					j--;

				} while (produtos.get(j).getDescricao().compareToIgnoreCase(x.getDescricao()) > 0);

				do {
					i++;

				} while (produtos.get(i).getDescricao().compareToIgnoreCase(x.getDescricao()) < 0);
			} else if (criterio.equals(CRIT_PRECO_CRESC)) {

				do {
					j--;

				} while (produtos.get(j).getPreco() > x.getPreco());

				do {
					i++;

				} while (produtos.get(i).getPreco() < x.getPreco());
			}

			else if (criterio.equals(CRIT_ESTOQUE_CRESC)) {

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

				Produto temp = produtos.get(i); // substitui Produto temp = produtos[i];
				produtos.set(i, produtos.get(j)); // substitui produtos[i] = produtos[j];
				produtos.set(j, temp); // substitui produtos[j] = temp;

			} else
				return j;

		}
        
    }

    private int compara(Produto p1, Produto p2, String criterio) {

        if(criterio.equals(CRIT_DESC_DECRESC)) {
            return -1 * p1.getDescricao().compareToIgnoreCase(p2.getDescricao());
        } else if(criterio.equals(CRIT_PRECO_DECRESC)) {
            return -1 * Double.compare(p1.getPreco(), p2.getPreco());
        } else if(criterio.equals(CRIT_ESTOQUE_DECRESC)){
            return -1 * Integer.compare(p1.getQtdEstoque(), p2.getQtdEstoque());
        } else {
            throw new IllegalArgumentException("Criterio invalido!");
        }

    }

}