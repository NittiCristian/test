package it.uniroma3.diadia.giocatore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import it.uniroma3.diadia.Costanti;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.attrezzi.ComparatorePerAttrezzi;

public class Borsa {
	
	public final static int DEFAULT_PESO_MAX_BORSA = Costanti.getPesoMax();
	private Set<Attrezzo>attrezzi; 
	private int pesoMax;
	
	public Borsa() {
		
		this(DEFAULT_PESO_MAX_BORSA);
	}
	public Borsa(int pesoMax) {
		
		this.pesoMax = pesoMax;
		this.attrezzi = new HashSet<Attrezzo>();
	
	}
	public boolean addAttrezzo(Attrezzo attrezzo) {
	
			if((this.getPeso()+attrezzo.getPeso())<=this.pesoMax) {
				return attrezzi.add(attrezzo);
			}else
				return false;
	}
	public int getPesoMax() {
		
		return pesoMax;
	}
	
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

	public int getPeso(){
		
		int pesoTotale = 0;
		Iterator<Attrezzo> it= this.attrezzi.iterator();
		
		while (it.hasNext()) {
			
			Attrezzo a = it.next();
			pesoTotale = pesoTotale+a.getPeso();
		}
		return pesoTotale;
	}
	public boolean isEmpty() {

		return this.attrezzi.isEmpty();
	}
	public boolean hasAttrezzo(String nomeAttrezzo) {
		
		return this.getAttrezzo(nomeAttrezzo)!=null ;
	}
		
	public Attrezzo removeAttrezzo(String nomeAttrezzo) {
		Attrezzo a = null;

		if(nomeAttrezzo!=null) {
			if(this.hasAttrezzo(nomeAttrezzo)) {

				a=this.getAttrezzo(nomeAttrezzo);
				this.attrezzi.remove(a);
				return a;
			}

		}
		return a;
	}

	public String toString() {
		
		StringBuilder s = new StringBuilder();
		Iterator<Attrezzo> it= this.attrezzi.iterator();
		
		if (!this.isEmpty()) {
			
			s.append("Contenuto borsa ("+this.getPeso()+"kg/"+this.getPesoMax()+"kg): ");
				
			while (it.hasNext()) {
				
				s.append(it.next().toString()+" ");
			}
		}else
			s.append("Borsa vuota");
		
		return s.toString();
	}
	public List<Attrezzo> getContenutoOrdinatoPerPeso(){
		
		ComparatorePerAttrezzi comp= new ComparatorePerAttrezzi();
		ArrayList<Attrezzo> ordinata= new ArrayList<Attrezzo>(attrezzi);
		Collections.sort(ordinata,comp);
		return ordinata;
	}
	
	public SortedSet<Attrezzo> getContenutoOrdinatoPerNome(){
		
		SortedSet<Attrezzo> ordinata= new TreeSet<Attrezzo>(attrezzi);
		return ordinata;
	}
	
	public Map<Integer,Set<Attrezzo>> getContenutoRaggruppatoPerPeso(){
		
		Map<Integer,Set<Attrezzo>> ordinata= new HashMap<Integer,Set<Attrezzo>>();
		
		Iterator<Attrezzo> it= this.attrezzi.iterator();
		Attrezzo a = null;
		Set<Attrezzo> tmp;

		while(it.hasNext()) {

			a=it.next();
			tmp=ordinata.get(a.getPeso());
			if(tmp==null) {

				tmp=new HashSet<Attrezzo>();
				ordinata.put(a.getPeso(), tmp);
			}

			tmp.add(a);
		}
		return ordinata;
	}

	public SortedSet<Attrezzo> getSortedSetOrdinatoPerPeso(){

		ComparatorePerAttrezzi comp= new ComparatorePerAttrezzi();
		SortedSet<Attrezzo> ordinata= new TreeSet<Attrezzo>(comp);
		ordinata.addAll(this.attrezzi);
		return ordinata;
	}
}