import java.util.ArrayList;
import java.util.List;

public class FilterDesc implements FilterStrategy {
    private String compare;

    public FilterDesc(String compareString) {
        this.compare = compareString;
    }

    private boolean hasString(Produto p) {
        String aux = compare.toLowerCase();
        String auxProduto = p.getDescricao().toLowerCase();
        int index = auxProduto.indexOf(aux);
        if (index == -1)
            return false;
        return true;
    }

    public List<Produto> filtra(List<Produto> list) {
        List<Produto> newList = new ArrayList<Produto>();
        for (Produto p : list) {
            if (hasString(p)) {
                newList.add(p);
            }
        }
        return newList;
    }
}