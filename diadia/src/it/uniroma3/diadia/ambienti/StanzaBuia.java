package it.uniroma3.diadia.ambienti;

public class StanzaBuia extends Stanza{
	
	final static private String ATTREZZO_BUIO_DEFAULT="torcia"; 
	
	private String attrezzoBuio="torcia";
	
	public StanzaBuia(String nome) {
		this(nome, ATTREZZO_BUIO_DEFAULT);
		
	}
	public StanzaBuia(String nome, String attrezzoBuio) {
		
		super(nome);
		this.attrezzoBuio=attrezzoBuio;
		
	}
	@Override
	public String getDescrizione() {
		
		if(this.hasAttrezzo(attrezzoBuio))
        return super.getDescrizione();
		else
		return "Qui c'è un buio :o";
    }
	public void setAttrezzoBuio(String attrezzoBuio) {
		this.attrezzoBuio = attrezzoBuio;
	}
}
