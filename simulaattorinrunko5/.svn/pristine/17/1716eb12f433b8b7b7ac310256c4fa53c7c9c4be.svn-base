package simu.model;

import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	protected LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	
	private ContinuousGenerator generator;
	protected Tapahtumalista tapahtumalista;
	protected TapahtumanTyyppi skeduloitavanTapahtumanTyyppi; 
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;			
	}
	
	public Palvelupiste(Tapahtumalista tl, TapahtumanTyyppi t) {
		tapahtumalista = tl;
		skeduloitavanTapahtumanTyyppi = t;
	}

	public LinkedList<Asiakas> getJono() {
		return jono;
	}

	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		
	}

	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}

	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		double palveluaika;
		varattu = true;
		if(generator == null) {
			palveluaika = 0;
		}
		else {
			palveluaika = generator.sample();
		}
		jono.peek().setSaapumisaika(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika());
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
	}


	public boolean onVarattu(){
		return varattu;
	}


	public boolean onJonossa(){
		return jono.size() != 0;
	}

}
