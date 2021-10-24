package simu.model;

import java.util.ArrayList;

import controller.IKontrolleri;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import simu.framework.Trace;

/**
 * Omamoottori luo simulaatiosysteemin palvelupisteet ja käsittelee kaikki
 * simulaation tapahtumat
 * 
 * @author Sampo Bredenberg, Ville Haapamäki, Lassi Piispanen
 * @version 1.0
 */
public class OmaMoottori extends Moottori {

	/**
	 * Objekti, jolla luodaan uudet asiakkaat systeemiin
	 */
	private Saapumisprosessi saapumisprosessi;

	/**
	 * Lista, johon tallennetaan kaikki asiakkaat tulostuksia varten
	 */
	private ArrayList<Asiakas> asiakasRekisteri = new ArrayList<Asiakas>();

	/**
	 * Luo kaikki palvelupisteet ja luo viitteen instanssimuuttujaan
	 * saapumisprosessi
	 * 
	 * @param kontrolleri MVC mallin mukainen controller viite
	 */

	private int sali1PoisJaaneetAsiakaat = 0;
	private int sali2PoisJaaneetAsiakaat = 0;
	
	public OmaMoottori(IKontrolleri kontrolleri) {
		super(kontrolleri);

		palvelupisteet = new Palvelupiste[4];
		salit = new Sali[2];

		palvelupisteet[0] = new Palvelupiste(tapahtumalista, TapahtumanTyyppi.JONODEP);
		palvelupisteet[1] = new Palvelupiste(new Normal(3, 5), tapahtumalista, TapahtumanTyyppi.TURVADEP0);
		palvelupisteet[2] = new Palvelupiste(new Normal(3, 5), tapahtumalista, TapahtumanTyyppi.TURVADEP1);
		palvelupisteet[3] = new Palvelupiste(new Normal(15, 5), tapahtumalista, TapahtumanTyyppi.RESPADEP);
		salit[0] = new Sali(tapahtumalista, TapahtumanTyyppi.SALIDEP1, kontrolleri.getSali1Asetukset());
		salit[1] = new Sali(tapahtumalista, TapahtumanTyyppi.SALIDEP2, kontrolleri.getSali2Asetukset());

		saapumisprosessi = new Saapumisprosessi(new Normal(15, 5), tapahtumalista, TapahtumanTyyppi.JONOARR);
	}

	/**
	 * Alustaa istuntosalien tapahtumat, tämä luo asiakkaiden tuloajat
	 */
	@Override
	protected void alustukset() {

		salit[0].teeSalitapahtuma();
		salit[1].teeSalitapahtuma();
		kontrolleri.setSaliTekstit(Paikka.SALI1, String.format("%.2f",salit[0].getAlkamisAika()),
				String.format("%.2f",salit[0].getLoppuAika()));
		kontrolleri.setSaliTekstit(Paikka.SALI2, String.format("%.2f",salit[1].getAlkamisAika()),
				String.format("%.2f",salit[1].getLoppuAika()));
		kontrolleri.setSaliYleisöTeksti(Paikka.SALI1, 0 + "/" + salit[0].getYleisöMaksimi());
		kontrolleri.setSaliYleisöTeksti(Paikka.SALI2, 0 + "/" + salit[1].getYleisöMaksimi());
		saapumisprosessi.generoiSeuraava();
	}

