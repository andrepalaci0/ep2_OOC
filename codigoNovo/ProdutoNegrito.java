public class ProdutoNegrito extends ProdutoDecorator{
    private String original;
    public ProdutoNegrito(Produto produtoPadrao)
    {
        super(produtoPadrao.getId(), produtoPadrao.getDescricao(), produtoPadrao.getCategoria(), produtoPadrao.getQtdEstoque(), produtoPadrao.getPreco());
        this.original = produtoPadrao.formataParaImpressao();
    }
    public String formataParaImpressao()
    {
        String textToBold = original;
        String bold = "<span style=\"font-style:italic\"> ";
		
        //textToBold =  bold + textToItalic;
        //isso aqui nao ta exatamente certo, tem q fazer uma busca pelo
        //"style" e inserir dentro dele. vou fazer depois :)

        return textToBold;
    }
}