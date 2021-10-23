package view;

import java.io.IOException;


import controller.IKontrolleri;
import controller.Kontrolleri;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.Paikka;
import simu.model.TapahtumanTyyppi;
import simu.model.TurvatarkastusPiste;

public class SimulaattoriOverviewController extends Application implements ISimulaattorinUI {
    @FXML
    private Text sali1Tila;
    @FXML
    private Text sali2Tila;
    @FXML
    private Text sali1TietoKenttä;
    @FXML
    private Text sali2TietoKenttä;
    @FXML
    private Text aika;
    @FXML
    private Label viiveArvo;
    @FXML
    private Label sali1Palveluaika;
    @FXML
    private Label sali2Palveluaika;
    @FXML
    private Label asiakkaitaSali1;
    @FXML
    private Label asiakkaitaSali2;
    


	@FXML
    private Label sali1AlkaaTeksti;
    @FXML
    private Label sali1LoppuuTeksti;
    @FXML
    private Label sali1AsiakasTeksti;
    
    @FXML
    private Label sali2AlkaaTeksti;
    @FXML
    private Label sali2LoppuuTeksti;
    @FXML
    private Label sali2AsiakasTeksti;
    
    @FXML
    private TextField simulointiAika;
    @FXML
    private Slider viiveSlider;
    
    @FXML
    private FlowPane turva1AsiakasJono;
    @FXML
    private FlowPane turva2AsiakasJono;
    
    @FXML
    private FlowPane sali1Asiakkaat;
    @FXML
    private FlowPane sali2Asiakkaat;
    
    @FXML
    private HBox turva1Palveltava;
    @FXML
    private HBox turva2Palveltava;
    @FXML
    private HBox aspaPalveltava;
    @FXML
    private Button simulaattoriAloitus;
    @FXML
    private Button sali1Asetukset;
    @FXML
    private Button sali2Asetukset;
    