	/**
	 * Suorittaa systeemin B-vaiheen tapahtuman
	 * 
	 * @param t Suoritettava Tapahtuma
	 */
	@Override
	protected void suoritaTapahtuma(Tapahtuma t) { // B-vaiheen tapahtumat
		Asiakas a;
		kontrolleri.laitaAikaTeksti(Kello.getInstance().getAika());
		switch (t.getTyyppi()) {

		// Tässä määritellään mihin kohdetapahtumaan asiakkaat ovat menossa.
		case JONOARR:
			palvelupisteet[0].lisaaJonoon(new Asiakas(t.getPäämäärä()));
			kontrolleri.siirräTurvaJonoon(TurvatarkastusPiste.TURVA1, t.getPäämäärä());
			break;

		// Salin alku tapahtuma, että saadaan UI teksti muuttumaan
		case SALIALKU:
			switch (t.getPäämäärä()) {
			case SALIDEP1:
				kontrolleri.asetaSalinTila(Paikka.SALI1, "ALKANUT");
				break;
			case SALIDEP2:
				kontrolleri.asetaSalinTila(Paikka.SALI2, "ALKANUT");
				break;
			}

		case JONODEP:
			// erikoissääntö ekalle jonolle joka lajittelee turvatarkastukseen jos sellainen
			// vapaana
			if (palvelupisteet[1].getJono().size() <= palvelupisteet[2].getJono().size()
					&& palvelupisteet[0].getJono().size() > 0) {
				Asiakas palveltava = palvelupisteet[0].otaJonosta();

				palvelupisteet[1].lisaaJonoon(palveltava);
				kontrolleri.siirräTurvaPalveltava(TurvatarkastusPiste.TURVA1);
			} else if (palvelupisteet[1].getJono().size() > palvelupisteet[2].getJono().size()
					&& palvelupisteet[0].getJono().size() > 0) {
				Asiakas palveltava = palvelupisteet[0].otaJonosta();

				palvelupisteet[2].lisaaJonoon(palveltava);
				kontrolleri.siirräTurvaPalveltava(TurvatarkastusPiste.TURVA2);
			}
			break;

		// Turvatarkastuksista lähteminen eli sorttaus asiakasobjektin kohdetapahtuman
		// mukaiseen jonoon.
		case TURVADEP0:
			Asiakas turva1Asiakas = palvelupisteet[1].otaJonosta();
			turvaSaliTarkastus(turva1Asiakas, TurvatarkastusPiste.TURVA1);
			break;

		case TURVADEP1:
			Asiakas turva2Asiakas = palvelupisteet[2].otaJonosta();
			turvaSaliTarkastus(turva2Asiakas, TurvatarkastusPiste.TURVA2);
			break;

		// Sali 1 lähtö
		case SALIDEP1:
			a = salit[0].otaSalista();
			a.setPoistumisaika(t.getTyyppi(), Kello.getInstance().getAika());
			a.raportti();
			asiakasRekisteri.add(a);
			kontrolleri.saliTyhjennys(Paikka.SALI1);
			// Jos tapahtuma on loppunu ja uutta ei ole tehty, tehdään uusi tapahtuma +
			// asiakkaat
			if (salit[0].onLoppunut()) {
				kontrolleri.asetaSalinTila(Paikka.SALI1, "AUKI");
				salit[0].teeSalitapahtuma();
				kontrolleri.setSaliTekstit(Paikka.SALI1, String.format("%.2f",salit[0].getAlkamisAika()),
						String.format("%.2f",salit[0].getLoppuAika()));
				kontrolleri.setSaliYleisöTeksti(Paikka.SALI1, 0 + "/" + salit[0].getYleisöMaksimi());
			}
			break;

		// Sali 2 lähtö
		case SALIDEP2:
			a = salit[1].otaSalista();
			a.setPoistumisaika(t.getTyyppi(), Kello.getInstance().getAika());
			a.raportti();
			asiakasRekisteri.add(a);
			kontrolleri.saliTyhjennys(Paikka.SALI2);
			// Jos tapahtuma on loppunu ja uutta ei ole tehty, tehdään uusi tapahtuma +
			// asiakkaat
			if (salit[1].onLoppunut()) {
				kontrolleri.asetaSalinTila(Paikka.SALI2, "AUKI");
				salit[1].teeSalitapahtuma();
				kontrolleri.setSaliTekstit(Paikka.SALI2, String.format("%.2f",salit[1].getAlkamisAika()),
						String.format("%.2f",salit[1].getLoppuAika()));
				kontrolleri.setSaliYleisöTeksti(Paikka.SALI2, 0 + "/" + salit[1].getYleisöMaksimi());
			}

			break;

		// Respa lähtö
		case RESPADEP:
			a = palvelupisteet[3].otaJonosta();
			a.setPoistumisaika(t.getTyyppi(), Kello.getInstance().getAika());
			a.raportti();
			kontrolleri.saliTyhjennys(Paikka.RESPA);
			break;
		}

	}

