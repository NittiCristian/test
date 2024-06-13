package it.uniroma3.diadia.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;

class testBorsa {
	
	Borsa borsa;
	Attrezzo attrezzo ;
	@BeforeEach
	public void setUp() {
		
		borsa =new Borsa(10);
		attrezzo = new Attrezzo("Osso",1);
	}

	@Test
	void testAddAttrezzoBorsaVuota() {
		
		borsa.addAttrezzo(attrezzo);
		
		assertEquals(attrezzo, borsa.getAttrezzo("Osso"));
	}
	
	@Test
	void testAddAttrezzoBorsaPiena() {
		
		Attrezzo attrezzo10 = new Attrezzo("Pistola",10);
		borsa.addAttrezzo(attrezzo10);
		
		assertFalse(borsa.addAttrezzo(attrezzo));
	}
	
	@Test
	void testRemoveAttrezzo() {
		
		borsa.addAttrezzo(attrezzo);
		borsa.removeAttrezzo("Osso");
		
		assertTrue(borsa.isEmpty());
	}
	@Test
	void testRemoveAttrezzoBorsaVuota() {
		
		assertEquals(null, borsa.removeAttrezzo("Osso"));
	}
	
	@Test
	void testRemoveAttrezzoBorsaPienaAttrezzoInesistente() {
		
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo);
		
		assertEquals(null, borsa.removeAttrezzo("Pistola"));
	}
	@Test
	void testInserimentoAttrezzoDuplicato() {
		
		borsa.addAttrezzo(attrezzo);
		
		assertFalse(borsa.addAttrezzo(attrezzo));
	}

	@Test
	void testOrdinamentoPerPeso() {
		
		Attrezzo attrezzo10 = new Attrezzo("Pistola",4);
		Attrezzo attrezzo2 = new Attrezzo("Libro",2);
		Attrezzo attrezzo5 = new Attrezzo("Sasso",2);
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo2);
		borsa.addAttrezzo(attrezzo10);
		borsa.addAttrezzo(attrezzo5);
	
		assertEquals("Osso", borsa.getContenutoOrdinatoPerPeso().get(0).getNome());         
		assertEquals("Libro", borsa.getContenutoOrdinatoPerPeso().get(1).getNome());         
		assertEquals("Sasso", borsa.getContenutoOrdinatoPerPeso().get(2).getNome());         
		assertEquals("Pistola", borsa.getContenutoOrdinatoPerPeso().get(3).getNome());
		
	}
	
	@Test
	void testOrdinamentoPerNome() {
		
		Attrezzo attrezzo10 = new Attrezzo("Pistola",4);
		Attrezzo attrezzo2 = new Attrezzo("Libro",2);
		Attrezzo attrezzo5 = new Attrezzo("Sasso",2);
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo2);
		borsa.addAttrezzo(attrezzo10);
		borsa.addAttrezzo(attrezzo5);
	
		Iterator<Attrezzo> it = borsa.getContenutoOrdinatoPerNome().iterator();
		
		assertEquals("Libro", it.next().getNome());
		assertEquals("Osso", it.next().getNome());
		assertEquals("Pistola", it.next().getNome());
		assertEquals("Sasso", it.next().getNome());
		
		
	}
	@Test
	void testOrdinamentoRaggruppatoPerPeso() {
		
		Attrezzo attrezzo10 = new Attrezzo("Pistola",4);
		Attrezzo attrezzo2 = new Attrezzo("Libro",2);
		Attrezzo attrezzo5 = new Attrezzo("Sasso",2);
		
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo2);
		borsa.addAttrezzo(attrezzo10);
		borsa.addAttrezzo(attrezzo5);
	
		Iterator<Attrezzo> it = borsa.getContenutoRaggruppatoPerPeso().get(1).iterator();
		
		assertEquals("Osso", it.next().getNome());
		it = borsa.getContenutoRaggruppatoPerPeso().get(2).iterator();
		
		assertEquals("Libro", it.next().getNome());
		assertEquals("Sasso", it.next().getNome());
		it = borsa.getContenutoRaggruppatoPerPeso().get(4).iterator();
		
		assertEquals("Pistola", it.next().getNome());
		
		
	}
	void testSortedSetOrdinamentoPerPeso() {
		
		Attrezzo attrezzo10 = new Attrezzo("Pistola",4);
		Attrezzo attrezzo2 = new Attrezzo("Libro",2);
		Attrezzo attrezzo5 = new Attrezzo("Sasso",2);
		
		borsa.addAttrezzo(attrezzo);
		borsa.addAttrezzo(attrezzo2);
		borsa.addAttrezzo(attrezzo10);
		borsa.addAttrezzo(attrezzo5);
	
		Iterator<Attrezzo> it = borsa.getSortedSetOrdinatoPerPeso().iterator();
		
		assertEquals("Osso", it.next().getNome());         
		assertEquals("Libro", it.next().getNome());         
		assertEquals("Sasso", it.next().getNome());         
		assertEquals("Pistola", it.next().getNome());
		
		
	}

}
