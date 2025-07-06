package View;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class Teste 
{
	public static void main(String[] args) 
	{
		Teste teste = new Teste();
		
		 DBConnection connection = new DBConnection();
		
		 try {
			Statement dbLink = connection.getConnection().createStatement();
			String cmd = "SELECT * FROM tbclientes;";
			int rs = dbLink.executeUpdate(cmd);
			ResultSet rsI = dbLink.executeQuery(cmd);
			
			while (rsI.next()) {
				System.out.println(rsI.getString("nome"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

}
