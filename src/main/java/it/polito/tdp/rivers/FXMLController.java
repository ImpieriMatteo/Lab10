/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void handleScelta(ActionEvent event) {
    	
    	River chosenOne = this.boxRiver.getValue();
    	this.model.loadFlow(chosenOne);
    	
    	List<Flow> flussi = chosenOne.getFlows();
    	this.txtStartDate.setText(flussi.get(0).getDay().toString());
    	this.txtEndDate.setText(flussi.get(flussi.size()-1).getDay().toString());
    	this.txtNumMeasurements.setText(""+flussi.size());
    	this.txtFMed.setText(String.format("%.3f", chosenOne.getFlowAvg()));
    	
    	this.btnSimula.setDisable(false);
    }
    
    @FXML
    void handleSimulazione(ActionEvent event) {
    	this.txtResult.clear();
    	double k;
    	
    	try {
    		k = Double.parseDouble(this.txtK.getText());
    		if(k<0)
    			throw new NumberFormatException();
    	}
    	catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire un NUMERO > 0");
    		return;
    	}
    	
    	River chosenOne = this.boxRiver.getValue();
    	this.model.simula(k, chosenOne.getFlowAvg(), chosenOne.getFlows());
    	
    	this.txtResult.appendText("Capacità media (m cubi al giorno): "+String.format("%.3f", this.model.getCmed())+"\n\n");
    	this.txtResult.appendText("Numero di giorni di disservizio: "+this.model.getNumGiorniDisservizio());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.boxRiver.getItems().addAll(this.model.getAllRivers());
    }
}
