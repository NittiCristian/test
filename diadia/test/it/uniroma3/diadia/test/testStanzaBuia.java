package it.uniroma3.diadia.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBuia;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class testStanzaBuia {

	private Attrezzo attrezzo;
	private StanzaBuia stanzaBuia;
	
	@BeforeEach
	public void setUp() {
		
		attrezzo=new Attrezzo("torcia", 4);
		stanzaBuia= new StanzaBuia("stanzabuia");
	}
	
	@Test
	void testGetDescrizioneStanzaSenzaTorcia() {
		
		assertEquals("Qui c'è un buio :o",stanzaBuia.getDescrizione());
	}
	
	@Test
	void testGetDescrizioneStanzaConTorcia() {
		
		stanzaBuia.addAttrezzo(attrezzo);
		assertEquals(((Stanza)stanzaBuia).getDescrizione(),stanzaBuia.getDescrizione());
	}

}
