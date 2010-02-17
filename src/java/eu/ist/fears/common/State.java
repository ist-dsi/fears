package eu.ist.fears.common;

public enum State {

    Novo("Novo"), Planeado("Planeado"), Implementacao("Em implementa&ccedil;&atilde;o"), Completo("Completo"), Rejeitado(
	    "Rejeitado");

    protected String html;

    private State(String html) {
	this.html = html;
    }

    public String getHTML() {
	return html;
    }

    public String getListBoxHTML() {
	if (!this.toString().equals(State.Implementacao.toString()))
	    return html;
	else
	    return State.Implementacao.toString();
    }

}
