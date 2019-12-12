package br.com.dashboardjtrac.funcoes;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.dashboardjtrac.model.ListaStatusJtrac;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FuncoesUtis {
	
	
	/**Formata uma data em timeStamp para formato BR String*/
	public static String converteTimeStamp(Timestamp data) {
		String dataFormatada = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			dataFormatada = sdf.format(data);
			
		} catch (Exception e) {
			System.out.println(e);
			dataFormatada = "";
		}
		return dataFormatada;
		
	}
	
	
	/**
	 * Formata uma string yyyy-MM-dd HH:mm:ss em LocalDateTime
	 * 
	 * @param dataTime
	 */
	public static LocalDateTime parseToLocalDateTime(String dataTime) {

		String str = dataTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

		return dateTime;
	}
	
	/***/
	
	/**
	 * Formata uma data em timeStamp para formato BR String
	 * 
	 * @param localDateTime
	 */
	public static String getFormatBRToExport(LocalDateTime localDateTime) {
		String dataFormatada = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
			
			dataFormatada = sdf.format(localDateTime);
			
		} catch (Exception e) {
			System.err.println(e);
			dataFormatada = "";
		}
		return dataFormatada;
		
	}
	
	
	public static String getDataToNameExport(LocalDateTime dataTime) {
		
		int hora = dataTime.getHour();
		int minutos = dataTime.getMinute();
		int segundos = dataTime.getSecond();
		
		String time = Integer.toString(hora).concat(Integer.toString(minutos)).concat(Integer.toString(segundos));
		
		int dia = dataTime.getDayOfMonth();
		int mes = dataTime.getMonthValue();
		int ano = dataTime.getYear();
		
		String data = Integer.toString(dia) + Integer.toString(mes) + Integer.toString(ano);
		
		return data + "_" + time;
		
	}
	
	public static void messageAlert(String messageAlert, AlertType iconAlert) {
        Alert alert = new Alert(iconAlert);
        alert.setTitle("Mensagem do sistema");
        alert.setHeaderText(null);
        alert.setContentText(messageAlert);
        alert.showAndWait();
    }
	
	public static void ordenaPorStatus(List<ListaStatusJtrac> lista) {
		
		Collections.sort(lista, new Comparator<ListaStatusJtrac>() {

			@Override
			public int compare(ListaStatusJtrac arg0, ListaStatusJtrac arg1) {
				
				return arg0.getStatus().compareTo(arg1.getStatus());
			}
		});
		
	}
	
	public static String formataDouble2Casas(Double valor) {
		
		DecimalFormat df = new DecimalFormat("#,###.00");
		String formatado = df.format(valor);
		
		return formatado;
	}
	
	

}
