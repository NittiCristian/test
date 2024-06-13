package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoNonValido extends AbstractComando {

	@Override
	public void esegui(Partita partita) {

		partita.getIo().mostraMessaggio("Comando non valido \n");

	}

	@Override
	public String getNome() {

		return "non valido";
	}

}
