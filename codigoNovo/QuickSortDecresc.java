import java.util.*;
public class QuickSortDecresc implements SortingStrategy
{
    
    public void ordena(List <Produto> produtos, int ini, int fim, String criterio) {

        if (ini < fim) {

            int q = particiona(produtos, criterio, ini, fim);

            ordena(produtos, ini, q, criterio);
            ordena(produtos, q + 1, fim, criterio);
        }

    }

    private int particiona(List<Produto> produtos, String criterio, int ini, int fim) {
        Produto x = produtos.get(ini); // subtitui Produto x = produtos[ini] pq nao da para acessar o indice direto na List
		int i = (ini - 1);
		int j = (fim + 1);

		while (true) {

			do{
				j--;
			} while(compara(produto.get(j)), x, criterio) > 0);

			do {
				i++;
			} while(compara(produto.get(i), x, criterio) < 0);

			if(i < j){
				Produto temp = produtos.get(i);
				produtos.set(i, produtos.get(j));
				produtos.set(j, temp);
			} else {
				return j;
			}

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