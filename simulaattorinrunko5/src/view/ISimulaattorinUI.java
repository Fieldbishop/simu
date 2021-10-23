package view;

import simu.model.Paikka;
import simu.model.TapahtumanTyyppi;
import simu.model.TurvatarkastusPiste;

public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();
	public long getViive();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setLoppuaika(double aika);
	public void setTurvaAsiakasJono(TurvatarkastusPiste tarkastusPiste, TapahtumanTyyppi päämäärä);
	public void setTurvaPalveltava(TurvatarkastusPiste tarkastusPiste);
	public void setTurvaToSali(TurvatarkastusPiste piste, Paikka päämäärä);
	public void poistaTurva(TurvatarkastusPiste piste);
	public void clearSali(Paikka päämäärä);
	public void setAikaTeksti(double aika);
	public void viiveMuuttunut();
	public void setSali1Tila(String tila);
	public void setSali2Tila(String tila);
	public void setLopputulokset(String tila);
	public void setSimulaattoriLoppu(int sali1Käviät, int sali2Käviät, double sali1PalveluAika, double sali2PalveluAika);
	public void setSaliTekstit(Paikka sali, String saliAlkaaTeksti, String saliLoppuuTeksti);
	public void setSaliYleisöTeksti(Paikka sali, String yleisöTeksti);
}
