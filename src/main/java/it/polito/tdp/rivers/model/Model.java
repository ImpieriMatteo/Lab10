package it.polito.tdp.rivers.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private Map<Integer, River> idMap;
	private Simulator sim;
	
	public Model() {
		this.dao = new RiversDAO();
		this.idMap = new HashMap<>();
		dao.getAllRivers(idMap);
		
		this.sim = new Simulator();
	}

	public Collection<River> getAllRivers() {
		return this.idMap.values();
	}
	
	public void loadFlow(River river) {
		this.dao.loadFlow(river);
	}
	
	public void simula(double k, double fMed, List<Flow> flussi) {
		this.sim.init(k, fMed, flussi);
		this.sim.run();
	}
	
	public int getNumGiorniDisservizio() {
		return this.sim.getNumGiorniDisservizio();
	}

	public double getCmed() {
		return this.sim.getCmed();
	}

}
