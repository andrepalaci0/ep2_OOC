import java.util.ArrayList;
import java.util.List;

public class FilterEstoque implements FilterStrategy{
    private String especCriterio;
    public FilterEstoque(String especCString)
    {
        this.especCriterio = especCString;
    }

    public List<Produto> filtra(List<Produto> list){
        List<Produto> newList = new ArrayList<Produto>();
        int valorMax = Integer.parseInt(especCriterio);
        for (Produto p : list) {
            if(p.getQtdEstoque() <= valorMax)
            {
                newList.add(p);
            }
        }
        return newList;
    }
}