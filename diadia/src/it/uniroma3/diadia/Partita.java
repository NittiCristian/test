package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.giocatore.Giocatore;

/**
 * Questa classe modella una partita del gioco
 *
 * @author  docente di POO
 * @see Stanza
 * @version base
 */

public class Partita {

	
	private Labirinto labirinto= new Labirinto("diadiaLabirinto.txt");
	private boolean finita;
	private Giocatore giocatore= new Giocatore();
	private IO io;
	
	public Partita(IO io){
		
		
		this.io=io;
		this.finita = false;
	}
	public Partita(Labirinto labirinto, IO io){
		
		this.labirinto=labirinto;
		this.io=io;
		this.finita = false;
	}
	
	/**
	 * Restituisce vero se e solo se la partita e' stata vinta
	 * @return vero se partita vinta
	 */
	public boolean vinta() {
		return this.labirinto.getStanzaCorrente()== this.labirinto.getStanzaVincente();
	}

	/**
	 * Restituisce vero se e solo se la partita e' finita
	 * @return vero se partita finita
	 */
	public boolean isFinita() {
		return finita || vinta() || (giocatore.getCfu() == 0);
	}

	/**
	 * Imposta la partita come finita
	 *
	 */
	public void setFinita() {
		this.finita = true;
	}


	public Labirinto getLabirinto() {
		return labirinto;
	}


	public void setLabirinto(Labirinto labirinto) {
		this.labirinto = labirinto;
	}


	public Giocatore getGiocatore() {
		return giocatore;
	}


	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}

	public Stanza getStanzaCorrente() {
		
		return this.labirinto.getStanzaCorrente();
	}

	public void setStanzaCorrente(Stanza prossimaStanza) {
		this.labirinto.setStanzaCorrente(prossimaStanza);
	}

	public boolean giocatoreIsVivo() {
		if(giocatore.getCfu()>0) 
			return true;
		return false;
	}

	public IO getIo() {
		return io;
	}	
}
