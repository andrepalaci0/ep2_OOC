import java.util.ArrayList;
import java.util.List;

public class FilterInterval implements FilterStrategy {
    private double low;
    private double high;

    public FilterInterval(String args) // DEVE SER NESSE FORMATO: n1-n2 , ex: 10.44-35.66 COM PONTO PELO AMOR DE DEUS
                                       // NAO COM VIRGULA
    {
        String[] aux = args.split("-");
        double auxLow = 0;
        double auxHigh = 0;
        try {
            auxLow = Double.parseDouble(aux[0]);
            auxHigh = Double.parseDouble(aux[1]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ERRO: Formato do argumento de filtragem inválido");
        }
        this.low = auxLow;
        this.high = auxHigh;
    }

    public List<Produto> filtra(List<Produto> list) {
        List<Produto> newList = new ArrayList<Produto>();
        for (Produto p : list) {
            if (p.getPreco() >= low && p.getPreco() <= high) {
                newList.add(p);
            }
        }
        return newList;

    }
}