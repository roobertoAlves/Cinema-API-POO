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
               String cmd = "INSERT INTO DBCinema.TBClientes ( "
                    + "id_cliente, "
                    + "nome, "
                    + "email, "
                    + "senha, "
                    + "cpf, "
                    + "telefone, "
                    + "perfilFidelidade, "
                    + "pontos) ";
                cmd +=  client.getIdClient() +"', '" +
				 		client.getName()+"', '" +
				 		client.getEmail()+"', '" +
						client.getPassword()+"', '" +
						client.getCpf()+"', '" +
						client.getPhone()+"', '" +
						client.getLoyaltyProfile()+"', '" +
						client.getPoints() +
                    ")'" ;

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
		return 0;
	}

	public int delete( Clients client ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( client.getIdClient() > 0 ) 
            {
                String cmd = "DELETE FROM DBCinema.TBClientes";
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
        String cmd = "SELECT id_cliente, nome, email, senha, cpf, telefone, perfilFidelidade, pontos FROM DBCinema.TBClientes";
        
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
