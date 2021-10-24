package controller;

import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.model.ITallennettavaDAO;
import simu.model.OmaMoottori;
import simu.model.Paikka;
import simu.model.Tallennettava;
import simu.model.TallennettavaAccessObject;
import simu.model.TapahtumanTyyppi;
import simu.model.TurvatarkastusPiste;
import view.ISimulaattorinUI;

public class Kontrolleri implements IKontrolleri{
	
	private IMoottori moottori; 
	private ISimulaattorinUI ui;
	private ITallennettavaDAO model = new TallennettavaAccessObject();
	
	private Boolean started = false;
	//Salien asetukset defaultti arvot
	private int[] sali1Asetukset = {150, 150, 70};
	private int[] sali2Asetukset = {100, 80, 30};
	
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}

	@Override
	public int[] getSali1Asetukset() {
		return sali1Asetukset;
	}


	@Override
	public void setSali1Asetukset(int[] sali1Asetukset) {
		this.sali1Asetukset = sali1Asetukset;
	}


	@Override
	public int[] getSali2Asetukset() {
		return sali2Asetukset;
	}


	@Override
	public void setSali2Asetukset(int[] sali2Asetukset) {
		this.sali2Asetukset = sali2Asetukset;
	}
		
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
		Kello.getInstance().setAika(0);
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		started = true;
		((Thread)moottori).start();
	}
	
	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*1.10));
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*0.9));
	}
		
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()->ui.setLoppuaika(aika)); 
	}


	@Override
	public void siirräTurvaJonoon(TurvatarkastusPiste tarkastusPiste, TapahtumanTyyppi päämäärä) {
		Platform.runLater(()->ui.setTurvaAsiakasJono(tarkastusPiste, päämäärä)); 
	}


	@Override
	public void siirräTurvaPalveltava(TurvatarkastusPiste tarkastusPiste) {
		Platform.runLater(()->ui.setTurvaPalveltava(tarkastusPiste)); 
	}


	@Override
	public void siirräTurvastaSaliin(TurvatarkastusPiste piste, Paikka päämäärä) {
		Platform.runLater(()->ui.setTurvaToSali(piste, päämäärä)); 
	}


	@Override
	public void poistaTurvasta(TurvatarkastusPiste piste) {
		Platform.runLater(()->ui.poistaTurva(piste)); 
	}

	@Override
	public void saliTyhjennys(Paikka päämäärä) {
		Platform.runLater(()->ui.clearSali(päämäärä)); 
	}


	@Override
	public void laitaAikaTeksti(double aika) {
		Platform.runLater(()->ui.setAikaTeksti(aika)); 
	}


	@Override
	public void asetaViive(long viive) {
		moottori.setViive((long)(viive));
	}

	@Override
	public void asetaSalinTila(Paikka sali, String tila) {
		switch (sali) {
		case SALI1:
			Platform.runLater(()->ui.setSali1Tila(tila)); 
			break;
		case SALI2:
			Platform.runLater(()->ui.setSali2Tila(tila)); 
			break;

		default:
			break;
		}
	}



	@Override
	public void simulaatiLoppu(int sali1Käviät, int sali2Käviät, double sali1PalveluAika, double sali2PalveluAika) {
		started = false;
		Platform.runLater(()->ui.setSimulaattoriLoppu(sali1Käviät, sali2Käviät, sali1PalveluAika, sali2PalveluAika)); 
	}

	@Override
	public Boolean isStarted() {
		return started;
	}

	@Override
	public void setSaliTekstit(Paikka sali, String saliAlkaaTeksti, String saliLoppuuTeksti) {
		Platform.runLater(()->ui.setSaliTekstit(sali, saliAlkaaTeksti ,saliLoppuuTeksti)); 
		
	}

	@Override
	public void setSaliYleisöTeksti(Paikka sali, String yleisöTeksti) {
		Platform.runLater(()->ui.setSaliYleisöTeksti(sali, yleisöTeksti)); 
	}
	
	@Override
	public ObservableList<Tallennettava> getTulokset() {
		return FXCollections.observableArrayList(model.readAll());
	}
	
	@Override
	public void createTulos(
			int sisaanPaasseet1, 
			int poisJaaneet1,
			int sisaanPaasseet2, 
			int poisJaaneet2,
			double asiakkaanOdotusAVG) {
		Tallennettava uusiTulos = new Tallennettava(sisaanPaasseet1, poisJaaneet1, sali1Asetukset[0], sali1Asetukset[1], sisaanPaasseet2, poisJaaneet2, sali2Asetukset[0], sali2Asetukset[1], asiakkaanOdotusAVG);
		model.createEntry(uusiTulos);
	}
}
