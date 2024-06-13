package it.uniroma3.diadia.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.ComandoPrendi;

public class testComandoPrendi {
	
	private IO io;
	private Partita partita;
	private Attrezzo attrezzo;
	private ComandoPrendi prendi;

	@BeforeEach
	public void setUp() {
		
		io= new IOConsole(null);
		partita= new Partita(io);
		attrezzo= new Attrezzo("sasso", 4);
		prendi= new ComandoPrendi();
	}
	
	@Test
	void testEseguiStanzaUnElementoBorsaVuota() {
		
		partita.getStanzaCorrente().addAttrezzo(attrezzo);
		prendi.setParametro(attrezzo.getNome());
		prendi.esegui(this.partita);
		
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo(attrezzo.getNome()));
	}
	
	@Test
	void testEseguiStanzaVuotaBorsaVuota() {
		
		prendi.setParametro(attrezzo.getNome());
		prendi.esegui(this.partita);
		
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo(attrezzo.getNome()));
	}
	
	@Test
	void testEseguiStanzaUnElementoBorsaPiena() {
		
		partita.getStanzaCorrente().addAttrezzo(attrezzo);
		
		attrezzo= new Attrezzo("martello", 10);
		partita.getGiocatore().getBorsa().addAttrezzo(attrezzo);
		
		prendi.setParametro(attrezzo.getNome());
		prendi.esegui(this.partita);
		
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("sasso"));
	}
	
}
