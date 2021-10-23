package controller;

import simu.model.Paikka;
import simu.model.TapahtumanTyyppi;
import simu.model.TurvatarkastusPiste;

public interface IKontrolleri {
	
		// Rajapinta, joka tarjotaan  käyttöliittymälle:
	
		public void kaynnistaSimulointi();
		public void nopeuta();
		public void hidasta();
		public void asetaViive(long viive);
		public int[] getSali1Asetukeset();
		public int[] getSali2Asetukeset();
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
		public void setSali1Asetukeset(int[] sali1Asetukeset);
		public void setSali2Asetukeset(int[] sali2Asetukeset);
		public void setSaliTekstit(Paikka sali, String saliAlkaaTeksti, String saliLoppuuTeksti);
		public void setSaliYleisöTeksti(Paikka sali, String yleisöTeksti);
}
