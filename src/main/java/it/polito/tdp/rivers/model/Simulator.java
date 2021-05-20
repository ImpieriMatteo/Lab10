package it.polito.tdp.rivers.model;

import java.util.List;
import java.util.PriorityQueue;

public class Simulator {
	
	// coda degli eventi
	private PriorityQueue<Event> queue;
	
	// modello del mondo
	private double Q;
	private double C;
	private double fOutMin;
	private double fOutMax;
	
	// parametri di input
	private double fMed;
	private double fIn;
	private int numMisurazioni;
	
	// parametri di output
	private int numGiorniDisservizio;
	private double Cmed;

	public void init(double k, double fMed, List<Flow> flussi) {
		this.queue = new PriorityQueue<>();
		
		this.fMed = fMed*60*60*24;
		this.Q = k*this.fMed*30;
		this.fIn = 0.0;
		this.C = this.Q/2;
		
		this.fOutMin = 0.8*this.fMed;
		this.fOutMax = this.fOutMin*10;
		this.numMisurazioni = flussi.size();
		
		this.numGiorniDisservizio = 0;
		this.Cmed = 0.0;
		
		for(Flow f : flussi) 
			this.queue.add(new Event(f.getDay(), f));
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.fIn = e.getFlusso().getFlow()*60*60*24;
			
			if(this.C+this.fIn>this.Q) {
				// Il flusso d'acqua che entra esce subito quindi non lo aggiungo
				
				int p = (int) (Math.random()*100);
				if(p<5) {
					if(this.C-this.fOutMax<0) {
						this.numGiorniDisservizio++;
						this.C = 0;
					}
					else
						this.C -= this.fOutMax;
				}
				else {
					if (this.C-this.fOutMin<0) {
						this.numGiorniDisservizio++;
						this.C = 0;
					}
					else
						this.C -= this.fOutMin;
				}
			}
			else {
				// Il flusso d'acqua entra e rimane dentro
				this.C += this.fIn;
				
				int p = (int) (Math.random()*100);
				if (p < 5) {
					if (this.C-this.fOutMax<0) {
						this.numGiorniDisservizio++;
						this.C = 0;
					}
					else
						this.C -= this.fOutMax;
				}
				else {
					if (this.C-this.fOutMin<0) {
						this.numGiorniDisservizio++;
						this.C = 0;
					}
					else
						this.C -= this.fOutMin;
				}
			}
			
			this.Cmed += this.C;
		}
		
		this.Cmed = this.Cmed/this.numMisurazioni;
	}

	public int getNumGiorniDisservizio() {
		return numGiorniDisservizio;
	}

	public double getCmed() {
		return Cmed;
	}
	
	
}
