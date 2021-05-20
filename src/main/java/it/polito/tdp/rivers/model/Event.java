package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{
	
	private LocalDate giorno;
	private Flow flusso;

	public Event(LocalDate giorno, Flow flusso) {
		this.giorno = giorno;
		this.flusso = flusso;
	}

	public LocalDate getGiorno() {
		return giorno;
	}

	public void setGiorno(LocalDate giorno) {
		this.giorno = giorno;
	}

	public Flow getFlusso() {
		return flusso;
	}

	public void setFlusso(Flow flusso) {
		this.flusso = flusso;
	}

	@Override
	public String toString() {
		return "Event [giorno=" + giorno + ", flusso=" + flusso + ", tipo=" + "]";
	}

	@Override
	public int compareTo(Event other) {
		return this.giorno.compareTo(other.giorno);
	}
	
	

}
