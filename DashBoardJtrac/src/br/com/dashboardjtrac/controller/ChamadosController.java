package br.com.dashboardjtrac.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import br.com.dashboardjtrac.DAO.ChamadosDAO;
import br.com.dashboardjtrac.DAO.SpacesJtrac;
import br.com.dashboardjtrac.funcoes.CalculaSummaryResults;
import br.com.dashboardjtrac.funcoes.ExportaCSV;
import br.com.dashboardjtrac.funcoes.FuncoesUtis;
import br.com.dashboardjtrac.model.ChamadosModel;
import br.com.dashboardjtrac.persistence.AbstractAccessDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class ChamadosController implements Initializable{

    @FXML
    private JFXTextField txtNumeroChamado;

    @FXML
    private JFXDatePicker dateDatainicio;

    @FXML
    private JFXDatePicker dateDataFim;

    @FXML
    private JFXComboBox<String> comboSistema;

    @FXML
    private Label labelQtdChamados;

    @FXML
    private TableView<ChamadosModel> gradeChamados;

    @FXML
    private TableColumn<ChamadosModel, Long> colChamados;

    @FXML
    private TableColumn<ChamadosModel, String> colSistema;
    
    @FXML
    private TableColumn<ChamadosModel, String> colStatus;
    
    @FXML
    private TableColumn<ChamadosModel, String> colDataAbertura;

    @FXML
    private TableColumn<ChamadosModel, String> colDataFechamento;
    
    @FXML
    private TableColumn<ChamadosModel, String> colCategoria;
    
    @FXML
    private TableColumn<ChamadosModel, String> colPrioridade;
    
    @FXML
    private TableColumn<ChamadosModel, String> colEffortTime;
    
    @FXML
    private JFXButton btnCarrergaGrade;

    @FXML
    private JFXButton btnExposrtarCSV;
    
    @FXML
    private JFXButton btnTeste;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		renderizaGrade();
		//getGradeChamados();
		carregaComboSistema();

	}
	

	
	private void renderizaGrade() {
		
		colChamados.setCellValueFactory(new PropertyValueFactory<ChamadosModel, Long>("sequence_num"));
		colSistema.setCellValueFactory(new PropertyValueFactory<ChamadosModel, String>("prefix_code"));
		colStatus.setCellValueFactory(new PropertyValueFactory<ChamadosModel, String>("status"));
		colDataAbertura.setCellValueFactory(new PropertyValueFactory<ChamadosModel, String>("dataAbertura"));
		colDataFechamento.setCellValueFactory(new PropertyValueFactory<ChamadosModel, String>("dataFechamento"));
		colCategoria.setCellValueFactory(new PropertyValueFactory<ChamadosModel, String>("categoria"));
		colPrioridade.setCellValueFactory(new PropertyValueFactory<ChamadosModel, String>("prioridade"));
		colEffortTime.setCellValueFactory(new PropertyValueFactory<ChamadosModel, String>("effortTime"));

	}
	
	private void getGradeChamados() {
		
		try {
			
			LocalDate dataIni = dateDatainicio.getValue();
	    	LocalDate dataFim = dateDataFim.getValue();
	    	String prefix_code = null;
	    	
	    	if(!comboSistema.getSelectionModel().getSelectedItem().equalsIgnoreCase("SELECIONE")) {
	    		prefix_code = comboSistema.getSelectionModel().getSelectedItem();
	    	}
	    	
	    	Integer sequence_num = 0;
	    	
	    	int comparaData = 0;
	    	
	    	if(dataIni != null && dataFim != null) {
	    		comparaData = dataIni.compareTo(dataFim);
	    	}
	    	
	    	if(comparaData <= 0 && (dataIni != null && dataFim != null) || (dataIni == null && dataFim == null)) {
	    		
	    		if(!txtNumeroChamado.getText().isEmpty()) {
	    			sequence_num = Integer.parseInt(txtNumeroChamado.getText().trim());
	    		}
	    		
	    		//System.out.println("OK executo o que devo fazer");
	    		ChamadosDAO dao = new ChamadosDAO();
				List<ChamadosModel> list = dao.findAllChamados(dataIni, dataFim, prefix_code, sequence_num);
				
				gradeChamados.getItems().clear();
				gradeChamados.getItems().addAll(list);
				
				labelQtdChamados.setText(countRowGradeChamados().toString());
				
				FuncoesUtis.messageAlert("Dados gerados com sucesso!", AlertType.INFORMATION);
	    		
	    	}else {
	    		FuncoesUtis.messageAlert("A data de inicio não pode ser maior que a data fim", AlertType.WARNING);
	    	}
	    	
			
			
		} catch (Exception e) {
			System.err.println("Erro ao carregar grade chamados" + e);
		}
	}
	
	private Integer countRowGradeChamados() {
		
		Integer row = Integer.valueOf(gradeChamados.getItems().size());
		
		return row;
		
	}
	
	
	@FXML
    void carregaGradeOnAction(ActionEvent event) {
		getGradeChamados();
    }

    @FXML
    void exportarCSVOnAction(ActionEvent event) {
    	
    	FuncoesUtis.messageAlert("Atenção\n"
    			+ "Esta ação poderá levar alguns minutos."
    			+ "O processo terá terminado quando aparecer a segunda caixa de confirmação.", AlertType.INFORMATION);
    	
    	final DirectoryChooser dirChooser = new DirectoryChooser();
    	
    	File file = dirChooser.showDialog(null);
    	String path = file.toString();
    	
    	LocalDate dataIni = dateDatainicio.getValue();
    	LocalDate dataFim = dateDataFim.getValue();
    	String prefix_code = null;
    	Integer sequence_num = 0;
    	
    	int comparaData = 0;
    	
    	if(!comboSistema.getSelectionModel().getSelectedItem().equalsIgnoreCase("SELECIONE")) {
    		prefix_code = comboSistema.getSelectionModel().getSelectedItem();
    	}
    	
    	if(dataIni != null && dataFim != null) {
    		comparaData = dataIni.compareTo(dataFim);
    	}
    	
    	if(comparaData <= 0 && (dataIni != null && dataFim != null) || (dataIni == null && dataFim == null)) {
    		
    		if(!txtNumeroChamado.getText().isEmpty()) {
    			sequence_num = Integer.parseInt(txtNumeroChamado.getText().trim());
    		}
    		
    		ChamadosDAO dao = new ChamadosDAO();
    		List<ChamadosModel> list = dao.findAllChamados(dataIni, dataFim, prefix_code, sequence_num);
        	
        	if(file != null) {
        		
        		try {
        			ExportaCSV.exportaCSV(list, path);
        			ExportaCSV.exportaResultadosCSV(summarioResultados(list), path);
    			} catch (Exception e) {
    				System.err.println("Erro: " + e);
    			}
        	}
    	}
    	
    }
    
    
    private void carregaComboSistema() {
	
    	try {
			
    		SpacesJtrac dao = new SpacesJtrac();
        	
        	List<String> list = dao.findPrefixCodeSistema();
        	
        	comboSistema.getItems().clear();
        	comboSistema.getItems().add("SELECIONE");
        	comboSistema.getItems().addAll(list);
        	
        	comboSistema.getSelectionModel().select(0);
    		
		} catch (Exception e) {
			System.err.println("Erro ao carreagr combo sistema: " + e);
		}
    }
    
    
    private List<String> summarioResultados(List<ChamadosModel> list) {
    	
    	List<String> resultados = new ArrayList<String>();
    	
    	resultados = CalculaSummaryResults.calculaSummary(list);
    	
    	return resultados;
    	
    	
//    	LocalDate dataIni = dateDatainicio.getValue();
//    	LocalDate dataFim = dateDataFim.getValue();
//    	String prefix_code = null;
//    	Integer sequence_num = 0;
//    	
//    	int comparaData = 0;
//    	
//    	if(!comboSistema.getSelectionModel().getSelectedItem().equalsIgnoreCase("SELECIONE")) {
//    		prefix_code = comboSistema.getSelectionModel().getSelectedItem();
//    	}
//    	
//    	if(dataIni != null && dataFim != null) {
//    		comparaData = dataIni.compareTo(dataFim);
//    	}
//    	
//    	if(comparaData <= 0 && (dataIni != null && dataFim != null) || (dataIni == null && dataFim == null)) {
//    		
//    		if(!txtNumeroChamado.getText().isEmpty()) {
//    			sequence_num = Integer.parseInt(txtNumeroChamado.getText().trim());
//    		}
//    		
//    		ChamadosDAO dao = new ChamadosDAO();
//    		List<ChamadosModel> list = dao.findAllChamados(dataIni, dataFim, prefix_code, sequence_num);
//        	
//        	
//    		CalculaSummaryResults.calculaSummary(list);
//    		
//    		
//    	}
    	
    	
    	
    }
    
    
    
    @FXML
    void testeOnAction(ActionEvent event) {
    	
    	//summarioResultados();
    }

}
