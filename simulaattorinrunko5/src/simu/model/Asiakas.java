package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;


// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas{
	private double saapuminen0;					//systeemiin saapuminen
	private double saapuminen1;					//ensimmäiseen palvelupisteeseen saapuminen
	private double saapuminen2;					//toiseen palvelupisteeseen saapuminen
	private double poistuminen1;				//ensimmäisestä palvelupisteestä poistuminen
	private double poistuminen2;				//toisesta palvelupisteestä poistuminen
	
	private int id;
	private static int i = 1;					//asiakkaiden id numerointia ylläpitävä suure
	private static long sum = 0;
	private TapahtumanTyyppi kohdeTapahtuma;	//Asiakkaan kohdetapahtuma, eli asiakkaan tyyppi
	private TurvatarkastusPiste turvaTarkastusNro;	//missä turtvatarkastuspisteessä asiakasta palveltiin
	
	//Asiakas saa tapahtumantyypin parametrina, jotta asiakas voidaan lähettää omaan kohdetapahtumaansa
	public Asiakas(TapahtumanTyyppi kohde){
	    id = i++;
	    kohdeTapahtuma = kohde;
	    
		saapuminen0 = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+saapuminen0);
	}

	public void setTurvaTarkastus(TurvatarkastusPiste tt) {
		turvaTarkastusNro = tt;
	}
	
	public TurvatarkastusPiste getTurvaTarkastusNro() {
		return turvaTarkastusNro;
	}
	
	//Palauttaa asiakkaan kohdetapahtuman poistumistapahtuman Tapahtumantyypin
	public TapahtumanTyyppi getKohdeTapahtuma() {
		return kohdeTapahtuma;
	}
	
	//Palauttaa poistumisenajankohdan riippuen mistä poistumisesta kyse
	//TODO: pitää jatkaa kun tapahtumien määrä kasvaa
	public double getPoistuminen(TapahtumanTyyppi tt) {
		if(tt == TapahtumanTyyppi.TURVADEP0 || tt == TapahtumanTyyppi.TURVADEP1) {
			return poistuminen1;
		}
		return poistuminen2;
		
	}

	//Asettaa poistumisenajankohdan riippuen mistä poistumisesta kyse
	//TODO: pitää jatkaa kun tapahtumien määrä kasvaa
	public void setPoistumisaika(TapahtumanTyyppi tt, double poistumisaika) {
		if(tt == TapahtumanTyyppi.TURVADEP0 || tt == TapahtumanTyyppi.TURVADEP1) {
			poistuminen1 = poistumisaika;
		}
		else {
			poistuminen2 = poistumisaika;
		}
	}
	
	public void setPoistumisaika2( double poistumisaika) {
		poistuminen2 = poistumisaika;
	}
	
	public double getSysteemiinSaapumisaika() {
		return saapuminen0;
	}
	
	public double getSaapumisaika(TapahtumanTyyppi tt) {
		
		if(tt == TapahtumanTyyppi.TURVADEP0 || tt == TapahtumanTyyppi.TURVADEP1) {
			return saapuminen1;
		}
		return saapuminen2;
	}

	public void setSaapumisaika(TapahtumanTyyppi tt, double saapumisaika) {
		if(tt == TapahtumanTyyppi.TURVADEP0 || tt == TapahtumanTyyppi.TURVADEP1) {
			saapuminen1 = saapumisaika;
		}
		saapuminen2 = saapumisaika;
	}
	
	public int getId() {
		return id;
	}
	
	public void raportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapuminen0);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistuminen2);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistuminen2-saapuminen0));
		sum += (poistuminen2-saapuminen0);
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ keskiarvo);
	}
}