	private IKontrolleri kontrolleri;
    private AnchorPane rootLayout;
	private Stage primaryStage;
    
	
	@Override
	public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Käräjäoikeus simulaattori");
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimulaattoriOverviewController.class.getResource("../view/simulaattoriOverview.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Avaa salin asetukset.
     */
    public void openSaliAsetukset(Paikka sali) {
		if(kontrolleri == null) {
			kontrolleri = new Kontrolleri(this);
		}
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimulaattoriOverviewController.class.getResource("../view/SaliAsetukset.fxml"));
            VBox page = (VBox) loader.load();
            Stage asetuksetStage = new Stage();
            asetuksetStage.setTitle("Salin asetukset");
            asetuksetStage.initModality(Modality.WINDOW_MODAL);
            asetuksetStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            asetuksetStage.setScene(scene);
            asetuksetStage.setResizable(false);

            
            SaliAsetuksetController controller = loader.getController();
            controller.setSettingStage(asetuksetStage);
            controller.setKontrolleri(kontrolleri);
            controller.setSali(sali);
            
            asetuksetStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleSali1Asetukset() {
    	openSaliAsetukset(Paikka.SALI1);
    }
    
    @FXML
    private void handleSali2Asetukset() {
    	openSaliAsetukset(Paikka.SALI2);
    }
    
    @FXML
    private void handleSimulaatioAloitus() {
    	clearSimulaattori();

		if(kontrolleri == null) {
			kontrolleri = new Kontrolleri(this);
		}
		simulaattoriAloitus.setDisable(true);
		sali1Asetukset.setDisable(true);
		sali2Asetukset.setDisable(true);
		setSali1Tila("AUKI");
		setSali2Tila("AUKI");
		kontrolleri.kaynnistaSimulointi();
    }
    
    @FXML
    private void handleSimulaatioNopeus() {
    }
    
    private void clearSimulaattori() {
    	turva1AsiakasJono.getChildren().clear();
    	turva2AsiakasJono.getChildren().clear();
    	sali1Asiakkaat.getChildren().clear();
    	sali2Asiakkaat.getChildren().clear();
    	
    	turva1Palveltava.getChildren().clear();
    	turva2Palveltava.getChildren().clear();
    	aspaPalveltava.getChildren().clear();
    	asiakkaitaSali1.setText("");
    	asiakkaitaSali2.setText("");
    	sali1Palveluaika.setText("");
    	sali2Palveluaika.setText("");
    }
	@Override
	public void setTurvaAsiakasJono(TurvatarkastusPiste tarkastusPiste, TapahtumanTyyppi päämäärä) {
		Circle asiakas = new Circle();
		asiakas.setRadius(12);
		switch (päämäärä) {
		case RESPADEP:
			asiakas.setFill(Color.YELLOW);
			break;
		case SALIDEP1:
			asiakas.setFill(Color.DARKGREEN);
			break;
		case SALIDEP2:
			asiakas.setFill(Color.DARKBLUE);
			break;
		default:
			break;

		}
		switch(tarkastusPiste) {
		case TURVA1:
			turva1AsiakasJono.getChildren().add(asiakas);
			break;
		case TURVA2:
			turva2AsiakasJono.getChildren().add(asiakas);
			break;
		default:
			break;
		}
		
	}
	@Override
	public void setTurvaPalveltava(TurvatarkastusPiste tarkastusPiste) {
		Circle asiakas;
		switch(tarkastusPiste) {
		case TURVA1:
			asiakas = (Circle) turva1AsiakasJono.getChildren().get(0);
			turva1AsiakasJono.getChildren().remove(asiakas);
			turva1Palveltava.getChildren().add(asiakas);
			break;
		case TURVA2:
			asiakas = (Circle) turva1AsiakasJono.getChildren().get(0);
			turva1AsiakasJono.getChildren().remove(asiakas);
			turva2Palveltava.getChildren().add(asiakas);
			break;
		default:
			break;
		}

	}
	@Override
	public void setTurvaToSali(TurvatarkastusPiste piste, Paikka päämäärä) {
		Circle asiakas = null;
		switch(piste) {
			case TURVA1:
				asiakas = (Circle) turva1Palveltava.getChildren().get(0);
				turva1Palveltava.getChildren().remove(asiakas);
				break;
			case TURVA2:
				asiakas = (Circle) turva2Palveltava.getChildren().get(0);
				turva2Palveltava.getChildren().remove(asiakas);
				break;
			default:
				System.out.println("Piste annettu väärin");
				break;
		}
		
		switch(päämäärä) {
		case SALI1:
			sali1Asiakkaat.getChildren().add(asiakas);
			break;
		case SALI2:
			sali2Asiakkaat.getChildren().add(asiakas);
			break;
		case RESPA:
			aspaPalveltava.getChildren().add(asiakas);
			break;
		default:
			System.out.println("Salia ei ole olemassa");
			break;
		}

	}
	@Override
	public void poistaTurva(TurvatarkastusPiste piste) {
		Circle asiakas = null;
		switch(piste) {
		case TURVA1:
			asiakas = (Circle) turva1Palveltava.getChildren().get(0);
			turva1Palveltava.getChildren().remove(asiakas);
			break;
		case TURVA2:
			asiakas = (Circle) turva2Palveltava.getChildren().get(0);
			turva2Palveltava.getChildren().remove(asiakas);
			break;
		default:
			System.out.println("Piste annettu väärin");
			break;
	}
	}
	
	@Override
	public void clearSali(Paikka päämäärä) {
		switch(päämäärä) {
		case SALI1:
			sali1Asiakkaat.getChildren().remove(0);
			break;
		case SALI2:
			sali2Asiakkaat.getChildren().remove(0);
			break;
		case RESPA:
			aspaPalveltava.getChildren().remove(0);
			break;
		default:
			break;
		
		}
	}
	@Override
	public void init(){
		Trace.setTraceLevel(Level.INFO);
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public double getAika() {
		int tulos = 0;
		try{
			String text = simulointiAika.getText();
			tulos = Integer.parseInt(text);
		}
		catch(RuntimeException e) {
			System.out.println("Ei voi laittaaa tekstiä");
		}
		return tulos;
	}
	@Override
	public long getViive() {
		return (long) viiveSlider.getValue();
	}
	@Override
	public void setLoppuaika(double aika) {
		
	}
	
	public static void main(String[] args) {

		launch(args);
	}


	@Override
	public void setAikaTeksti(double aika) {
		this.aika.setText(String.format("%.2f", aika));
	}
	
	@Override
	public void viiveMuuttunut() {
		if(kontrolleri != null) {
			if(kontrolleri.isStarted()) {
				kontrolleri.asetaViive((long)viiveSlider.getValue());			
			}
		}
		this.viiveArvo.setText(String.format("%.2f", viiveSlider.getValue()));
	}

	@Override
	public void setSali1Tila(String tila) {
		this.sali1Tila.setText(tila);
	}
	
	@Override
	public void setSali2Tila(String tila) {
		this.sali2Tila.setText(tila);
	}
	
	@Override
	public void setLopputulokset(String tila) {
		this.sali2Tila.setText(tila);
	}
	
	@Override
	public void setSimulaattoriLoppu(int sali1Käviät, int sali2Käviät, double sali1PalveluAika, double sali2PalveluAika) {
		sali1Palveluaika.setText(String.format("%.2f", sali1PalveluAika));
		sali2Palveluaika.setText(String.format("%.2f", sali2PalveluAika));
		asiakkaitaSali1.setText(Integer.toString(sali1Käviät));
		asiakkaitaSali2.setText(Integer.toString(sali2Käviät));
		simulaattoriAloitus.setDisable(false);
		sali1Asetukset.setDisable(false);
		sali2Asetukset.setDisable(false);
	}
	
	@Override
	public void setSaliTekstit(Paikka sali, String saliAlkaaTeksti, String sal1LoppuuTeksti) {
		switch(sali) {
		case SALI1:
			this.sali1AlkaaTeksti.setText(saliAlkaaTeksti);
			this.sali1LoppuuTeksti.setText(sal1LoppuuTeksti);
			break;
		case SALI2:
			this.sali2AlkaaTeksti.setText(saliAlkaaTeksti);
			this.sali2LoppuuTeksti.setText(sal1LoppuuTeksti);
			break;
		default:
			break;	
		}
	}
	
	@Override
	public void setSaliYleisöTeksti(Paikka sali, String yleisöTeksti) {
		switch(sali) {
		case SALI1:
			this.sali1AsiakasTeksti.setText(yleisöTeksti);
			break;
		case SALI2:
			this.sali2AsiakasTeksti.setText(yleisöTeksti);
			break;
		default:
			break;	
		}
	}
}
