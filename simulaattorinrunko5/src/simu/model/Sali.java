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

/**
 * Sali on Palvelupisteen alaluokka, joka täyttyy salissa järjestettävään
 * istuntoon pyrkivistä asiakkaista
 * 
 * @author Sampo Bredenberg, Ville Haapamäki, Lassi Piispanen
 * @version 1.0
 */
public class Sali extends Palvelupiste {
	/**
	 * Lista kaikista salissa olleista asiakkaista
	 */
	private LinkedList<Asiakas> salissaOlleet = new LinkedList<Asiakas>();
	/**
	 * Satunnaisgeneraattori saliin saapuvien ihmisten määrälle
	 */
	private ContinuousGenerator generator;
	/**
	 * Aika salin istunnon päättymisen ja seuraavan alkamisen välillä
	 */
	private double odotusAika;

	/**
	 * Salin istunnon kesto
	 */
	private double tapahtumanKesto;

	/**
	 * Istunnon alkamisen ajankohta
	 */
	private double alkamisAika = 0;
	/**
	 * Istunnon loppumisen ajankohta
	 */
	private double loppuAika = 0;
	/**
	 * Salin tilavuus
	 */
	private int yleisöMaksimi;
	/**
	 * Muuttuja sille, kuinka ajoissa asiakkaat saapuvat paikalle ehtiäkseen
	 * istuntoon
	 */
	private double etukateisArvo;

	/**
	 * Konstruktori salitapahtumalle
	 * 
	 * @param tapahtumalista Systeemin tapahtumajärjestystä ylläpitävän listan
	 *                       instanssi
	 * @param tyyppi         skeduloitavan tapahtuman tyyppi
	 * @param saliAsetukset  GUI:sta säädettäviä salitapahtumien asetuksia
	 */
	public Sali(Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, int[] saliAsetukset) {

		super(tapahtumalista, tyyppi);
		generator = new Normal(saliAsetukset[0], saliAsetukset[0] * 0.1);
		this.odotusAika = generator.sample();
		generator = new Normal(saliAsetukset[1], saliAsetukset[1] * 0.1);
		this.tapahtumanKesto = generator.sample();
		generator = new Normal(saliAsetukset[2], saliAsetukset[2] * 0.1);
		this.yleisöMaksimi = (int) generator.sample();

	}

	/**
	 * Asettaa instanssimuuttujan etukateisArvo
	 * 
	 * @param a aikaarvo sille, kuinka ajoissa ihmiset saapuvat paikalle, tämän ajan
	 *          lisäksi käytössä on myös negexp käyrä, jolla luodaan hajontaa
	 *          asiakkaiden saapumiselle
	 */
	public void setEtukateisArvo(double a) {
		etukateisArvo = a;
	}

	/**
	 * Aloittaa palvelun saliin ehtineille, kun istunto alkaa
	 */
	@Override
	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Trace.out(Trace.Level.INFO, "Lisätään asiakas: " + jono.peekLast().getId() + " saliin");
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, loppuAika));
		salissaOlleet.add(otaJonosta());
	}

	/**
	 * Poistaa salissa olleita
	 * 
	 * @return
	 */
	public Asiakas otaSalista() {
		return salissaOlleet.poll();
	}

	/**
	 * Palauttaa boolean riippuen siitä, onko istunto käynnissä
	 * 
	 * @return false, jos sali on vappaa
	 */
	public boolean onAlkanut() {
		return Kello.getInstance().getAika() > alkamisAika && Kello.getInstance().getAika() < loppuAika;
	}

	/**
	 * Palauttaa boolean riippuen siitä, onko istunto loppunut
	 * 
	 * @return false, jos istunto on käynnissä vielä
	 */
	public boolean onLoppunut() {
		return Kello.getInstance().getAika() >= loppuAika;
	}

	/**
	 * Palauttaa boolean riippuen siitä, onko salin kapasiteetti saavutettu
	 * 
	 * @return true, jos sali täynnä
	 */
	public boolean onTäynnä() {
		return yleisöMaksimi <= salissaOlleet.size();
	}

	/**
	 * Palauttaa salissa palveltujen määrän
	 * 
	 * @return Salissa olleiden asiakkaiden määrä
	 */
	public int getSalissaOlleetMäärä() {
		return salissaOlleet.size();
	}

	/**
	 * Palauttaa salin kapasiteetin
	 * 
	 * @return Palauttaa saliin mahtuvien ihmisten määrän
	 */
	public int getYleisöMaksimi() {
		return yleisöMaksimi;
	}

	/**
	 * Tekee salin asiakkaat ja lisää ne tapahtumalistaan
	 */
	public void teeSalitapahtuma() {
		alkamisAika = Kello.getInstance().getAika() + odotusAika;
		loppuAika = Kello.getInstance().getAika() + tapahtumanKesto + odotusAika;
		generator = new Normal(yleisöMaksimi * 0.95, 5);
		ContinuousGenerator saliAsiakasGenerator = new Negexp(7);

		Trace.out(Trace.Level.INFO,
				"Uusi istunto tehty joka loppuu ajassa: " + loppuAika + " ja alkaa ajassa " + alkamisAika);
		Tapahtuma salinAlku = new Tapahtuma(TapahtumanTyyppi.SALIALKU, alkamisAika, skeduloitavanTapahtumanTyyppi);
		tapahtumalista.lisaa(salinAlku);
		int asiakkaita = (int) Math.floor(generator.sample());

		for (int i = 0; i < asiakkaita; i++) {
			// negexp avulla generoidut saapumisajat istuntoon saapuville asiakkaille
			double saapumisAika = saliAsiakasGenerator.sample() + etukateisArvo;
			Tapahtuma t = new Tapahtuma(TapahtumanTyyppi.JONOARR, alkamisAika - saapumisAika,
					skeduloitavanTapahtumanTyyppi);
			tapahtumalista.lisaa(t);
		}

	}

	/**
	 * palauttaa istunnon alkamisen ajankohdan
	 * 
	 * @return kellonaika
	 */
	public double getAlkamisAika() {
		return alkamisAika;
	}

	/**
	 * Palauttaa istunnon loppumisen ajankohdan
	 * 
	 * @return kellonaika
	 */
	public double getLoppuAika() {
		return loppuAika;
	}
}
