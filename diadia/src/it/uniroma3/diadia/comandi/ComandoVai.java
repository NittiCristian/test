package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;

/**
 * Questa classe modella un comando.
 * Un comando consiste al piu' di due parole:
 * il nome del comando ed un parametro
 * su cui si applica il comando.
 * (Ad es. alla riga digitata dall'utente "vai nord"
 *  corrisponde un comando di nome "vai" e parametro "nord").
 *
 * @author  docente di POO
 * @version base
 */

public class ComandoVai extends AbstractComando{
	
	private String direzione;
	
	@Override
	public void esegui(Partita partita) {
		
		Stanza stanzaCorrente = partita.getStanzaCorrente();
		Stanza prossimaStanza = null;
		
		if(this.direzione==null) {
			partita.getIo().mostraMessaggio("Dove vuoi andare ?\n   Devi specificare una direzione ");
		return;
		}
		
		prossimaStanza = stanzaCorrente.getStanzaAdiacente(this.direzione);
		
		if (prossimaStanza == null) {
			partita.getIo().mostraMessaggio("Direzione inesistente");
			return;
		}
			
			partita.setStanzaCorrente(prossimaStanza);
			partita.getIo().mostraMessaggio(partita.getStanzaCorrente().getNome());
			partita.getGiocatore().setCfu(partita.getGiocatore().getCfu()-1);
		
	}

	public void setParametro(String parametro) {
		
		this.direzione=parametro;
	}
	public String getParametro() {
		
		return this.direzione;
	}

	@Override
	public String getNome() {
		
		return "vai";
	}
}