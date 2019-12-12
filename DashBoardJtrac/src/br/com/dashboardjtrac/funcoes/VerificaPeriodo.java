package br.com.dashboardjtrac.funcoes;

import java.time.LocalTime;

import br.com.dashboardjtrac.Enumerics.EnumPeriodosDoDia;

public class VerificaPeriodo {
	
	public static String verificaPeriodo(String sistema, String dataAbertura) {
		
		
		/**
		 * 
		 * manhã: seg-sab 6:00-13:45
			
			tarde: seg-sex 13:45-22:00
			sab 13:45-18:00
			
			noite: seg-sex 22:00-6:00 (5 dias na semana)
			
			sem cobertura:
			sab 18:00-seg 6:00
			
		 */
		
		LocalTime time = FuncoesUtis.parseToLocalDateTime(dataAbertura).toLocalTime();
		String weekday = FuncoesUtis.parseToLocalDateTime(dataAbertura).toLocalDate().getDayOfWeek().toString();
		
		
		LocalTime manha = LocalTime.of(06, 00, 00);
		LocalTime tarde = LocalTime.of(13, 45, 00);
		LocalTime noite = LocalTime.of(22, 00, 00);
		LocalTime noiteSabado = LocalTime.of(18, 00, 00);
		
		String result = "";
		
		System.out.println(weekday);
		
		
		switch (weekday) {
		case "MONDAY":
		case "TUESDAY":
		case "WEDNESDAY":
		case "THURSDAY":
		case "FRIDAY":
			
			/**Turno manhã*/
			if(time.isAfter(manha) && time.isBefore(tarde)) {
				result = sistema + " - " + EnumPeriodosDoDia.MANHA.getPeriodo();
			
			/**Turno Tarde*/
			}else if (time.isAfter(tarde) && time.isBefore(noite)) {
				result = sistema + " - " + EnumPeriodosDoDia.TARDE.getPeriodo();
				
				
			/**Turno Noite*/
			}else if((time.isAfter(noite) || time.isBefore(LocalTime.MIDNIGHT)) || (time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(manha)) && weekday != "MONDAY") {
				result = sistema + " - " + EnumPeriodosDoDia.NOITE.getPeriodo();
			
				/**Madrugada de domingo para segunda*/
			}else if((time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(manha)) && weekday == "MONDAY") {
				result = sistema + " - " + EnumPeriodosDoDia.SEM_COBERTURA.getPeriodo();
			}
			
			break;
			
		case "SATURDAY":
			
			if(time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(manha)) {
				result = sistema + " - " + EnumPeriodosDoDia.NOITE.getPeriodo();
				
			}else if(time.isAfter(manha) && time.isBefore(tarde)) {
				result = sistema + " - " + EnumPeriodosDoDia.MANHA.getPeriodo();
				
			}else if(time.isAfter(tarde) && time.isBefore(noiteSabado)) {
				result = sistema + " - " + EnumPeriodosDoDia.TARDE.getPeriodo();
				
			}else if(time.isAfter(noiteSabado)) {
				result = sistema + " - " + EnumPeriodosDoDia.SEM_COBERTURA.getPeriodo();
				
			}
			
			break;

		default:
			result = sistema + " - " + EnumPeriodosDoDia.SEM_COBERTURA.getPeriodo();
			break;
		}
		
		
		System.out.println(result);
		System.out.println(time);
		System.out.println("----------------------------------------------------------------------");
		
		return result;
	}

}
