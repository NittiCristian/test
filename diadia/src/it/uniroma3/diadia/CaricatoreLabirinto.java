package it.uniroma3.diadia;

import java.io.*;
import java.util.*;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.ambienti.StanzaBuia;
import it.uniroma3.diadia.ambienti.StanzaMagica;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;


public class CaricatoreLabirinto {

	/* prefisso di una singola riga di testo contenente tutti i nomi delle stanze */
	private static final String STANZE_MARKER = "Stanze:";             

	/* prefisso di una singola riga contenente il nome della stanza iniziale */
	private static final String STANZA_INIZIALE_MARKER = "Inizio:";    

	/* prefisso della riga contenente il nome stanza vincente */
	private static final String STANZA_VINCENTE_MARKER = "Vincente:";  

	/* prefisso della riga contenente le specifiche degli attrezzi da collocare nel formato <nomeAttrezzo> <peso> <nomeStanza> */
	private static final String ATTREZZI_MARKER = "Attrezzi:";

	/* prefisso della riga contenente le specifiche dei collegamenti tra stanza nel formato <nomeStanzaDa> <direzione> <nomeStanzaA> */
	private static final String USCITE_MARKER = "Uscite:";
	/* prefisso della riga contenente le specifiche dei personaggi tra stanza nel formato <nomePersonaggio> <StanzaPersonaggio> <Presentazione> <TipoPersonaggio> se mago <NomeAttrezzo> <PesoAttrezzo> */
	private static final String PERSONAGGI_MARKER = "Personaggi:";

	/*
	 *  Esempio di un possibile file di specifica di un labirinto

		Stanze: biblioteca, N10, N11, N12 bloccata pinza ovest, N13 buia bacchetta, N14 magica 3
		Inizio: biblioteca
		Vincente: N11
		Attrezzi: martello 10 biblioteca, pinza 2 N10
		Uscite: biblioteca nord N10, biblioteca sud N11, N10 sud biblioteca, biblioteca est N12, N12 ovest biblioteca, N11 est N13, N13 ovest N11, N11 ovest N14, N14 est N11
		Personaggi: Merlino N10 Mago bacchetta 1 ho un attrezzo per te, bau biblioteca Cane bauu

	 */
	
	private LineNumberReader reader;

	private Map<String, Stanza> nome2stanza;

	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;


	public CaricatoreLabirinto(String nomeFile) throws FileNotFoundException {
		this.nome2stanza = new HashMap<String,Stanza>();
		this.reader = new LineNumberReader(new FileReader(nomeFile));
	}

