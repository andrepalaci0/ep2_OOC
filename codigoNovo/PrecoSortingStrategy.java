public class PrecoSortingStrategy implements SortingAlgoStrategy{
    private SortingAlgoStrategy sortAlgo;
    public PrecoSortingStrategy(SortingAlgoStrategy algo)
    {
        this.sortAlgo = algo;
    }
    
    public void ordena(int ini, int fim)
    {
        this.sortAlgo.ordena(ini, fim);
        //tem q alterar oq ele recebe, pra ele ordenar por um tipo generico pré definido
    }
}