	/**
	 * simuloinnin datan tulostus
	 */
	@Override
	protected void tulokset() {
		int turvatarkastus1Total = 0;
		double turvatarkastus1TotalPalveluAika = 0;
		int turvatarkastus2Total = 0;
		double turvatarkastus2TotalPalveluAika = 0;
		int sali1Total = 0;
		double sali1TotalPalveluAika = 0;
		int sali2Total = 0;
		double sali2TotalPalveluAika = 0;
		int respaTotal = 0;
		double respaTotalPalveluAika = 0;

		for (Asiakas a : asiakasRekisteri) {
			switch (a.getTurvaTarkastusNro()) {
			case TURVA1:
				turvatarkastus1Total++;
				turvatarkastus1TotalPalveluAika += a.getPoistuminen(TapahtumanTyyppi.TURVADEP0)
						- a.getSaapumisaika(TapahtumanTyyppi.TURVADEP0);
				break;
			case TURVA2:
				turvatarkastus2Total++;
				turvatarkastus2TotalPalveluAika += a.getPoistuminen(TapahtumanTyyppi.TURVADEP1)
						- a.getSaapumisaika(TapahtumanTyyppi.TURVADEP1);
				break;
			default:
				break;
			}

			switch (a.getKohdeTapahtuma()) {
			case RESPADEP:
				respaTotal++;
				respaTotalPalveluAika += a.getPoistuminen(TapahtumanTyyppi.RESPADEP)
						- a.getSaapumisaika(TapahtumanTyyppi.RESPADEP);
				break;
			case SALIDEP1:
				sali1Total++;
				sali1TotalPalveluAika += a.getPoistuminen(TapahtumanTyyppi.SALIDEP1)
						- a.getSaapumisaika(TapahtumanTyyppi.TURVADEP0);
				break;
			case SALIDEP2:
				sali2Total++;
				sali2TotalPalveluAika += a.getPoistuminen(TapahtumanTyyppi.SALIDEP2)
						- a.getSaapumisaika(TapahtumanTyyppi.TURVADEP0);
				break;
			default:
				break;
			}
		}
		kontrolleri.simulaatiLoppu(sali1Total, sali2Total, sali1TotalPalveluAika, sali2TotalPalveluAika);
		kontrolleri.createTulos(sali1Total, sali1PoisJaaneetAsiakaat, sali2Total, sali2PoisJaaneetAsiakaat, (sali1TotalPalveluAika + sali2TotalPalveluAika) / asiakasRekisteri.size());
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset:");
		System.out.println("Total systeemissä asioineet asiakkaat: " + asiakasRekisteri.size()
				+ "\nPalvelupistekohtaiset asiakkaat:\ntt1: " + turvatarkastus1Total + "\ntt2: " + turvatarkastus2Total
				+ "\nSali1: " + sali1Total + "\nSali2: " + sali2Total + "\nRespa " + respaTotal
				+ "\n\nbusy timet:\ntt1: " + turvatarkastus1TotalPalveluAika + "\ntt2: "
				+ turvatarkastus2TotalPalveluAika + "\nrespa: " + respaTotalPalveluAika + "\nsali1: "
				+ sali1TotalPalveluAika + "\nsali2: " + sali2TotalPalveluAika);
	}

	/**
	 * Asettaa asiakkaalle turvatarkastuspisteen ja siirtää asiakkaan
	 * turvatarkastuksesta jonoon
	 * 
	 * @param asiakas Käsiteltävä asiakas
	 * @param piste   Asiakasta käsitellyt turvatarkastuspiste
	 */
	void turvaSaliTarkastus(Asiakas asiakas, TurvatarkastusPiste piste) {
		asiakas.setTurvaTarkastus(piste);
		asiakas.setPoistumisaika(TapahtumanTyyppi.TURVADEP1, Kello.getInstance().getAika());
		switch (asiakas.getKohdeTapahtuma()) {
		case SALIDEP1:
			if (!salit[0].onAlkanut() && !salit[0].onTäynnä()) {
				salit[0].lisaaJonoon(asiakas);
				kontrolleri.siirräTurvastaSaliin(piste, Paikka.SALI1);
				kontrolleri.setSaliYleisöTeksti(Paikka.SALI1,
						salit[0].getSalissaOlleetMäärä() + "/" + salit[0].getYleisöMaksimi());
			} else {
				Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " ei päässyt istuntoon");
				asiakas.setPoistumisaika2(Kello.getInstance().getAika());
				asiakas.raportti();
				kontrolleri.poistaTurvasta(piste);
				sali1PoisJaaneetAsiakaat++;
			}

			break;
		case SALIDEP2:
			if (!salit[1].onAlkanut() && !salit[1].onTäynnä()) {
				salit[1].lisaaJonoon(asiakas);
				kontrolleri.siirräTurvastaSaliin(piste, Paikka.SALI2);
				kontrolleri.setSaliYleisöTeksti(Paikka.SALI2,
						salit[1].getSalissaOlleetMäärä() + "/" + salit[1].getYleisöMaksimi());
			} else {
				Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " ei päässyt istuntoon");
				asiakas.setPoistumisaika2(Kello.getInstance().getAika());
				asiakas.raportti();
				kontrolleri.poistaTurvasta(piste);
				sali2PoisJaaneetAsiakaat++;
			}

			break;
		case RESPADEP:
			palvelupisteet[3].lisaaJonoon(asiakas);
			kontrolleri.siirräTurvastaSaliin(piste, Paikka.RESPA);
			saapumisprosessi.generoiSeuraava();
			break;
		}
		if (palvelupisteet[1].getJono().size() == 0 && palvelupisteet[0].getJono().size() > 0)
			palvelupisteet[1].lisaaJonoon(palvelupisteet[0].otaJonosta());
		else if (palvelupisteet[2].getJono().size() == 0 && palvelupisteet[0].getJono().size() > 0) {
			palvelupisteet[2].lisaaJonoon(palvelupisteet[0].otaJonosta());
		}
	}
}
