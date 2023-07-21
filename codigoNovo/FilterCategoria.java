import java.util.ArrayList;
import java.util.List;

public class FilterCategoria implements FilterStrategy {
    private String categoria;
    public FilterCategoria(String categoriaString){
        this.categoria = categoriaString;
    }

    public List<Produto> filtra(List<Produto> list) {
        List<Produto> newList = new ArrayList<Produto>();
        for (Produto p : list) {
            if(p.getCategoria().equalsIgnoreCase(categoria))
            {
                newList.add(p);
            }
        }
        return newList;
    }
}
