package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoGuarda extends AbstractComando {

	@Override
	public void esegui(Partita partita) {
	
		partita.getIo().mostraMessaggio(partita.getStanzaCorrente().getDescrizione());
		partita.getIo().mostraMessaggio("Cfu: "+partita.getGiocatore().getCfu());
		partita.getIo().mostraMessaggio(partita.getGiocatore().getBorsa().getContenutoRaggruppatoPerPeso().toString());
		partita.getIo().mostraMessaggio(partita.getGiocatore().getBorsa().getContenutoOrdinatoPerPeso().toString());
		partita.getIo().mostraMessaggio(partita.getGiocatore().getBorsa().getContenutoOrdinatoPerNome().toString());
	}


	@Override
	public String getNome() {
		
		return "guarda";
	}

}
