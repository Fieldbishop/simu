package simu.model;

import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

/**
 * Palvelupiste-objektit hoitavat asiakkaiden käsittelyn microtasolla
 * 
 * @author Sampo Bredenberg, Ville Haapamäki, Lassi Piispanen
 * @version 1.0
 */
public class Palvelupiste {

	/**
	 * Palvelupisteen jono, sisältää asiakasobjekteja
	 */
	protected LinkedList<Asiakas> jono = new LinkedList<Asiakas>();

	/**
	 * Palvelupisteen palvelun randomointiin käytettävä generaattoriobjekti
	 */
	private ContinuousGenerator generator;

	/**
	 * Systeemin Tapahtumalista objekti ylläpitää simulaation tapahtumien
	 * aikajärjestystä
	 */
	protected Tapahtumalista tapahtumalista;

	/**
	 * Seuraavan, palvelupisteen luoman, tapahtumalistan tapahtuman tyyppi, kun
	 * asiakas on käsitelty
	 */
	protected TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	/**
	 * Boolean arvo sille, onko palvelupiste varattu
	 */
	private boolean varattu = false;

	/**
	 * Konstruktori Palvelupisteen luomista varten
	 * 
	 * @param generator      Palvelupisteen palvelunaikoja tuottava
	 *                       satunnaisgeneraattori
	 * @param tapahtumalista Systeemin tapahtumajärjestystä ylläpitävän listan
	 *                       instanssi
	 * @param tyyppi         Palvelun päättymisen tapahtumantyyppi
	 */
	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
	}

	/**
	 * Konstruktori ilman satunnaisgeneraattoria, tätä käytetään ensimmäisen
	 * palvelupisteen "jonon" luomisessa. Jono ei palvele asiakasta vaan toimii
	 * reitittimenä vapaille turvatarkastuspisteille
	 * 
	 * @param tl Systeemin tapahtumajärjestystä ylläpitävän listan instanssi
	 * @param t  Palvelun päättymisen tapahtumantyyppi
	 */
	public Palvelupiste(Tapahtumalista tl, TapahtumanTyyppi t) {
		tapahtumalista = tl;
		skeduloitavanTapahtumanTyyppi = t;
	}

	/**
	 * Palauttaa palvelupisteem jonon
	 * 
	 * @return LinkedList, jossa Asiakas-objekteja
	 */
	public LinkedList<Asiakas> getJono() {
		return jono;
	}

	/**
	 * Lisää asiakkaan palvelupisteen jonoon
	 * 
	 * @param a Lisättävä asiakas
	 */
	public void lisaaJonoon(Asiakas a) {
		jono.add(a);
	}

	/**
	 * Poistaa palvelussa olleen asiakkaan jonosta
	 * 
	 * @return Palauttaa jonon ensimmäisen asiakas-objektin
	 */
	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}

	/**
	 * Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
	 */
	public void aloitaPalvelu() {
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		double palveluaika;
		varattu = true;
		if (generator == null) {
			palveluaika = 0;
		} else {
			palveluaika = generator.sample();
		}
		jono.peek().setSaapumisaika(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika());
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika() + palveluaika));
	}

	/**
	 * Tarkistaa onko palvelupiste varattu
	 * 
	 * @return Palauttaa instanssimuuttujan varattu
	 */
	public boolean onVarattu() {
		return varattu;
	}

	/**
	 * Palauttaa palvelupisteen jonon pituuden
	 * 
	 * @return Jonon pituus
	 */
	public boolean onJonossa() {
		return jono.size() != 0;
	}

}
