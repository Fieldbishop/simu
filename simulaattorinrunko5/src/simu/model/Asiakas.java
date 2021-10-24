package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;

/**
 * Asiakasluokka luo ja tallennetaan asiakasobjektien kulun simulaatiosysteemin
 * läpi
 * 
 * @author Sampo Bredenberg, Ville Haapamäki, Lassi Piispanen
 * @version 1.0
 */
public class Asiakas {
	/**
	 * Asiakkaan saapuminen systeemiin
	 */
	private double saapuminen0;
	/**
	 * Asiakkaan saapuminen turvatarkastuspisteelle
	 */
	private double saapuminen1;
	/**
	 * Asiakkaan saapuminen asiakaspalveluun tai istuntosaliin, asiakkaan tyypistä
	 * riippuen
	 */
	private double saapuminen2;
	/**
	 * Asiakkaan poistuminen turvatarkastuksesta
	 */
	private double poistuminen1;
	/**
	 * Asiakkaan poistuminen systeemistä
	 */
	private double poistuminen2;
	/**
	 * Asiakkaan tunnistenumero
	 */
	private int id;
	/**
	 * Asiakkaiden id-numerointia ylläpitävä apumuuttuja
	 */
	private static int i = 1;
	/**
	 * Asiakkaiden summaa ylläpitävä apumuuttuja
	 */
	private static long sum = 0;
	/**
	 * Asiakkaan kohdetapahtuma, käytännössä asiakkaan tyyppi
	 */
	private TapahtumanTyyppi kohdeTapahtuma;
	/**
	 * Asiakkasta palvelleen turvatarkastuspisteen numero
	 */
	private TurvatarkastusPiste turvaTarkastusNro;

	/**
	 * @param kohde Asiakas saa tapahtumantyypin parametrina, jotta asiakas voidaan
	 *              lähettää omaan kohdetapahtumaansa
	 */
	public Asiakas(TapahtumanTyyppi kohde) {
		id = i++;
		kohdeTapahtuma = kohde;

		saapuminen0 = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapuminen0);
	}

	/**
	 * @param tt Asetetaan asiakkaan instanssimuuttujan turvaTarkastusNro arvoksi
	 */
	public void setTurvaTarkastus(TurvatarkastusPiste tt) {
		turvaTarkastusNro = tt;
	}

	/**
	 * @return palauttaa asiakkaan instanssimuuttujan turvaTarkastusNro
	 */
	public TurvatarkastusPiste getTurvaTarkastusNro() {
		return turvaTarkastusNro;
	}

	/**
	 * @return palauttaa asiakkaan instanssimuuttujan kohdeTapahtuma
	 *         Tapahtumantyypin
	 */
	public TapahtumanTyyppi getKohdeTapahtuma() {
		return kohdeTapahtuma;
	}

	/**
	 * Palauttaa syötetyn Tapahtumantyypin mukaisen poistumisajan
	 * 
	 * @param tt syötettävä Tapahtumantyyppi
	 * @return Tapahtumantyyppi mukainen poistumisaika
	 */
	public double getPoistuminen(TapahtumanTyyppi tt) {
		if (tt == TapahtumanTyyppi.TURVADEP0 || tt == TapahtumanTyyppi.TURVADEP1) {
			return poistuminen1;
		}
		return poistuminen2;

	}

	/**
	 * Asettaa poistumisajan syötettyä Tapahtumantyyppiä vastaavalle
	 * instanssimuuttujalle
	 * 
	 * @param tt            käsiteltävä tapahtuma
	 * @param poistumisaika kellonaika tapahtumalle
	 */
	public void setPoistumisaika(TapahtumanTyyppi tt, double poistumisaika) {
		if (tt == TapahtumanTyyppi.TURVADEP0 || tt == TapahtumanTyyppi.TURVADEP1) {
			poistuminen1 = poistumisaika;
		} else {
			poistuminen2 = poistumisaika;
		}
	}

	/**
	 * Systeemistä poistumisen ajankohdan asettaminen
	 * 
	 * @param poistumisaika kellonaika
	 */
	public void setPoistumisaika2(double poistumisaika) {
		poistuminen2 = poistumisaika;
	}

	/**
	 * @return Systeemiin saapumisen ajankohta
	 */
	public double getSysteemiinSaapumisaika() {
		return saapuminen0;
	}

	/**
	 * Palauttaa tapahtumantyyppiä vastaavan ajankohdan
	 * 
	 * @param tt halutun tapahtuman tapahtumantyyppi
	 * @return tapahtumaa vastaava instanssimuuttuja
	 */
	public double getSaapumisaika(TapahtumanTyyppi tt) {

		if (tt == TapahtumanTyyppi.TURVADEP0 || tt == TapahtumanTyyppi.TURVADEP1) {
			return saapuminen1;
		}
		return saapuminen2;
	}

	/**
	 * Asettaa saapumisajan syötettyä tapahtumantyyppiä vastaavalle
	 * instanssimuuttujalle
	 * 
	 * @param tt           Haluttu tapahtumantyyppi
	 * @param saapumisaika Kellonaika
	 */
	public void setSaapumisaika(TapahtumanTyyppi tt, double saapumisaika) {
		if (tt == TapahtumanTyyppi.TURVADEP0 || tt == TapahtumanTyyppi.TURVADEP1) {
			saapuminen1 = saapumisaika;
		}
		saapuminen2 = saapumisaika;
	}

	/**
	 * @return Palauttaa asiakkaan id tunnisteen
	 */
	public int getId() {
		return id;
	}

	/**
	 * Tulostaa asiakkaan tietoja consoleen
	 */
	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapuminen0);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistuminen2);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistuminen2 - saapuminen0));
		sum += (poistuminen2 - saapuminen0);
		double keskiarvo = sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}

}
