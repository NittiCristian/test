package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoRegala extends AbstractComando{

	private String nomeAttrezzo;

	@Override
	public void esegui(Partita partita) {
		
		if(nomeAttrezzo!=null) {
		Attrezzo attrezzo=partita.getGiocatore().getBorsa().getAttrezzo(nomeAttrezzo);
		
		partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);
	
		partita.getIo().mostraMessaggio(partita.getStanzaCorrente().getPersonaggio().riceviRegalo(attrezzo, partita));
		}
	}

	@Override
	public String getNome() {
		
		return "regala";
	}
	
	@Override
	public void setParametro(String parametro) {
		
		this.nomeAttrezzo=parametro;
	}

}
