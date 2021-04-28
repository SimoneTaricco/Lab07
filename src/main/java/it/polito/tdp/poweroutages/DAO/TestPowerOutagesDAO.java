package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		
		/*try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO() ;
			
			for(Nerc n:dao.getNercList())
				System.out.println(n.getId());

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}*/
		
		PowerOutageDAO dao = new PowerOutageDAO();
		List<Nerc> listaProva = dao.getNercList();

		List<PowerOutage> listaProvaIdSpecifico = dao.getSpecificIdNercList(listaProva.get(3));
		for(PowerOutage p:listaProvaIdSpecifico)
			System.out.println(p);

		
	}		

}
