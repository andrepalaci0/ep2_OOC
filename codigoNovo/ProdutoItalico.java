public class ProdutoItalico extends ProdutoDecorator{
    private String original;
    public ProdutoItalico(Produto produtoPadrao){
        super(produtoPadrao.getId(), produtoPadrao.getDescricao(), produtoPadrao.getCategoria(),produtoPadrao.getQtdEstoque(), produtoPadrao.getPreco());
        this.original = produtoPadrao.formataParaImpressao();
    }

    public String formataParaImpressao()
    {
        String textToItalic = original;
        String italic = "<span style=\"font-style:italic\"> ";
		
        //textToItalic = italic + textToItalic;
        //isso aqui nao ta exatamente certo, tem q fazer uma busca pelo
        //"style" e inserir dentro dele. vou fazer depois :)

        return textToItalic;
    }
}