import java.util.*;
interface SortingStrategy
{
    public static final String CRIT_DESC_CRESC = "descricao_c";
	public static final String CRIT_PRECO_CRESC = "preco_c";
	public static final String CRIT_ESTOQUE_CRESC = "estoque_c";

	public static final String CRIT_DESC_DECRESC = "descricao_dc";
	public static final String CRIT_PRECO_DECRESC = "preco_dc";
	public static final String CRIT_ESTOQUE_DECRESC = "estoque_dc";

    public void ordena(List<Produto> produtos, int ini, int fim);
}
