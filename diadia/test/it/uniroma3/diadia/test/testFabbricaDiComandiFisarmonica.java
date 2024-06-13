//package it.uniroma3.diadia.test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import it.uniroma3.diadia.comandi.*;
//
//class testFabbricaDiComandiFisarmonica {
//
//	private FabbricaDiComandiFisarmonica fabbrica;
//	private Comando comando;
//	
//	@BeforeEach
//	public void setUp(){
//	
//		fabbrica=new FabbricaDiComandiFisarmonica();
//	}
//	
//	@Test
//	void testCostruisciComandoVai() {
//		ComandoVai comandoVai= new ComandoVai();
//		assertEquals(comandoVai.getClass(), fabbrica.costruisciComando("vai").getClass());
//	}
//	
//	@Test
//	void testComandoNonValido() {
//		ComandoNonValido comandoNonValido= new ComandoNonValido();
//		assertEquals(comandoNonValido.getClass(), fabbrica.costruisciComando("").getClass());
//	}
//	
//	@Test
//	void testComandoPosaSetParametro() {
//		comando = fabbrica.costruisciComando("posa osso");
//		assertEquals("osso", comando.getParametro());
//	}
//	@Test
//	void testComandoPrendiSetNome() {
//		comando = fabbrica.costruisciComando("prendi osso");
//		assertEquals("prendi", comando.getNome());
//	}
//
//
//}