	public void carica() throws FormatoFileNonValidoException {
		try {
			this.leggiECreaStanze();
			this.leggiInizialeEvincente();
			this.leggiECollocaAttrezzi();
			this.leggiEImpostaUscite();
			this.leggiECreaPersonaggi();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

	private void leggiECreaPersonaggi() throws FormatoFileNonValidoException {
		
		String specifichePersonaggio= this.leggiRigaCheCominciaPer(PERSONAGGI_MARKER);
		
		for(String specificaPersonaggio : separaStringheAlleVirgole(specifichePersonaggio)) {
			
			String StanzaPersonaggio = null;
			String nomePersonaggio = null;
			String presentazione = null;
			String tipoPersonaggio = null; 
			Attrezzo attrezzo = null;
			
			try (Scanner scannerLinea = new Scanner(specificaPersonaggio)) {
				
				
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome di un Personaggio."));
				nomePersonaggio = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("la stanza del personaggio "+nomePersonaggio+"."));
				StanzaPersonaggio = scannerLinea.next();
				
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il tipo del personaggio "+nomePersonaggio+"."));
				tipoPersonaggio = scannerLinea.next();
				
				if (tipoPersonaggio.equals("Mago")) {
					
					String nomeAttrezzo = null;
					String pesoAttrezzo = null;
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("il none del attrezzo del personaggio "+nomePersonaggio+"."));
					nomeAttrezzo = scannerLinea.next();
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("il peso dell'attrezzo "+nomeAttrezzo+"del personaggio "+nomePersonaggio+"."));
					pesoAttrezzo = scannerLinea.next();
					attrezzo=new Attrezzo(nomeAttrezzo, Integer.parseInt(pesoAttrezzo));
					
				}
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("la presentazione di un personaggio "+nomePersonaggio+"."));
				presentazione = scannerLinea.nextLine();
						
			}				
			creaPersonaggio(nomePersonaggio,StanzaPersonaggio , presentazione, tipoPersonaggio, attrezzo);
		}
	}

	private void creaPersonaggio(String nomePersonaggio,String stanzaPersonaggio , String presentazione, String tipoPersonaggio,Attrezzo attrezzo) throws FormatoFileNonValidoException {
		
		AbstractPersonaggio personaggio = null;
		StringBuilder nomeClasse = new StringBuilder("it.uniroma3.diadia.personaggi.");
		nomeClasse.append(tipoPersonaggio);
		try {
			personaggio = (AbstractPersonaggio) Class.forName(nomeClasse.toString()).newInstance();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		personaggio.setNome(nomePersonaggio);
		personaggio.setPresentazione(presentazione);
		
		if (tipoPersonaggio.equals("Mago")) {
			
			try {
				check(attrezzo!=null, "attrezzo non inserito");
			} catch (Exception e) {
				
				throw new FormatoFileNonValidoException(e.getMessage());
			}
			personaggio.setAttrezzo(attrezzo);
		}
		
		posaPersonaggio(personaggio, stanzaPersonaggio);
		 
	}

	private void posaPersonaggio(AbstractPersonaggio personaggio, String stanzaPersonaggio) throws FormatoFileNonValidoException {
		
		check(isStanzaValida(stanzaPersonaggio),"personaggio "+ personaggio.getNome()+" non collocabile: stanza " +stanzaPersonaggio+" inesistente");
		this.nome2stanza.get(stanzaPersonaggio).setPersonaggio(personaggio);
		
	}

	private String leggiRigaCheCominciaPer(String marker) throws FormatoFileNonValidoException {
		try {
			String riga = this.reader.readLine();
			check(riga.startsWith(marker),"era attesa una riga che cominciasse per "+marker);
			return riga.substring(marker.length()+1);
		} catch (IOException e) {
			throw new FormatoFileNonValidoException(e.getMessage());
		}
	}

	private void leggiECreaStanze() throws FormatoFileNonValidoException  {

		String specificheStanze = this.leggiRigaCheCominciaPer(STANZE_MARKER);
		String tipoStanza = null; 
		String nomeStanza = null;
		String attrezzoSblocco = null;
		String direzioneBloccata = null;
		String sogliaMagica = null;
		String nomeAttrezzoBuio = null;
		Stanza stanza=null;
		Stanza stanzaApp=null;
		for(String specificaStanza : separaStringheAlleVirgole(specificheStanze)) {

			try (Scanner scannerLinea = new Scanner(specificaStanza)) {
				
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome di una stanza."));
				nomeStanza= scannerLinea.next();
				
				if(scannerLinea.hasNext()) {
					
					tipoStanza=scannerLinea.next();
					
					if(tipoStanza.equals("buia")) {
						check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nomeAttrezzoBuio della stanza "+nomeStanza));
						nomeAttrezzoBuio = scannerLinea.next();
					}
					
					if(tipoStanza.equals("magica")) {
						
						check(scannerLinea.hasNext(),msgTerminazionePrecoce("la soglia magica della stanza "+nomeStanza));
						sogliaMagica = scannerLinea.next();
					}
					
					if(tipoStanza.equals("bloccata")) {
						
						check(scannerLinea.hasNext(),msgTerminazionePrecoce("l'attrezzo sblocco della stanza "+nomeStanza));
						attrezzoSblocco = scannerLinea.next();
						check(scannerLinea.hasNext(),msgTerminazionePrecoce("la direzione bloccata della stanza "+nomeStanza));
						direzioneBloccata = scannerLinea.next();
					}
					
					stanzaApp = creaStanzaSpeciale(nomeStanza, tipoStanza, attrezzoSblocco, direzioneBloccata, sogliaMagica, nomeAttrezzoBuio);
					
					this.nome2stanza.put(nomeStanza, stanzaApp);
					
				}else{
					 
					stanza = new Stanza(nomeStanza);
					
					this.nome2stanza.put(nomeStanza, stanza);
				}	

			}
		}
	}

	private Stanza creaStanzaSpeciale(String nomeStanza, String tipoStanza, String attrezzoSblocco, String direzioneBloccata, String sogliaMagica, String nomeAttrezzoBuio) throws FormatoFileNonValidoException {
		
		Stanza stanzaSpeciale = null;
		
		if(tipoStanza.equals("buia")) {
			
			return new StanzaBuia(nomeStanza, nomeAttrezzoBuio);
			
		}
		if(tipoStanza.equals("magica")) {
			
			return new StanzaMagica(nomeStanza, Integer.parseInt(sogliaMagica));
			
		}
		if(tipoStanza.equals("bloccata")) {
		
			return new StanzaBloccata(nomeStanza, attrezzoSblocco, direzioneBloccata);
			
		}
		
		
		check(stanzaSpeciale==null,"Stanza speciale non creata "+tipoStanza+" "+nomeStanza) ;	
	
		return stanzaSpeciale;
	}

	private List<String> separaStringheAlleVirgole(String string) {

		List<String> result = new LinkedList<>();
		Scanner scanner = new Scanner(string);
		scanner.useDelimiter(", ");

		try(Scanner scannerDiParole = scanner;){
			while(scannerDiParole.hasNext()) {
				result.add(scannerDiParole.next());
			}
		}
		
		return result;
	}


	private void leggiInizialeEvincente() throws FormatoFileNonValidoException {
		String nomeStanzaIniziale = null;
		nomeStanzaIniziale = this.leggiRigaCheCominciaPer(STANZA_INIZIALE_MARKER);
		check(this.isStanzaValida(nomeStanzaIniziale), nomeStanzaIniziale +" non definita");
		String nomeStanzaVincente = this.leggiRigaCheCominciaPer(STANZA_VINCENTE_MARKER);
		check(this.isStanzaValida(nomeStanzaVincente), nomeStanzaVincente + " non definita");
		this.stanzaIniziale = this.nome2stanza.get(nomeStanzaIniziale);
		this.stanzaVincente = this.nome2stanza.get(nomeStanzaVincente);
	}

	private void leggiECollocaAttrezzi() throws FormatoFileNonValidoException {

		String specificheAttrezzi = this.leggiRigaCheCominciaPer(ATTREZZI_MARKER);

		for(String specificaAttrezzo : separaStringheAlleVirgole(specificheAttrezzi)) {
			String nomeAttrezzo = null;
			String pesoAttrezzo = null;
			String nomeStanza = null; 
			try (Scanner scannerLinea = new Scanner(specificaAttrezzo)) {
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome di un attrezzo."));
				nomeAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il peso dell'attrezzo "+nomeAttrezzo+"."));
				pesoAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome della stanza in cui collocare l'attrezzo "+nomeAttrezzo+"."));
				nomeStanza = scannerLinea.next();
			}				
			posaAttrezzo(nomeAttrezzo, pesoAttrezzo, nomeStanza);
		}
	}

	private void posaAttrezzo(String nomeAttrezzo, String pesoAttrezzo, String nomeStanza) throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoAttrezzo);
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			check(isStanzaValida(nomeStanza),"Attrezzo "+ nomeAttrezzo+" non collocabile: stanza " +nomeStanza+" inesistente");
			this.nome2stanza.get(nomeStanza).addAttrezzo(attrezzo);
		}
		catch (NumberFormatException e) {
			check(false, "Peso attrezzo "+nomeAttrezzo+" non valido");
		}
	}


	private boolean isStanzaValida(String nomeStanza) {
		return this.nome2stanza.containsKey(nomeStanza);
	}

	private void leggiEImpostaUscite() throws FormatoFileNonValidoException {
		String specificheUscite = this.leggiRigaCheCominciaPer(USCITE_MARKER);
		
		for(String uscite : separaStringheAlleVirgole(specificheUscite)) {
			
			try (Scanner scannerDiLinea = new Scanner(uscite)) {			

			while (scannerDiLinea.hasNext()) {
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("le uscite di una stanza."));
				String stanzaPartenza = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la direzione di una uscita della stanza "+stanzaPartenza));
				String dir = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la destinazione di una uscita della stanza "+stanzaPartenza+" nella direzione "+dir));
				String stanzaDestinazione = scannerDiLinea.next();
				
				impostaUscita(stanzaPartenza, dir, stanzaDestinazione);
				}
			} 
		}
	}

	private String msgTerminazionePrecoce(String msg) {
		return "Terminazione precoce del file prima di leggere "+msg;
	}

	private void impostaUscita(String stanzaDa, String dir, String nomeA) throws FormatoFileNonValidoException {
		
		check(isStanzaValida(stanzaDa),"Stanza di partenza sconosciuta "+dir);
		check(isStanzaValida(nomeA),"Stanza di destinazione sconosciuta "+ dir);
		Stanza partenzaDa = this.nome2stanza.get(stanzaDa);
		Stanza arrivoA = this.nome2stanza.get(nomeA);
		partenzaDa.impostaStanzaAdiacente(dir, arrivoA);
	}


	final private void check(boolean condizioneCheDeveEsseraVera, String messaggioErrore) throws FormatoFileNonValidoException {
		if (!condizioneCheDeveEsseraVera)
			throw new FormatoFileNonValidoException("Formato file non valido [" + this.reader.getLineNumber() + "] "+messaggioErrore);		
	}

	public Stanza getStanzaIniziale() {
		return this.stanzaIniziale;
	}

	public Stanza getStanzaVincente() {
		return this.stanzaVincente;
	}
}