package view;

import controller.IKontrolleri;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import simu.model.Paikka;
import simu.model.Tallennettava;

public class VertailuController {
	
	@FXML
	private TableView<Tallennettava> ajo1;
	@FXML
	private TableView<Tallennettava> ajo2;
    @FXML
    private TableColumn<Tallennettava, Integer> idColumnTaulukko1;
    @FXML
    private TableColumn<Tallennettava, Integer> asiakkaatSali1Taulukko1;
    @FXML
    private TableColumn<Tallennettava, Integer> asiakkaatSali2Taulukko1;
    
    @FXML
    private TableColumn<Tallennettava, Integer> idColumnTaulukko2;
    @FXML
    private TableColumn<Tallennettava, Integer> asiakkaatSali1Taulukko2;
    @FXML
    private TableColumn<Tallennettava, Integer> asiakkaatSali2Taulukko2;
    
    @FXML
    private BarChart<String, Number> kaavioAjo1Sali1;
    @FXML
    private BarChart<String, Number> kaavioAjo1Sali2;
    @FXML
    private BarChart<String, Number> kaavioAjo2Sali1;
    @FXML
    private BarChart<String, Number> kaavioAjo2Sali2;
    
    
    @FXML
    private Label kestoAjo1Sali1;
    @FXML
    private Label odotusAjo1Sali1;
    @FXML
    private Label asiakkaitaAjo1Sali1;
    @FXML
    private Label mahdollisuusAjo1Sali1;
    @FXML
    private Label asiakasOdotusAjo1Sali1;
    
    @FXML
    private Label kestoAjo1Sali2;
    @FXML
    private Label odotusAjo1Sali2;
    @FXML
    private Label asiakkaitaAjo1Sali2;
    @FXML
    private Label mahdollisuusAjo1Sali2;
    @FXML
    private Label asiakasOdotusAjo1Sali2;
    
    @FXML
    private Label kestoAjo2Sali1;
    @FXML
    private Label odotusAjo2Sali1;
    @FXML
    private Label asiakkaitaAjo2Sali1;
    @FXML
    private Label mahdollisuusAjo2Sali1;
    @FXML
    private Label asiakasOdotusAjo2Sali1;
    
    @FXML
    private Label kestoAjo2Sali2;
    @FXML
    private Label odotusAjo2Sali2;
    @FXML
    private Label asiakkaitaAjo2Sali2;
    @FXML
    private Label mahdollisuusAjo2Sali2;
    @FXML
    private Label asiakasOdotusAjo2Sali2;
    
  
    private Stage vertailusStage;
    private IKontrolleri kontrolleri;
	
