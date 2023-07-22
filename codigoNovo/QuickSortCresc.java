import java.util.*;
public class QuickSortCresc implements SortingStrategy {

    private String criterio;
    public QuickSortCresc(String criterio)
    {
        this.criterio = criterio;
    }

    public void ordena(List <Produto> produtos, int ini, int fim) {

        if (ini < fim) {

            int q = particiona(produtos, ini, fim);

            ordena(produtos, ini, q);
            ordena(produtos, q + 1, fim);
        }

    }

	private int particiona(List<Produto> produtos, int ini, int fim) {
        Produto x = produtos.get(ini); 
		int i = (ini - 1);
		int j = (fim + 1);

		while (true) {

			do{
				j--;
			} while(compara(produtos.get(j), x, criterio) > 0);

			do {
				i++;
			} while(compara(produtos.get(i), x, criterio) < 0);

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
