package it.uniroma3.diadia.ambienti;

import java.io.FileNotFoundException;
import java.util.HashMap;

import it.uniroma3.diadia.CaricatoreLabirinto;
import it.uniroma3.diadia.FormatoFileNonValidoException;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;
import it.uniroma3.diadia.personaggi.Strega;

public class Labirinto {

	private Stanza stanzaCorrente;
	private Stanza stanzaVincente;


	public Stanza getStanzaVincente() {
		return stanzaVincente;
	}
	public void setStanzaVincente(Stanza stanzaVincente) {
		this.stanzaVincente = stanzaVincente;
	}

	public void setStanzaCorrente(Stanza stanzaCorrente) {
		this.stanzaCorrente = stanzaCorrente;
	}

	public Stanza getStanzaCorrente() {
		return this.stanzaCorrente;
	}

	/**
	 * Crea tutte le stanze e le porte di collegamento
	 * @throws FileNotFoundException 
	 * @throws FormatoFileNonValidoException 
	 */
	public Labirinto(String nomeFile) {

		CaricatoreLabirinto c = null;
		try {
			c = new CaricatoreLabirinto(nomeFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			c.carica();
		} catch (FormatoFileNonValidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.stanzaCorrente = c.getStanzaIniziale();
		this.stanzaVincente = c.getStanzaVincente();
	}



	public Labirinto() {

	}
	
	public void creaStanze() {

		/* crea gli attrezzi */
		Attrezzo lanterna = new Attrezzo("lanterna",3);
		Attrezzo osso = new Attrezzo("osso",1);
		Attrezzo sasso = new Attrezzo("sasso",2);
		Attrezzo torcia = new Attrezzo("torcia",4);
		Attrezzo chiave = new Attrezzo("chiave",1);
		Attrezzo martello= new Attrezzo("martello",3);

		/* crea stanze del labirinto */
		Stanza atrio = new Stanza("Atrio");
		Stanza aulaN11 = new Stanza("Aula N11");
		Stanza aulaN10 = new Stanza("Aula N10");
		Stanza laboratorio = new Stanza("Laboratorio Campus");
		Stanza biblioteca = new Stanza("Biblioteca");
		StanzaMagica aulaN18= new StanzaMagica("Aula N18");
		StanzaBuia bagno= new StanzaBuia("Bagno");
		StanzaBloccata ufficio=new StanzaBloccata("ufficio", "chiave", "nord");

		AbstractPersonaggio Strega= new Strega("Morgana","Strega cattiva");	
		/* collega le stanze */
		atrio.impostaStanzaAdiacente("nord", biblioteca);
		atrio.impostaStanzaAdiacente("est", aulaN11);
		atrio.impostaStanzaAdiacente("sud", aulaN10);
		atrio.impostaStanzaAdiacente("ovest", laboratorio);
		
		aulaN11.impostaStanzaAdiacente("est", laboratorio);
		aulaN11.impostaStanzaAdiacente("ovest", atrio);
		aulaN11.impostaStanzaAdiacente("sud", aulaN18);
		aulaN11.impostaStanzaAdiacente("nord", bagno);
		
		aulaN10.impostaStanzaAdiacente("nord", atrio);
		aulaN10.impostaStanzaAdiacente("est", aulaN11);
		aulaN10.impostaStanzaAdiacente("ovest", laboratorio);
		aulaN10.impostaStanzaAdiacente("sud", ufficio);
		
		laboratorio.impostaStanzaAdiacente("est", atrio);
		laboratorio.impostaStanzaAdiacente("ovest", aulaN11);
		
		biblioteca.impostaStanzaAdiacente("sud", atrio);

		aulaN18.impostaStanzaAdiacente("nord", aulaN11);
		
		bagno.impostaStanzaAdiacente("sud", aulaN11);
		

		ufficio.impostaStanzaAdiacente("nord", aulaN10);
		

		/* pone gli attrezzi nelle stanze */
		aulaN10.addAttrezzo(lanterna);
		atrio.addAttrezzo(osso);

		atrio.addAttrezzo(martello);
		aulaN18.addAttrezzo(sasso);
		bagno.addAttrezzo(torcia);
		ufficio.addAttrezzo(chiave);

		//pone i personaggi nelle stanze


		laboratorio.setPersonaggio(Strega);
		// il gioco comincia nell'atrio
		stanzaCorrente = atrio;  
		stanzaVincente = biblioteca;

	}



	public static LabirintoBuilder newBuilder() {
		return new Labirinto.LabirintoBuilder();
	}

	public static class LabirintoBuilder {

		private Labirinto labirinto= new Labirinto();
		private HashMap<String,Stanza> listaStanze= new HashMap<>();
		private Stanza ultimaStanza;

		private LabirintoBuilder(String nomeFile) {
			this.labirinto = new Labirinto(nomeFile);
			this.listaStanze = new HashMap<>();
		}
		private LabirintoBuilder() {
			this.labirinto = getLabirinto();
			this.listaStanze = new HashMap<>();
		}

		public LabirintoBuilder addStanzaIniziale(String stanza) {

			this.ultimaStanza=new Stanza(stanza);
			this.listaStanze.put(stanza,this.ultimaStanza);
			this.labirinto.setStanzaCorrente(this.ultimaStanza);

			return this;	
		}

		public LabirintoBuilder addStanza(String stanza) {

			this.ultimaStanza=new Stanza(stanza);
			this.listaStanze.put(stanza,this.ultimaStanza);

			return this;
		}


		public LabirintoBuilder addStanzaVincente(String stanza) {
			this.ultimaStanza=new Stanza(stanza);
			this.listaStanze.put(stanza,this.ultimaStanza);
			this.labirinto.setStanzaVincente(this.ultimaStanza);

			return this;	
		}


		public Labirinto getLabirinto() {

			return labirinto;
		}

		public LabirintoBuilder addAttrezzo(String nomeAttrezzo,int peso) {


			this.ultimaStanza.addAttrezzo(new Attrezzo(nomeAttrezzo, peso));
			return this;
		}

		public LabirintoBuilder addAdiacenza(String corrente, String adiacente, String direzione) {

			this.listaStanze.get(corrente).impostaStanzaAdiacente(direzione,listaStanze.get(adiacente));

			return this;
		}

		public HashMap<String,Stanza> getListaStanze() {

			return  this.listaStanze;
		}


	}

}
