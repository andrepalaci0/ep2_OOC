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
        textToBold = addBold(textToBold);
        textToBold = addSpan(textToBold);

        return textToBold;
    }
    
    private String addBold(String string) {
        String aux;
        if (!hasStyle(string)) {
            aux = "<span style=\" font-weight:bold; \">";
            return (aux + string);
        } else {
            String style = "style=\"";
            String insert = " font-weight:bold;";
            int index = string.indexOf(style);
            String out = string.substring(0, index + style.length()) + insert
                    + string.substring(index + style.length());
            return out;
        }
    }
}