package eu.ist.fears.client.common;

public enum State {

	Novo("Novo"),
    Planeado("Planeado"),
    Implementacao("Em implementa&ccedil;&atilde;o"),
    Completo("Completo");
    
    protected String _html;
    
    
    private State(String html){
		_html=html;
	}
	
    public String getHTML(){
    	return _html;
    }
	
}
