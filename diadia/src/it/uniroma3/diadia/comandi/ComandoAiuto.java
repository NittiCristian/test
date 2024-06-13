package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoAiuto extends AbstractComando {

	static final private String[] elencoComandi = {"vai", "aiuto", "fine","prendi","posa","guarda","saluta","interagisci","regala"};

	@Override
	public void esegui(Partita partita) {
		
		for(int i=0; i< elencoComandi.length; i++) 
			partita.getIo().mostraMessaggio(elencoComandi[i]+" ");
		partita.getIo().mostraMessaggio("");

	}

	@Override
	public String getNome() {
		
		return "aiuto";
	}




}
