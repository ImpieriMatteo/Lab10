package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public void getAllRivers(Map<Integer, River> idMap) {
		
		final String sql = "SELECT id, name FROM river";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				idMap.put(res.getInt("id"), new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}

	public void loadFlow(River river) {
		
		final String sql = "SELECT f.river AS id, f.day AS giorno, f.flow AS flusso "
				+ "FROM flow AS f "
				+ "WHERE f.river = ?";
		
		List<Flow> flussi = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Flow flusso = new Flow(res.getDate("giorno").toLocalDate(), res.getFloat("flusso"), river);				
				flussi.add(flusso);
			}
			
			river.setFlows(flussi);
			river.setFlowAvg();

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
	}
}
