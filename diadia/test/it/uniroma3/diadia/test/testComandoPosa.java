package it.uniroma3.diadia.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

import it.uniroma3.diadia.comandi.ComandoPosa;


class testComandoPosa {
	
	private IO io;
	private Partita partita;
	private Attrezzo attrezzo;
	private ComandoPosa posa;

	
	@BeforeEach
	public void setUp() {
		
		io= new IOConsole(null);
		partita= new Partita(io);
		attrezzo= new Attrezzo("Lanterna", 3);
		posa= new ComandoPosa();
	}
	
	@Test
	void testEseguiBorsaUnElemento() {
		
		
		partita.getGiocatore().getBorsa().addAttrezzo(attrezzo);
		posa.setParametro(attrezzo.getNome());
		posa.esegui(this.partita);
		
		assertTrue(partita.getStanzaCorrente().hasAttrezzo(attrezzo.getNome()));
	}
	
	@Test
	void testEseguiBorsaVuota() {
		
		posa.setParametro(attrezzo.getNome());
		posa.esegui(this.partita);
		
		assertFalse(partita.getStanzaCorrente().hasAttrezzo(attrezzo.getNome()));
	}
	
	@Test
	void testEseguiBorsaVouta() {
		
		posa.setParametro(attrezzo.getNome());
		posa.esegui(this.partita);
		
		assertFalse(partita.getStanzaCorrente().hasAttrezzo(attrezzo.getNome()));
	}
}
