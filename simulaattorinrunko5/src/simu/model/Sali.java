package simu.model;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

public class Sali extends Palvelupiste{
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	private LinkedList<Asiakas> salissaOlleet = new LinkedList<Asiakas>();
	private ContinuousGenerator generator; //Genu jotta saadaan random määrä ihmisiä istuntoihin
	private double odotusAika;
	private double tapahtumanKesto;
	
	private double alkamisAika = 0;
	private double loppuAika = 0;
	private int yleisöMaksimi;
	private double etukateisArvo;
	
	public Sali(
			Tapahtumalista tapahtumalista, 
			TapahtumanTyyppi tyyppi, 
			int[] saliAsetukset) {
		
		super(tapahtumalista, tyyppi);
		this.odotusAika = saliAsetukset[0];
		this.tapahtumanKesto = saliAsetukset[1];
		this.yleisöMaksimi = saliAsetukset[2];
	}

	public void setEtukateisArvo(double a){
		etukateisArvo = a;
	}
	@Override
	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Trace.out(Trace.Level.INFO, "Lisätään asiakas: " +  jono.peekLast().getId() + " saliin");
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, loppuAika));
		salissaOlleet.add(otaJonosta());
	}
	
	public Asiakas otaSalista() {
		return salissaOlleet.poll();
	}
	
	public boolean onAlkanut() {
		return Kello.getInstance().getAika() > alkamisAika && Kello.getInstance().getAika() < loppuAika;
	}
	
	public boolean onLoppunut() {
		return Kello.getInstance().getAika() >= loppuAika;
	}
	
	public boolean onTäynnä() {
		return yleisöMaksimi <= salissaOlleet.size();
	}
	
	public int getSalissaOlleetMäärä() {
		return salissaOlleet.size();
	}
	
	public int getYleisöMaksimi() {
		return yleisöMaksimi;
	}

	//Tee salille asiakkaat 
	public void teeSalitapahtuma() {
		alkamisAika = Kello.getInstance().getAika()+odotusAika;
		loppuAika = Kello.getInstance().getAika()+tapahtumanKesto + odotusAika;
		generator = new Normal(yleisöMaksimi*0.95, 5);
		ContinuousGenerator saliAsiakasGenerator = new Negexp(7);
		
		Trace.out(Trace.Level.INFO, "Uusi istunto tehty joka loppuu ajassa: " + loppuAika + " ja alkaa ajassa " + alkamisAika);
		Tapahtuma salinAlku = new Tapahtuma(TapahtumanTyyppi.SALIALKU, alkamisAika, skeduloitavanTapahtumanTyyppi);
		tapahtumalista.lisaa(salinAlku);
		//Tehdään asiakaat ja niitten tulo ajat tapahtumalistaan
		//10 - maksimiyleisö + 25, että voitaisiin saada liika ihmisiä tulemaan
		//int asiakkaita = ThreadLocalRandom.current().nextInt(10, yleisöMaksimi + 25);
		int asiakkaita = (int)Math.floor(generator.sample());
		
		for(int i = 0; i < asiakkaita; i++) {
			//negexp avulla generoidut saapumisajat tapahtumaan saapuville asiakkaille
			double saapumisAika = saliAsiakasGenerator.sample()+etukateisArvo;
			Tapahtuma t = new Tapahtuma(TapahtumanTyyppi.JONOARR, alkamisAika-saapumisAika, skeduloitavanTapahtumanTyyppi);
			tapahtumalista.lisaa(t);
		}

	}
	
	public double getAlkamisAika() {
		return alkamisAika;
	}

	public double getLoppuAika() {
		return loppuAika;
	}
}