    @FXML
    private void initialize() {
    	idColumnTaulukko1.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
    	asiakkaatSali1Taulukko1.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getSisaanPaasseet1() + cellData.getValue().getPoisJaaneet1()).asObject());
    	asiakkaatSali2Taulukko1.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getSisaanPaasseet2() + cellData.getValue().getPoisJaaneet2()).asObject());
    	
    	ajo1.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAjoTulokset(newValue, 1));


        
    	idColumnTaulukko2.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
    	asiakkaatSali1Taulukko2.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getSisaanPaasseet1() + cellData.getValue().getPoisJaaneet1()).asObject());
    	asiakkaatSali2Taulukko2.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getSisaanPaasseet2() + cellData.getValue().getPoisJaaneet2()).asObject());
    	ajo2.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAjoTulokset(newValue, 2));
    	
        XYChart.Series<String, Number> taulukko1 = new Series<String, Number>();
        taulukko1.setName("Asiakkaat");       
        taulukko1.getData().add(new Data<String, Number>("Sisään päässeet", 0));
        taulukko1.getData().add(new Data<String, Number>("Pois jääneet", 0));
        kaavioAjo1Sali1.getData().add(taulukko1);
        kaavioAjo1Sali2.getData().add(taulukko1);
        kaavioAjo2Sali1.getData().add(taulukko1);
        kaavioAjo2Sali2.getData().add(taulukko1);
    }
    
    public void setVertailuStage(Stage vertailusStage) {
        this.vertailusStage = vertailusStage;

    }
    
    public void setKontrolleri(IKontrolleri kontrolleri) {
    	this.kontrolleri = kontrolleri; 
    	
    	ajo1.setItems((ObservableList<Tallennettava>) this.kontrolleri.getTulokset());
    	ajo2.setItems((ObservableList<Tallennettava>) this.kontrolleri.getTulokset());
    }
	@FXML
	public void handleTakaisin() {
		vertailusStage.close();
	}
	
	private void showAjoTulokset(Tallennettava tiedot, int ajoNumero) {
		BarChart<String, Number> kaavioSali1;
		BarChart<String, Number> kaavioSali2;
		
		switch (ajoNumero) {
		case 1:
			//Sali 1
			kaavioSali1 = kaavioAjo1Sali1;
			kestoAjo1Sali1.setText(Integer.toString(tiedot.getAsetusKesto1()));
			odotusAjo1Sali1.setText(Integer.toString(tiedot.getAsetusOdotusaika1()));
			asiakkaitaAjo1Sali1.setText(Integer.toString(tiedot.getSisaanPaasseet1() + tiedot.getPoisJaaneet1()));
			mahdollisuusAjo1Sali1.setText(Double.toString(tiedot.getSisaanPaasseetPoisJaaneetSuhde1()));
			asiakasOdotusAjo1Sali1.setText(Double.toString(tiedot.getAsiakkaanOdotusAVG()));
			
			//Sali 2
			kaavioSali2 = kaavioAjo1Sali2;
			kestoAjo1Sali2.setText(Integer.toString(tiedot.getAsetusKesto2()));
			odotusAjo1Sali2.setText(Integer.toString(tiedot.getAsetusOdotusaika2()));
			asiakkaitaAjo1Sali2.setText(Integer.toString(tiedot.getSisaanPaasseet2() + tiedot.getPoisJaaneet2()));
			mahdollisuusAjo1Sali2.setText(Double.toString(tiedot.getSisaanPaasseetPoisJaaneetSuhde2()));
			asiakasOdotusAjo1Sali2.setText(Double.toString(tiedot.getAsiakkaanOdotusAVG()));
			break;
		case 2:
			kaavioSali1 = kaavioAjo2Sali1;
			kestoAjo2Sali1.setText(Integer.toString(tiedot.getAsetusKesto1()));
			odotusAjo2Sali1.setText(Integer.toString(tiedot.getAsetusOdotusaika1()));
			asiakkaitaAjo2Sali1.setText(Integer.toString(tiedot.getSisaanPaasseet1() + tiedot.getPoisJaaneet1()));
			mahdollisuusAjo2Sali1.setText(Double.toString(tiedot.getSisaanPaasseetPoisJaaneetSuhde1()));
			asiakasOdotusAjo2Sali1.setText(Double.toString(tiedot.getAsiakkaanOdotusAVG()));
			
			kaavioSali2 = kaavioAjo2Sali2;
			kestoAjo2Sali2.setText(Integer.toString(tiedot.getAsetusKesto2()));
			odotusAjo2Sali2.setText(Integer.toString(tiedot.getAsetusOdotusaika2()));
			asiakkaitaAjo2Sali2.setText(Integer.toString(tiedot.getSisaanPaasseet2() + tiedot.getPoisJaaneet2()));
			mahdollisuusAjo2Sali2.setText(Double.toString(tiedot.getSisaanPaasseetPoisJaaneetSuhde2()));
			asiakasOdotusAjo2Sali2.setText(Double.toString(tiedot.getAsiakkaanOdotusAVG()));
			break;
		default:
			 kaavioSali1 = null;
			 kaavioSali2 = null;
			break;
		}
		kaavioSali1.getData().clear();
		kaavioSali2.getData().clear();
		
        XYChart.Series<String, Number> taulukko1 = new Series<String, Number>();
        taulukko1.setName("Asiakkaat");       
        taulukko1.getData().add(new XYChart.Data<String, Number>("Sisään päässeet", tiedot.getSisaanPaasseet1()));
        taulukko1.getData().add(new XYChart.Data<String, Number>("Pois jääneet", tiedot.getPoisJaaneet1()));
        kaavioSali1.getData().add(taulukko1);
		
        XYChart.Series<String, Number> taulukko2 = new Series<String, Number>();
        taulukko2.setName("Asiakkaat");       
        taulukko2.getData().add(new XYChart.Data<String, Number>("Sisään päässeet", tiedot.getSisaanPaasseet2()));
        taulukko2.getData().add(new XYChart.Data<String, Number>("Pois jääneet", tiedot.getPoisJaaneet2()));
        kaavioSali2.getData().add(taulukko2);
	}
}
