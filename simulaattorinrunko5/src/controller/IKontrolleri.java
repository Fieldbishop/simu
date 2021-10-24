package controller;

import java.util.List;

import javafx.collections.ObservableList;
import simu.model.Paikka;
import simu.model.Tallennettava;
import simu.model.TapahtumanTyyppi;
import simu.model.TurvatarkastusPiste;

public interface IKontrolleri {
	
		// Rajapinta, joka tarjotaan  käyttöliittymälle:
	
		public void kaynnistaSimulointi();
		public void nopeuta();
		public void hidasta();
		public void asetaViive(long viive);
		public int[] getSali1Asetukset();
		public int[] getSali2Asetukset();
		public Boolean isStarted();
		
		// Rajapinta, joka tarjotaan moottorille:
		
		public void naytaLoppuaika(double aika);
		public void siirräTurvaJonoon(TurvatarkastusPiste tarkastusPiste, TapahtumanTyyppi tyyppi);
		public void siirräTurvaPalveltava(TurvatarkastusPiste tarkastusPiste);
		public void siirräTurvastaSaliin(TurvatarkastusPiste piste, Paikka päämäärä);
		public void poistaTurvasta(TurvatarkastusPiste piste);
		public void saliTyhjennys(Paikka päämäärä);
		public void laitaAikaTeksti(double aika);
		public void asetaSalinTila(Paikka sali, String tila);
		public void simulaatiLoppu(int sali1Käviät, int sali2Käviät, double sali1PalveluAika, double sali2PalveluAika);
		public void setSali1Asetukset(int[] sali1Asetukset);
		public void setSali2Asetukset(int[] sali2Asetukset);
		public void setSaliTekstit(Paikka sali, String saliAlkaaTeksti, String saliLoppuuTeksti);
		public void setSaliYleisöTeksti(Paikka sali, String yleisöTeksti);
		public ObservableList<Tallennettava> getTulokset();
		public void createTulos(int sisaanPaasseet1, int poisJaaneet1, int sisaanPaasseet2, int poisJaaneet2,
				double asiakkaanOdotusAVG);
}
