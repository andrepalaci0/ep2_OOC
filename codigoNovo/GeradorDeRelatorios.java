
import java.io.PrintWriter;
import java.io.IOException;

import java.util.*;

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
	private int format_flags;

    public GeradorDeRelatorios(List<Produto> produtos, String algoritmo, String criterio, String filtro, String argFiltro, int format_flags) {

		this.produtos = new ArrayList<>(produtos); // faz uma copia dos produtos fornecidos, substitui o for que tinha para o vetor
        this.algoritmo = algoritmo;
        this.criterio = criterio;
        this.format_flags = format_flags;
        this.filtro = filtro;
        this.argFiltro = argFiltro;

    }

	private int particiona(int ini, int fim) {

		Produto x = produtos.get(ini); // subtitui Produto x = produtos[ini] pq nao da para acessar o indice direto na List
		int i = (ini - 1);
		int j = (fim + 1);

		while (true) {

			if (criterio.equals(CRIT_DESC_CRESC)) {

				do {
					j--;

				} while (produtos.get(j).getDescricao().compareToIgnoreCase(x.getDescricao()) > 0);

				do {
					i++;

				} while (produtos.get(i).getDescricao().compareToIgnoreCase(x.getDescricao()) < 0);
			} else if (criterio.equals(CRIT_PRECO_CRESC)) {

				do {
					j--;

				} while (produtos.get(j).getPreco() > x.getPreco());

				do {
					i++;

				} while (produtos.get(i).getPreco() < x.getPreco());
			}

			else if (criterio.equals(CRIT_ESTOQUE_CRESC)) {

				do {
					j--;

				} while (produtos.get(j).getQtdEstoque() > x.getQtdEstoque());

				do {
					i++;

				} while (produtos.get(i).getQtdEstoque() < x.getQtdEstoque());

			} else {

				throw new RuntimeException("Criterio invalido!");
			}

			if (i < j) {

				Produto temp = produtos.get(i); // substitui Produto temp = produtos[i];
				produtos.set(i, produtos.get(j)); // substitui produtos[i] = produtos[j];
				produtos.set(j, temp); // substitui produtos[j] = temp;

			} else
				return j;

		}

	}

	private void ordena(int ini, int fim) {

		if (algoritmo.equals(ALG_INSERTIONSORT)) {

			for (int i = ini; i <= fim; i++) {

				Produto x = produtos.get(i);
				int j = (i - 1);

				while (j >= ini) {

					if (criterio.equals(CRIT_DESC_CRESC)) {

						if (x.getDescricao().compareToIgnoreCase(produtos.get(j).getDescricao()) < 0) {

							produtos.set(j + 1, produtos.get(j)); // substitui produtos[j + 1] = produtos[j];
							j--;
						} else
							break;
					} else if (criterio.equals(CRIT_PRECO_CRESC)) {

						if (x.getPreco() < produtos.get(j).getPreco()) {

							produtos.set(j + 1, produtos.get(j)); // substitui produtos[j + 1] = produtos[j];
							j--;
						} else
							break;
					} else if (criterio.equals(CRIT_ESTOQUE_CRESC)) {

						if (x.getQtdEstoque() < produtos.get(j).getQtdEstoque()) {

							produtos.set(j + 1, produtos.get(j)); // substitui produtos[j + 1] = produtos[j];
							j--;
						} else
							break;
					} else
						throw new RuntimeException("Criterio invalido!");
				}

				produtos.set(j + 1, x); // substitui produtos[j + 1] = x;
			}
		} else if (algoritmo.equals(ALG_QUICKSORT)) {

			if (ini < fim) {

				int q = particiona(ini, fim);

				ordena(ini, q);
				ordena(q + 1, fim);
			}
		} else {
			throw new RuntimeException("Algoritmo invalido!");
		}
	}

	public void debug() { // produtos.size() substitui produtos.length


		System.out.println("Gerando relatório para lista contendo " + produtos.size() + " produto(s)");

		System.out.println("parametro filtro = '" + argFiltro + "'");
	}

	/*
	 * Coisas a se consertar no código:
	 * 
	 * 1. STRATEGY: algoritmo de ordenação: Quicksort e Insertion Sort.
	 * (Parcialmente Feito. Olhar arquivos que comecem com SortingAlgo)
	 * 
	 * 2. STRATEGY: critério de ordenação: ordem crescente pelo atributo descrição
	 * de um
	 * produto; ordem crescente pelo atributo preço de um produto; e ordem crescente
	 * pelo atributo
	 * quantidade em estoque de um produto.
	 * 
	 * 3. STRATEGY: critério de filtragem: todos (ou seja, todos os produtos entram
	 * na listagem gerada); produtos cujo estoque seja menor ou igual a uma certa
	 * quantidade; e produtos de uma determinada categoria. 
	 * 	-> ainda vou criar um strategy pra cada tipo de filto
	 * 
	 * 4. opções de formatação: padrão (nenhuma opção aplicada); itálico; e negrito.
	 * As formatações são implementadas usando tags HTML que aplicam o efeito
	 * desejado a um texto
	 */

	/*
	 * coisas a implementar no código:
	 * 
	 * critérios de ordenação em ordem decrescente para os atributos descrição,
	 * preço e estoque de um produto (são 3 critérios no total);
	 * 
	 * •critério de filtragem para selecionar produtos com preço dentro de um
	 * intervalo determinado;
	 * 
	 * •critério de filtragem para selecionar produtos cuja descrição contenha uma
	 * determinada subs-tring;
	 * 
	 * •decorador de formatação para definir a cor do texto referente a um produto;
	 * •carregar produtos de um arquivo CSV (exemplo acompanha enunciado) e
	 * salvá-los em umacoleção de produtos;
	 */
	private SortingAlgoStrategy getSortingAlgorithm(boolean crescente) {
		if (crescente) {
			if (algoritmo.equals(ALG_INSERTIONSORT)) {
				return new SortingAlgoCrescInsertionSort();
			} else if (algoritmo.equals(ALG_QUICKSORT)) {
				return new SortingAlgoCrescQuickSort();
			} else {
				throw new IllegalArgumentException("Algoritmo invalido!");
			}
		}else{
			if (algoritmo.equals(ALG_INSERTIONSORT)) {
				return new SortingAlgoDecrescInsertionSort(); 
			} else if (algoritmo.equals(ALG_QUICKSORT)) {
				return new SortingAlgoDecrescQuickSort();
			} else {
				throw new IllegalArgumentException("Algoritmo invalido!");
			}
		}
	}

	private SortingAlgoStrategy getSortStrategy() {
		SortingAlgoStrategy strategy;
		if (criterio.equals(CRIT_PRECO_CRESC)) {
			strategy = new PrecoSortingStrategy(getSortingAlgorithm(true));
		} else if (criterio.equals(CRIT_DESC_CRESC)) {
			strategy = new DescSortingStrategy(getSortingAlgorithm(true));
		} else if (criterio.equals(CRIT_ESTOQUE_CRESC)) {
			strategy = new QtdSortingStrategy(getSortingAlgorithm(true));
		} else if (criterio.equals(CRIT_DESC_DECRESC)) {
			strategy = new DescSortingStrategy(getSortingAlgorithm(false));
		} else if (criterio.equals(CRIT_ESTOQUE_DECRESC)) {
			strategy = new QtdSortingStrategy(getSortingAlgorithm(false));
		} else if (criterio.equals(CRIT_PRECO_DECRESC)) {
			strategy = new PrecoSortingStrategy(getSortingAlgorithm(false));
		} else {
			throw new IllegalArgumentException("Criterio invalido!");
		}
		return strategy;
	}

	public void geraRelatorio(String arquivoSaida) throws IOException {

		debug();
		/*
		 * essa funcao gera relatorio muito provavelmente quebra o
		 * Single Responsability Principle pq ela basicamente faz tudo que o código
		 * manda
		 * Acho que, dada certa explicação no relatório, da pra mudar bastante coisa
		 * aqui
		 * Foda que ainda nao sei exatamente o certo a se fazer
		 */

		// A FUNCAO ORDENA VAI SER UM TIPO STRATEGY, QUE VAI DEFINIDO DE ACORDO COM OS
		// PARAMETROS DO OBJETO

		// ficaria:
		SortingAlgoStrategy strategy = getSortStrategy();
		strategy.ordena(0, produtos.length - 1); // novo, utilizando strategy
		// tem q mudar oq essa bomba de ordenacao recebe.
		ordena(0, produtos.length - 1); // antigo

		PrintWriter out = new PrintWriter(arquivoSaida);

		out.println("<!DOCTYPE html><html>");
		out.println("<head><title>Relatorio de produtos</title></head>");
		out.println("<body>");
		out.println("Relatorio de Produtos:");
		out.println("<ul>");

		int count = 0;

		for (int i = 0; i < produtos.length; i++) {

			Produto p = produtos[i];
			boolean selecionado = false;

			if (filtro.equals(FILTRO_TODOS)) {

				selecionado = true;
			} else if (filtro.equals(FILTRO_ESTOQUE_MENOR_OU_IQUAL_A)) {

				if (p.getQtdEstoque() <= Integer.parseInt(argFiltro))
					selecionado = true;
			} else if (filtro.equals(FILTRO_CATEGORIA_IGUAL_A)) {

				if (p.getCategoria().equalsIgnoreCase(argFiltro))
					selecionado = true;
			} else {
				throw new RuntimeException("Filtro invalido!");
			}

			if (selecionado) {

				out.print("<li>");

				if ((format_flags & FORMATO_ITALICO) > 0) {

					out.print("<span style=\"font-style:italic\">");
				}

				if ((format_flags & FORMATO_NEGRITO) > 0) {

					out.print("<span style=\"font-weight:bold\">");
				}

				out.print(p.formataParaImpressao());

				if ((format_flags & FORMATO_NEGRITO) > 0) {

					out.print("</span>");
				}

				if ((format_flags & FORMATO_ITALICO) > 0) {

					out.print("</span>");
				}

				out.println("</li>");
				count++;
			}
		}

		out.println("</ul>");
		out.println(count + " produtos listados, de um total de " + produtos.length + ".");
		out.println("</body>");
		out.println("</html>");
		out.close();
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
		produtos.add(new ProdutoPadrao(28, "Disquetes Maxell 5.25 polegadas (caixa com 10 unidades)", "Informatica", 23, 49.00));
		produtos.add(new ProdutoPadrao(29, "Alone in The Dark", "Games", 11, 59.00));
		produtos.add(new ProdutoPadrao(30, "The Art of Computer Programming Vol. 1", "Livros", 3, 240.00));
		produtos.add(new ProdutoPadrao(31, "The Art of Computer Programming Vol. 2", "Livros", 2, 200.00));
		produtos.add(new ProdutoPadrao(32, "The Art of Computer Programming Vol. 3", "Livros", 4, 270.00));
	
		return produtos;

	}

	public static void main(String[] args) {

		if (args.length < 4) {

			System.out.println("Uso:");
			System.out.println("\tjava " + GeradorDeRelatorios.class.getName()
					+ " <algoritmo> <critério de ordenação> <critério de filtragem> <parâmetro de filtragem> <opções de formatação>");
			System.out.println("Onde:");
			System.out.println("\talgoritmo: 'quick' ou 'insertion'");
			System.out.println("\tcriterio de ordenação: 'preco_c' ou 'descricao_c' ou 'estoque_c'");
			System.out.println("\tcriterio de filtragem: 'todos' ou 'estoque_menor_igual' ou 'categoria_igual'");
			System.out.println("\tparâmetro de filtragem: argumentos adicionais necessários para a filtragem");
			System.out.println("\topções de formatação: 'negrito' e/ou 'italico'");
			System.out.println();
			System.exit(1);
		}

		String opcao_algoritmo = args[0];
		String opcao_criterio_ord = args[1];
		String opcao_criterio_filtro = args[2];
		String opcao_parametro_filtro = args[3];

		String[] opcoes_formatacao = new String[2];
		opcoes_formatacao[0] = args.length > 4 ? args[4] : null;
		opcoes_formatacao[1] = args.length > 5 ? args[5] : null;
		int formato = FORMATO_PADRAO;

		for (int i = 0; i < opcoes_formatacao.length; i++) {

			String op = opcoes_formatacao[i];
			formato |= (op != null
					? op.equals("negrito") ? FORMATO_NEGRITO : (op.equals("italico") ? FORMATO_ITALICO : 0)
					: 0);
		}

		GeradorDeRelatorios gdr = new GeradorDeRelatorios(carregaProdutos(),
				opcao_algoritmo,
				opcao_criterio_ord,
				opcao_criterio_filtro,
				opcao_parametro_filtro,
				formato);

		try {
			gdr.geraRelatorio("saida.html");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
