package it.uniroma3.diadia.ambienti;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Classe Stanza - una stanza in un gioco di ruolo.
 * Una stanza e' un luogo fisico nel gioco.
 * E' collegata ad altre stanze attraverso delle uscite.
 * Ogni uscita e' associata ad una direzione.
 * 
 * @author docente di POO 
 * @see Attrezzo
 * @version base
*/

public class StanzaProtected {
	
	static final protected int NUMERO_MASSIMO_DIREZIONI = 4;
	static final protected int NUMERO_MASSIMO_ATTREZZI = 10;
	
	protected String nome;
	protected Set<Attrezzo> attrezzi;
	protected int numeroAttrezzi;
	protected Map<String,StanzaProtected> stanzeAdiacenti;
	protected int numeroStanzeAdiacenti;
	
    
    /**
     * Crea una stanza. Non ci sono stanze adiacenti, non ci sono attrezzi.
     * @param nome il nome della stanza
     */
    public StanzaProtected(String nome) {
    	this.nome = nome;
		this.numeroStanzeAdiacenti = 0;
		this.numeroAttrezzi = 0;
		this.attrezzi=new HashSet<Attrezzo>();
		this.stanzeAdiacenti=new HashMap<String,StanzaProtected>();
    }

    /**
     * Imposta una stanza adiacente.
     *
     * @param direzione direzione in cui sara' posta la stanza adiacente.
     * @param stanza stanza adiacente nella direzione indicata dal primo parametro.
     */
    public void impostaStanzaAdiacente(String direzione, StanzaProtected stanza) {

    	this.stanzeAdiacenti.put(direzione, stanza);
    	if(this.numeroStanzeAdiacenti<4)
    		this.numeroStanzeAdiacenti++;
    }

    /**
     * Restituisce la stanza adiacente nella direzione specificata
     * @param direzione
     */
    public StanzaProtected getStanzaAdiacente(String direzione) {
    	
    	return stanzeAdiacenti.get(direzione);
	}

    /**
     * Restituisce la nome della stanza.
     * @return il nome della stanza
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Restituisce la descrizione della stanza.
     * @return la descrizione della stanza
     */
    public String getDescrizione() {
        return this.toString();
    }

    /**
     * Restituisce la collezione di attrezzi presenti nella stanza.
     * @return la collezione di attrezzi nella stanza.
     */
    public Set<Attrezzo> getAttrezzi() {
		return this.attrezzi;
	}

    /**
     * Mette un attrezzo nella stanza.
     * @param attrezzo l'attrezzo da mettere nella stanza.
     * @return true se riesce ad aggiungere l'attrezzo, false atrimenti.
     */
    public boolean addAttrezzo(Attrezzo attrezzo) {
		if (this.numeroAttrezzi < NUMERO_MASSIMO_ATTREZZI) {
			this.attrezzi.add(attrezzo);
			this.numeroAttrezzi++;
			return true;
		}
		else {
			return false;
		}
	}

   /**
	* Restituisce una rappresentazione stringa di questa stanza,
	* stampadone la descrizione, le uscite e gli eventuali attrezzi contenuti
	* @return la rappresentazione stringa
	*/
	public String toString() {

		StringBuilder risultato = new StringBuilder();
		risultato.append(this.nome);

		risultato.append("\nUscite: ");

		Set<String> direzioni= stanzeAdiacenti.keySet();
		Iterator<String>it = direzioni.iterator();

		while(it.hasNext()) {

			risultato.append(" " + it.next());
		}

		risultato.append("\nAttrezzi nella stanza: ");


		Iterator<Attrezzo>it2 = attrezzi.iterator();
		while(it2.hasNext()) {

			risultato.append(it2.next().toString()+" ");	
		}

		return risultato.toString();
	}

    /**
	* Controlla se un attrezzo esiste nella stanza (uguaglianza sul nome).
	* @return true se l'attrezzo esiste nella stanza, false altrimenti.
	*/
	public boolean hasAttrezzo(String nomeAttrezzo) {
		
		return this.attrezzi.contains(this.getAttrezzo(nomeAttrezzo));
	}

	/**
     * Restituisce l'attrezzo nomeAttrezzo se presente nella stanza.
	 * @param nomeAttrezzo
	 * @return l'attrezzo presente nella stanza.
     * 		   null se l'attrezzo non e' presente.
	 */
	public Attrezzo getAttrezzo(String nomeAttrezzo) {

		Attrezzo a = null;
		Attrezzo b = null;

		if(nomeAttrezzo!=null) {

			Iterator<Attrezzo> it=this.attrezzi.iterator();
			while(it.hasNext()) {

				b = it.next();
				if(b.getNome().equals(nomeAttrezzo))
					return b;
			}

		}
		return a;
	}

	/**
	 * Rimuove un attrezzo dalla stanza (ricerca in base al nome).
	 * @param nomeAttrezzo
	 * @return true se l'attrezzo e' stato rimosso, false altrimenti
	 */
	public boolean removeAttrezzo(String nomeAttrezzo) {

		Attrezzo a = null;

		if(nomeAttrezzo!=null) {
			if(this.hasAttrezzo(nomeAttrezzo)) {

				a=this.getAttrezzo(nomeAttrezzo);
				return this.attrezzi.remove(a);

			}

		}
		return false;
	}

	private int getNumeroAttrezzi() {

		return this.numeroAttrezzi;
	}

	private int getNumeroStanzeAdiacenti() {

		return this.numeroStanzeAdiacenti;
	}

	@Override
	public boolean equals(Object o) {

		StanzaProtected that = (StanzaProtected)o;

		return this.nome.equals(that.getNome())&&this.numeroAttrezzi==that.getNumeroAttrezzi()&&this.getNumeroStanzeAdiacenti()==that.getNumeroStanzeAdiacenti();
	}


	@Override
	public int hashCode() {

		return this.nome.hashCode()+this.numeroAttrezzi+this.numeroStanzeAdiacenti;
	}

}