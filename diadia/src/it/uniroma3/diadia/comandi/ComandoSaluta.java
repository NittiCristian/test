package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;
import it.uniroma3.diadia.personaggi.Strega;

public class ComandoSaluta extends AbstractComando {

	@Override
	public void esegui(Partita partita) {

		AbstractPersonaggio personaggio;
		personaggio = partita.getStanzaCorrente().getPersonaggio();

		if (personaggio.getClass() == Strega.class) {

			((Strega) personaggio).setSalutata(true);
		}
		partita.getIo().mostraMessaggio(personaggio.saluta());
	}

	@Override
	public String getNome() {

		return "saluta";
	}

}
