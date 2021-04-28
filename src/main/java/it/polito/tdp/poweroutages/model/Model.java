package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	List<PowerOutage> partenza;
	List<PowerOutage> best = new ArrayList<>();
	
	private int anni;
	private int ore;
	
	private int maxPeople;
	
	public Model() {
		podao = new PowerOutageDAO();
		if (partenza != null)
			partenza.removeAll(partenza);
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> getSpecificIdNercList(Nerc nerc) {
		
		partenza = new ArrayList<>();
		
		this.partenza.addAll(podao.getSpecificIdNercList(nerc));
		
		return podao.getSpecificIdNercList(nerc);
	}
	
	public List<PowerOutage> getWantedOutages(Nerc n, int anni, int ore) {
		
		this.getSpecificIdNercList(n);
		
		List<PowerOutage> parziale = new ArrayList<>();
		this.maxPeople = 0;
		this.ore = ore;
		this.anni = anni;
				
		this.recursive(parziale, 0);
							
		return best;
	}
	
	public int peopleAffected(List<PowerOutage> l) {
		
		int cont = 0;
		
		for(PowerOutage po:l)
			cont += po.getCustomers_affected();
		
		return cont;
	}
	
	public void recursive(List<PowerOutage> parziale, int livello) {
				

		if (oreParziale(parziale) > this.ore || anniParziale(parziale) > this.anni)
			return;	

		if (livello==partenza.size()) {
			if (best.size()==0 || peopleAffected(parziale) > maxPeople) {
					maxPeople = peopleAffected(parziale);
					best = new ArrayList<>(parziale);
			}
			return;
		}
				
		parziale.add(partenza.get(livello));   	
		recursive(parziale,livello+1);				
		
		parziale.remove(partenza.get(livello));   	
		recursive(parziale,livello+1);				
	}
	
	public int oreParziale(List<PowerOutage> parziale) {
		
		int somma = 0;

		for(PowerOutage po:parziale) {
			Long secs = (po.getDate_event_finished().getTime() - po.getDate_event_began().getTime()) / 1000;
			int hours = secs.intValue()/3600;
			somma += hours;
		}
		
		return somma;
	}
	
	public int anniParziale(List<PowerOutage> parziale) {
		
		int massimo = 1899;
		int minimo = 2021;
		
		for(PowerOutage po:parziale) {
			if (po.getYear()>massimo)
				massimo = po.getYear();
			if (po.getYear()<minimo)
				minimo = po.getYear();
		}
				
		return massimo - minimo;
		
	}


}
