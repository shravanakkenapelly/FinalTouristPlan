package com.tourplan.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GenerateDate {
	
	public static List<String> generateDatesBetween(LocalDate startDate, LocalDate endDate){
		List<String> dates = new ArrayList<>();
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate)+1;
		
		for(int i= 0; i<numOfDaysBetween; i++) {
			LocalDate date = startDate.plusDays(i);
			dates.add(date.toString());
		}
		return dates;
	}

}
