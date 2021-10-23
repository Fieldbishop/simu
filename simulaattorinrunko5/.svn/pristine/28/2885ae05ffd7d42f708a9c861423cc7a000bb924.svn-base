package view;

import controller.IKontrolleri;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import simu.model.Paikka;

/**
 * @author Ville
 *
 */
public class SaliAsetuksetController {
	@FXML
	private TextField odotusAika;
	@FXML
	private TextField kesto;
	@FXML
	private TextField asiakasMäärä;
	@FXML
	private Label saliTeksti;
	
	private IKontrolleri kontrolleri;
	private int[] asetukset;
	private Paikka sali;
    private Stage settingsStage;
    @FXML
    private void initialize() {
    	
    }
    
    public void setSettingStage(Stage settingsStage) {
        this.settingsStage = settingsStage;
    }
    
    public void setKontrolleri(IKontrolleri kontrolleri) {
    	this.kontrolleri = kontrolleri;
    }
    
    public void setSali(Paikka sali) { 
    	switch(sali) {
		case SALI1:
			asetukset = kontrolleri.getSali1Asetukeset();
			saliTeksti.setText("Sali 1");
			break;
		case SALI2:
			asetukset = kontrolleri.getSali2Asetukeset();
			saliTeksti.setText("Sali 2");
			break;
		default:
			break;
    	}
		this.sali = sali;
    	odotusAika.setText(Integer.toString(asetukset[0]));
    	kesto.setText(Integer.toString(asetukset[1]));
    	asiakasMäärä.setText(Integer.toString(asetukset[2]));
    }
	@FXML
	private void handelSave() {
		try {
			asetukset[0] = Integer.parseInt(odotusAika.getText());
			asetukset[1] = Integer.parseInt(kesto.getText());
			asetukset[2] = Integer.parseInt(asiakasMäärä.getText());
			
			for (int i = 0; i < asetukset.length; i++) {
				if(asetukset[i] < 10) {
					throw new NumberFormatException();
				}
			}
			
	    	switch(this.sali) {
			case SALI1:
				kontrolleri.setSali1Asetukeset(asetukset);
				break;
			case SALI2:
				kontrolleri.setSali2Asetukeset(asetukset);
				break;
			default:
				break;
	    	}
	    	
	    	settingsStage.close();
		}
		catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(settingsStage);
            alert.setTitle("Väärän muotoiset arvot");
            alert.setHeaderText("Anna vain kokonaisnumeroita kenttiin");
            alert.setContentText("Arvojen pitää olla kokonaislukuja ja vähintää 10");            
            alert.showAndWait();

		}

	}
	
	@FXML
	private void handleCancel() {
		settingsStage.close();
	}
}
