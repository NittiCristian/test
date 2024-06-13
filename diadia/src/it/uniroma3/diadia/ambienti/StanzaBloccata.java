package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza{


	private String attrezzoSblocco;
	private String direzioneBloccata;
	



	public StanzaBloccata(String nome, String attrezzoSblocco, String direzioneBloccata) {
		
		super(nome);
		this.attrezzoSblocco=attrezzoSblocco;
		this.direzioneBloccata=direzioneBloccata;
	}

	public void setAttrezzoSblocco(String attrezzoSblocco) {
		this.attrezzoSblocco = attrezzoSblocco;
	}

	public void setDirezioneBloccata(String direzioneBloccata) {
		this.direzioneBloccata = direzioneBloccata;
	}

	@Override
	public Stanza getStanzaAdiacente(String direzione) {
		
		if(direzioneBloccata.equals(direzione)) {
			
			if(hasAttrezzo(attrezzoSblocco))
				return super.getStanzaAdiacente(direzione);
			else 
				return this;
		}else
			return super.getStanzaAdiacente(direzione);
		

	}
	@Override
	public String getDescrizione() {
		
		if(this.hasAttrezzo(attrezzoSblocco))
        return super.getDescrizione();
		else
		return "Per sbloccare la direzione "
					+direzioneBloccata
					+" devi posare l'attrezzo "
					+attrezzoSblocco +"\n"
					+super.getDescrizione();
    }
}
