package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class MovieTicketsDAO 
{
    private Statement dbLink = null;

	public MovieTicketsDAO() 
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

	public int insert(  MovieTickets tickets ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( tickets.getIdTicket() > 0 ) 
            {
                String cmd = "INSERT INTO DBCinema.TBIIngressos ( "
                    + "id_ingresso, "
                    + "sessao_id, "
                    + "cliente_id, "
                    + "poltrona, "
                    + "valor, "
                    + "tipoIngresso) ";
                cmd +=  tickets.getIdTicket() +"', '" + tickets.getIdTicket() +
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

	public int update( MovieTickets tickets ) 
    {
		return 0;
	}

	public int delete( MovieTickets tickets ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( tickets.getIdTicket() > 0 ) 
            {
                String cmd = "DELETE FROM DBCinema.TBIngressos WHERE id_ingresso = " + tickets.getIdTicket();

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
        String cmd = "SELECT id_ingresso, sessao_id, cliente_id, poltrona, valor, tipoIngresso FROM DBCinema.TBIngressos";
 
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

