package it.uniroma3.diadia;

import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.FabbricaDiComandi;
import it.uniroma3.diadia.comandi.FabbricaDiComandiRiflessiva;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO 
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
 * @version base
 */

public class DiaDia {


	static final private String MESSAGGIO_BENVENUTO = ""+
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"+
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n"+
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n"+
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n"+
			"Per conoscere le istruzioni usa il comando 'aiuto'.";

	private Partita partita;
	private IO io;



	public DiaDia(IO io) {

		this.io=io;
		this.partita = new Partita(io);
	}
	
	public DiaDia(Labirinto labirinto, IO io) {

		this.io=io;
		this.partita = new Partita(labirinto, io);
	}
	private boolean processaIstruzione(String istruzione) throws Exception {
		AbstractComando comandoDaEseguire;
		FabbricaDiComandi factory = new FabbricaDiComandiRiflessiva();
		comandoDaEseguire = factory.costruisciComando(istruzione);
		comandoDaEseguire.esegui(this.partita);

		if (this.partita.vinta())
			io.mostraMessaggio("Hai vinto!");

		if (!this.partita.giocatoreIsVivo())
			io.mostraMessaggio("Hai esaurito i CFU...");

		return this.partita.isFinita();
	}

	public void gioca() throws Exception {

		String istruzione; 

		io.mostraMessaggio(MESSAGGIO_BENVENUTO);

		

		do {		

			istruzione = io.leggiRiga();

			if(istruzione.equals("")) {

				io.mostraMessaggio("Comando non inserito \n");
			}
		}
		while (istruzione.equals("")||(!processaIstruzione(istruzione)));
	}  

	public static void main(String[] argc) throws Exception {
		Scanner scannerDiLinee = new Scanner(System.in);
		IO io=new IOConsole(scannerDiLinee);	
		DiaDia gioco = new DiaDia(io);
		gioco.gioca();
		scannerDiLinee.close();
	}
}