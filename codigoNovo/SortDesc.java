import java.util.*;

public class SortDesc implements SortingStrategy{
    private SortingStrategy sortAlgo;
    public SortDesc(SortingStrategy algo)
    {
        this.sortAlgo = algo;
    }
    
    public void ordena(List<Produto> list,int ini, int fim)
    {
        this.sortAlgo.ordena(list, ini, fim);
        //tem q alterar oq ele recebe, pra ele ordenar por um tipo generico pré definido
    }
}
