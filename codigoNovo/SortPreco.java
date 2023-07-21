import java.util.*;

public class SortPreco implements SortingStrategy{
    private SortingStrategy sortAlgo;
    public SortPreco(SortingStrategy algo)
    {
        this.sortAlgo = algo;
    }
    
    public void ordena(List<Produto> list, int ini, int fim)
    {
        this.sortAlgo.ordena(list,ini, fim);
        //tem q alterar oq ele recebe, pra ele ordenar por um tipo generico pré definido
    }
}