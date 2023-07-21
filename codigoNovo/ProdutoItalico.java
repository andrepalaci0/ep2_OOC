public class ProdutoItalico extends ProdutoDecorator{
    private String original;
    public ProdutoItalico(Produto produtoPadrao){
        super(produtoPadrao.getId(), produtoPadrao.getDescricao(), produtoPadrao.getCategoria(),produtoPadrao.getQtdEstoque(), produtoPadrao.getPreco());
        this.original = produtoPadrao.formataParaImpressao();
    }

    public String formataParaImpressao()
    {
        String textToItalic = original;
        textToItalic = addItalic(textToItalic);
        textToItalic = addSpan(textToItalic);
        //textToItalic = italic + textToItalic;
        //isso aqui nao ta exatamente certo, tem q fazer uma busca pelo
        //"style" e inserir dentro dele. vou fazer depois :)
        return textToItalic;
    }

    private String addItalic(String string) {
        String aux;
        if (!hasStyle(string)) {
            aux = "<span style=\" font-style:italic; \">";
            return (aux + string);
        } else {
            String style = "style=\"";
            String insert = "font-style:italic; ";
            int index = string.indexOf(style);
            String out = string.substring(0, index + style.length()) + insert
                    + string.substring(index + style.length());
            return out;
        }
    }

}