public class ProdutoCor extends ProdutoDecorator{
    private String original;
    private String color;
    public ProdutoCor(Produto produtoPadrao, String cor)
    {
        super(produtoPadrao.getId(), produtoPadrao.getDescricao(), produtoPadrao.getCategoria(), produtoPadrao.getQtdEstoque(), produtoPadrao.getPreco());
        this.original = produtoPadrao.formataParaImpressao();
        this.color = cor;
    }

    public String formataParaImpressao()
    {
        String textAddColor = original;
        textAddColor = addColor(textAddColor);
        textAddColor = addSpan(textAddColor);
        return textAddColor;
    }

    private String addColor(String string) {
        String preColor = " color:" + color;
        if (!hasStyle(string)) {
            String aux = "<span style=\""+ preColor + "; \">";
            return (aux + string);
        } else {
            String style = "style=\"";
            String insert = "color:"+ color +"; ";
            int index = string.indexOf(style);
            String out = string.substring(0, index + style.length()) + insert
                    + string.substring(index + style.length());
            return out;
        }
    }
}
