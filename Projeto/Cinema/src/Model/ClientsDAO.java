package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import database.DBConnection;


public class ClientsDAO 
{
    private Statement dbLink = null;

	public ClientsDAO() 
    {
		try 
        {
			this.dbLink = new DBConnection().getConnection().createStatement();
		} 
        catch ( SQLException e ) 
        {
			e.printStackTrace();
		}

	}

	public int insert(  Clients client ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( client.getIdClient() > 0 ) 
            {
               String cmd = "INSERT INTO bdcinema.tbclientes (id_cliente, nome, email, senha," + 
            		   		"cpf, telefone, perfilFidelidade, pontos) VALUES ("
               			+client.getIdClient() + "', '"
				 		+client.getName() + "', '" 
				 		+client.getEmail() + "', '" 
						+client.getPassword() + "', '" 
						+client.getCpf() + "', '" 
						+client.getPhone() + "', '" 
						+client.getLoyaltyProfile() + "', '" 
						+client.getPoints() + "')";

				linesAfected = dbLink.executeUpdate( cmd );

				return linesAfected;
			}
            else
            {
				return 0;
			}
		} 
        catch ( SQLException e ) 
        {
			e.printStackTrace();
			return 0;
		}		
	}

	public int update( Clients client ) 
    {
		try 
		{
			int linesAfected = 0;

			if ( client.getIdClient() > 0 ) 
			{
				String cmd = "UPDATE bdcinema.tbclientes SET" 
						+ "nome = '" + client.getName() + "', "
				 		+ "email = '" + client.getEmail() + "', "
				 		+ "senha = '" + client.getPassword() + "', "
						+ "cpf = '" + client.getCpf() + "', "
						+ "telefone = '" + client.getPhone() + "', "
						+ "perfilFidelidade = '" + client.getLoyaltyProfile() + "', "
						+ "pontos = '" + client.getPoints() + "' "
						+ "WHERE id_cliente = " + client.getIdClient();

				linesAfected = dbLink.executeUpdate( cmd );

				return linesAfected;
			}
			else
			{
				return 0;
			}
		} 
		catch ( SQLException e ) 
		{
			e.printStackTrace();
			return 0;
		}
	}

	public int delete( Clients client ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( client.getIdClient() > 0 ) 
            {
                String cmd = "DELETE FROM bdcinema.tbclientes";
					   cmd += "WHERE id_cliente = " + client.getIdClient();

				linesAfected = dbLink.executeUpdate( cmd );

				return linesAfected;

			}
            else
            {
				return 0;
			}
		} 
        catch ( SQLException e ) 
        {
			e.printStackTrace();
			return 0;
		}		
	}

	public ResultSet list( String where ) 
    {
        String cmd = "SELECT id_cliente, nome, email, senha, cpf, telefone," + 
        			 "perfilFidelidade, pontos FROM bdcinema.tbclientes";
        
        if ( !where.isEmpty() ) 
        {
			cmd += " WHERE " + where;
		}

		ResultSet rs = null;

		try 
        {
			rs = dbLink.executeQuery( cmd );
		} 
        catch ( SQLException e ) 
        {
			e.printStackTrace();
		}
        
		return rs;
	} 
}
