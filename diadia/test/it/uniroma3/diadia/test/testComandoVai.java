package it.uniroma3.diadia.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.comandi.ComandoVai;
import it.uniroma3.diadia.ambienti.*;

class testComandoVai {

	private IO io;
	private Partita partita;
	private ComandoVai vai;
	private Stanza stanza1;
	private Stanza stanza2;
	Labirinto.LabirintoBuilder labirintoBuilder;
	
	
	@BeforeEach
	public void setUp() {
		
		io= new IOConsole(null);
		partita= new Partita(io);
		vai= new ComandoVai();
		
		stanza1= new Stanza("stanza1");
		stanza2= new Stanza("stanza2");
		stanza1.impostaStanzaAdiacente("sud", stanza2);
		stanza2.impostaStanzaAdiacente("nord", stanza1);
		partita.setStanzaCorrente(stanza1);
		Labirinto labirinto = new Labirinto();
		labirintoBuilder=labirinto.newBuilder();
	}
	
	@Test
	void testEseguiDirezioneNulla() {
		
		vai.setParametro(null);
		vai.esegui(partita);
		assertEquals(stanza1, partita.getStanzaCorrente());
	}
	
	@Test
	void testEseguiDirezioneGiusta() {
		
		vai.setParametro("sud");
		vai.esegui(partita);
		assertEquals(stanza2, partita.getStanzaCorrente());
	}
	@Test
	void testEseguiDirezioneInesistente() {
		
		vai.setParametro("est");
		vai.esegui(partita);
		assertEquals(stanza1, partita.getStanzaCorrente());
	}
	
	@Test
	public void testBilocaleLabirintoBuilder() {
	
		Labirinto bilocale = labirintoBuilder
				.addStanzaIniziale("Atrio")
				.addStanzaVincente("Uscita")
				.addAdiacenza("Atrio", "Uscita", "nord")
				.addAdiacenza("Uscita", "Atrio", "sud")
				.getLabirinto();
		
		io= new IOConsole(null);
		partita= new Partita(bilocale,io);
		
		vai.setParametro("nord");
		vai.esegui(partita);
	
	assertEquals("Uscita",partita.getStanzaCorrente().getNome());
	}
	
	@Test
	public void testTrilocaleLabirintoBuilder(){
		
		Labirinto trilocale = labirintoBuilder
				.addStanzaIniziale("ingresso")
				.addStanza("biblioteca")
				.addAdiacenza("ingresso", "biblioteca", "sud")
				.addAdiacenza("biblioteca", "ingresso", "nord")
				.addStanzaVincente("cucina")
				.addAdiacenza("biblioteca", "cucina", "est")
				.addAdiacenza("cucina","biblioteca" , "ovest")
				.getLabirinto();	
		io= new IOConsole(null);
		partita= new Partita(trilocale,io);
		
		vai.setParametro("sud");
		vai.esegui(partita);
		vai.setParametro("est");
		vai.esegui(partita);
	
	assertEquals("cucina",partita.getStanzaCorrente().getNome());
	}
	

}
