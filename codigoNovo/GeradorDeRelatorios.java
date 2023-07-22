
import java.io.PrintWriter;
import java.io.IOException;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GeradorDeRelatorios {

	public static final String ALG_INSERTIONSORT = "insertion";
	public static final String ALG_QUICKSORT = "quick";

	public static final String CRIT_DESC_CRESC = "descricao_c";
	public static final String CRIT_PRECO_CRESC = "preco_c";
	public static final String CRIT_ESTOQUE_CRESC = "estoque_c";

	public static final String CRIT_DESC_DECRESC = "descricao_dc";
	public static final String CRIT_PRECO_DECRESC = "preco_dc";
	public static final String CRIT_ESTOQUE_DECRESC = "estoque_dc";

	public static final String FILTRO_TODOS = "todos";
	public static final String FILTRO_ESTOQUE_MENOR_OU_IQUAL_A = "estoque_menor_igual";
	public static final String FILTRO_CATEGORIA_IGUAL_A = "categoria_igual";
	public static final String FILTRO_PRECO_INTERVALO = "preco_intervalo";
	public static final String FILTRO_DESCRICAO_SUBSTRING = "descricao_sub";

	public static final String CRITERIO_COR_DESC = "desc_cor";
	public static final String CRITERIO_COR_ID = "id_cor";

	public static final String CRITERIO_FORMATACAO_NEGRITO = "negrito";
	public static final String CRITERIO_FORMATACAO_ITALICO = "italico";

	// operador bit a bit "ou" pode ser usado para combinar mais de
	// um estilo de formatacao simultaneamente (veja como no main)
	public static final int FORMATO_PADRAO = 0b0000;
	public static final int FORMATO_NEGRITO = 0b0001;
	public static final int FORMATO_ITALICO = 0b0010;

	private List<Produto> produtos; // otimizacao usando collections, substitui o private Produto[] produtos
	private String algoritmo;
	private String criterio;
	private String filtro;
	private String argFiltro;
	//private String criterioCor;
	//private String cor;
	//private String argsCor;
	//private String critFormatacao;
	//private String tipoFormatacao;
	//private String parametroTipoFormatacao;

	public GeradorDeRelatorios(List<Produto> produtos, String algoritmo, String criterio, String filtro,
			String argFiltro) {

		this.produtos = new ArrayList<>(produtos); // faz uma copia dos produtos fornecidos, substitui o for que tinha
													// para o vetor
		this.algoritmo = algoritmo;
		this.criterio = criterio;
		this.filtro = filtro;
		this.argFiltro = argFiltro;
	}

	public void debug() { // produtos.size() substitui produtos.length

		System.out.println("Gerando relatório para lista contendo " + produtos.size() + " produto(s)");

		System.out.println("parametro filtro = '" + argFiltro + "'");
	}

	private SortingStrategy getSortingAlgorithm(boolean crescente) {
		if (crescente) {
			if (algoritmo.equals(ALG_INSERTIONSORT)) {
				return new InsertionSortCresc(criterio);
			} else if (algoritmo.equals(ALG_QUICKSORT)) {
				return new QuickSortCresc(criterio);
			} else {
				throw new IllegalArgumentException("Algoritmo invalido!");
			}
		} else {
			if (algoritmo.equals(ALG_INSERTIONSORT)) {
				return new InsertionSortDecresc(criterio);
			} else if (algoritmo.equals(ALG_QUICKSORT)) {
				return new QuickSortDecresc(criterio);
			} else {
				throw new IllegalArgumentException("Algoritmo invalido!");
			}
		}
	}

	private SortingStrategy getSortStrategy() {
		SortingStrategy strategy;
		if (criterio.equals(CRIT_PRECO_CRESC)) {
			strategy = new SortPreco(getSortingAlgorithm(true));
		} else if (criterio.equals(CRIT_DESC_CRESC)) {
			strategy = new SortDesc(getSortingAlgorithm(true));
		} else if (criterio.equals(CRIT_ESTOQUE_CRESC)) {
			strategy = new SortQtd(getSortingAlgorithm(true));
		} else if (criterio.equals(CRIT_DESC_DECRESC)) {
			strategy = new SortDesc(getSortingAlgorithm(false));
		} else if (criterio.equals(CRIT_ESTOQUE_DECRESC)) {
			strategy = new SortQtd(getSortingAlgorithm(false));
		} else if (criterio.equals(CRIT_PRECO_DECRESC)) {
			strategy = new SortPreco(getSortingAlgorithm(false));
		} else {
			throw new IllegalArgumentException("Criterio invalido!");
		}
		return strategy;
	}

	private FilterStrategy getFilterStrategy() {
		FilterStrategy filter;
		if (filtro.equals(FILTRO_CATEGORIA_IGUAL_A)) {
			filter = new FilterCategoria(argFiltro);
		} else if (filtro.equals(FILTRO_ESTOQUE_MENOR_OU_IQUAL_A)) {
			filter = new FilterEstoque(argFiltro);
		} else if (filtro.equals(FILTRO_TODOS)) {
			filter = new FilterTodos();
		} else if (filtro.equals(FILTRO_DESCRICAO_SUBSTRING)) {
			filter = new FilterDesc(argFiltro);
		} else if (filtro.equals(FILTRO_PRECO_INTERVALO)) {
			filter = new FilterInterval(argFiltro);
		} else {
			throw new IllegalArgumentException("Criterio invalido!");
		}
		return filter;
	}

	public void geraRelatorio(String arquivoSaida) throws IOException {

		debug();
		SortingStrategy strategy = getSortStrategy();
		strategy.ordena(produtos, 0, produtos.size() - 1); // novo, utilizando strategy
		List<Produto> filteredList = new ArrayList<Produto>();
		FilterStrategy filter = getFilterStrategy();
		filteredList = filter.filtra(produtos); // lista ja filtrada de acordo com as especificações passadas
		
		PrintWriter out = new PrintWriter(arquivoSaida);
		out.println("<!DOCTYPE html><html>");
		out.println("<head><title>Relatorio de produtos</title></head>");
		out.println("<body>");
		out.println("Relatorio de Produtos:");
		out.println("<ul>");
		
		int count = 0;
		for (Produto p : filteredList) {
			count++;
			out.println("<li>" + p.formataParaImpressao() + "</li>");
		}

		out.println("</ul>");
		out.println(count + " produtos listados, de um total de " + produtos.size() + ".");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	public static List<Produto> recebeCarregaProdutos(File csvFile) {
		List<Produto> list = new ArrayList<Produto>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			String linha = br.readLine();
			linha = br.readLine();
			while (linha != null) {

				String[] vetor = linha.split(", ");
				Integer id = Integer.parseInt(vetor[0]);
				String descricao = vetor[1];
				String categoria = vetor[2];
				Integer qntEstoque = Integer.parseInt(vetor[3]);
				Double preco = Double.parseDouble(vetor[4]);
				Produto prod = new ProdutoPadrao(id, descricao, categoria, qntEstoque, preco);

				if (vetor[5].equals("true"))
					prod = new ProdutoNegrito(prod);
				if(vetor[6].equals("true"))
					prod = new ProdutoItalico(prod);
				
				prod = new ProdutoCor(prod, vetor[7]);

				list.add(prod);

				linha = br.readLine();
			}

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return list;
	}

	public static List<Produto> carregaProdutos() {

		List<Produto> produtos = new ArrayList<>();

		produtos.add(new ProdutoPadrao(1, "O Hobbit", "Livros", 2, 34.90));
		produtos.add(new ProdutoPadrao(2, "Notebook Core i7", "Informatica", 5, 1999.90));
		produtos.add(new ProdutoPadrao(3, "Resident Evil 4", "Games", 7, 79.90));
		produtos.add(new ProdutoPadrao(4, "iPhone", "Telefonia", 8, 4999.90));
		produtos.add(new ProdutoPadrao(5, "Calculo I", "Livros", 20, 55.00));
		produtos.add(new ProdutoPadrao(6, "Power Glove", "Games", 3, 499.90));
		produtos.add(new ProdutoPadrao(7, "Microsoft HoloLens", "Informatica", 1, 19900.00));
		produtos.add(new ProdutoPadrao(8, "OpenGL Programming Guide", "Livros", 4, 89.90));
		produtos.add(new ProdutoPadrao(9, "Vectrex", "Games", 1, 799.90));
		produtos.add(new ProdutoPadrao(10, "Carregador iPhone", "Telefonia", 15, 499.90));
		produtos.add(new ProdutoPadrao(11, "Introduction to Algorithms", "Livros", 7, 315.00));
		produtos.add(new ProdutoPadrao(12, "Daytona USA (Arcade)", "Games", 1, 12000.00));
		produtos.add(new ProdutoPadrao(13, "Neuromancer", "Livros", 5, 45.00));
		produtos.add(new ProdutoPadrao(14, "Nokia 3100", "Telefonia", 4, 249.99));
		produtos.add(new ProdutoPadrao(15, "Oculus Rift", "Games", 1, 3600.00));
		produtos.add(new ProdutoPadrao(16, "Trackball Logitech", "Informatica", 1, 250.00));
		produtos.add(new ProdutoPadrao(17, "After Burner II (Arcade)", "Games", 2, 8900.0));
		produtos.add(new ProdutoPadrao(18, "Assembly for Dummies", "Livros", 30, 129.90));
		produtos.add(new ProdutoPadrao(19, "iPhone (usado)", "Telefonia", 3, 3999.90));
		produtos.add(new ProdutoPadrao(20, "Game Programming Patterns", "Livros", 1, 299.90));
		produtos.add(new ProdutoPadrao(21, "Playstation 2", "Games", 10, 499.90));
		produtos.add(new ProdutoPadrao(22, "Carregador Nokia", "Telefonia", 14, 89.00));
		produtos.add(new ProdutoPadrao(23, "Placa Aceleradora Voodoo 2", "Informatica", 4, 189.00));
		produtos.add(new ProdutoPadrao(24, "Stunts", "Games", 3, 19.90));
		produtos.add(new ProdutoPadrao(25, "Carregador Generico", "Telefonia", 9, 30.00));
		produtos.add(new ProdutoPadrao(26, "Monitor VGA 14 polegadas", "Informatica", 2, 199.90));
		produtos.add(new ProdutoPadrao(27, "Nokia N-Gage", "Telefonia", 9, 699.00));
		produtos.add(new ProdutoPadrao(28, "Disquetes Maxell 5.25 polegadas (caixa com 10 unidades)", "Informatica", 23,
				49.00));
		produtos.add(new ProdutoPadrao(29, "Alone in The Dark", "Games", 11, 59.00));
		produtos.add(new ProdutoPadrao(30, "The Art of Computer Programming Vol. 1", "Livros", 3, 240.00));
		produtos.add(new ProdutoPadrao(31, "The Art of Computer Programming Vol. 2", "Livros", 2, 200.00));
		produtos.add(new ProdutoPadrao(32, "The Art of Computer Programming Vol. 3", "Livros", 4, 270.00));

		return produtos;

	}

	public static void main(String[] args) {

		if (args.length < 4) {

			System.out.println("Uso:");
			System.out.println("\tjava " + GeradorDeRelatorios.class.getName() + " <algoritmo> <critério de ordenação> <critério de filtragem> <parâmetro de filtragem> <csv File> <opções de formatação>");
			System.out.println("Onde:");
			System.out.println("\talgoritmo: 'quick' ou 'insertion'");
			System.out.println("\tcriterio de ordenação: 'preco_c' ou 'descricao_c' ou 'estoque_c' e para ordenação decrescente: 'preco_dc' ou 'descricao_dc' ou 'estoque_dc'");
			System.out.println("\tcriterio de filtragem: 'todos' ou 'estoque_menor_igual' ou 'categoria_igual' ou 'preco_intervalo' ou 'descricao_sub'"); 
			System.out.println("\tATENÇÃO: Caso seja selecionado 'preco_intervalo' , os parametros devem seguir o modelo: '10.35-31.50'");
			System.out.println("\tCom PONTO (.) e com '-' separando os dois numeros");
			System.out.println("\tparâmetro de filtragem: argumentos adicionais necessários para a filtragem"); 
			System.out.println("\tcsv File: caminho do arquivo .csv contendo os produtos");
			System.out.println("\topções de formatação: 'negrito' e/ou 'italico'");
			System.out.println();
			System.exit(1);
		}

		String opcao_algoritmo = args[0];
		String opcao_criterio_ord = args[1];
		String opcao_criterio_filtro = args[2];
		String opcao_parametro_filtro = args[3];
		String filePath = args[4];
		File csvFile = new File(filePath);
		
		
		String [] opcoes_formatacao = new String[2];
		opcoes_formatacao[0] = args.length > 5 ? args[5] : null;
		opcoes_formatacao[1] = args.length > 6 ? args[6] : null;
		int formato = FORMATO_PADRAO;
		
		for(int i = 0; i < opcoes_formatacao.length; i++) {

			String op = opcoes_formatacao[i];
			formato |= (op != null ? op.equals("negrito") ? FORMATO_NEGRITO : (op.equals("italico") ? FORMATO_ITALICO : 0) : 0); 
		}
		// já pode ser usado:
		// new GeradorDeRelatorios(recebeCarregaProdutos(csvFile))

		GeradorDeRelatorios gdr = new GeradorDeRelatorios(	recebeCarregaProdutos(csvFile), 
									opcao_algoritmo,
									opcao_criterio_ord,
									opcao_criterio_filtro,
									opcao_parametro_filtro
								 );

		try {
			gdr.geraRelatorio("saida.html");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
