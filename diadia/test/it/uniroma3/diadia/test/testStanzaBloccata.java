package it.uniroma3.diadia.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class testStanzaBloccata {
	private Attrezzo attrezzo;
	private StanzaBloccata stanzaBloccata;
	private Stanza stanzaAdiacente;
	
	@BeforeEach
	public void setUp() {
		
		attrezzo=new Attrezzo("chiave", 1);
		stanzaBloccata= new StanzaBloccata("stanzabloccata", "chiave", "nord");
		stanzaAdiacente= new Stanza("stanzaAdiacente");
		
		stanzaAdiacente.impostaStanzaAdiacente("sud", stanzaBloccata);
		stanzaBloccata.impostaStanzaAdiacente("nord", stanzaAdiacente);
	}
	
	@Test
	void testStanzaConChiave() {
		
		stanzaBloccata.addAttrezzo(attrezzo);
		assertEquals(stanzaAdiacente, stanzaBloccata.getStanzaAdiacente("nord"));
	}
	
	@Test
	void testStanzaSenzaChiave() {
			
		assertEquals(stanzaBloccata, stanzaBloccata.getStanzaAdiacente("nord"));
	}

}
