package it.uniroma3.diadia.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class testStanza {

	private Attrezzo attrezzo1;     
	private Attrezzo attrezzo2;     
	private Stanza stanza;      
	
	@BeforeEach     
	public void setUp() {     
		
		attrezzo1 = new Attrezzo("Osso",4);     
		attrezzo2 = new Attrezzo("Pistola",5);     
		stanza = new Stanza("n11");     
	}
	
	
	
	@Test
	public void testAggiungiAttrezzoStanzaPiena() {

		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		
		assertFalse(stanza.addAttrezzo(attrezzo1));
	}
	
	@Test
	public void testAggiungiAttrezzoStanzaPienaAMeta() {
		

		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		stanza.addAttrezzo(attrezzo1);
		
		
		assertTrue(stanza.addAttrezzo(attrezzo1));
	}
	
	
	@Test
	public void testAggiungiAttrezzoStanzaVuota() {
		
		assertTrue(stanza.addAttrezzo(attrezzo1));
	}
	
	@Test
	public void testAttrezzoCercatoETrovato() {
		
		stanza.addAttrezzo(attrezzo1);
		
		assertTrue(stanza.hasAttrezzo("Osso"));
	}
	
	@Test
	public void testNonEsistenzaAttrezzo1() {
	
		stanza.addAttrezzo(attrezzo2);
		stanza.addAttrezzo(attrezzo2);
		stanza.addAttrezzo(attrezzo2);
		stanza.addAttrezzo(attrezzo2);
		stanza.addAttrezzo(attrezzo2);
		
		assertFalse(stanza.hasAttrezzo("0sso"));
			
	

	}
	
	
	@Test
	public void testAggiuntaStanzaAdiacente() {
		
		Stanza stanza2 = new Stanza("n12");     
		stanza2.impostaStanzaAdiacente("nord",stanza2);
		
		assertEquals(stanza2,stanza2.getStanzaAdiacente("nord"));
	}
	

}
