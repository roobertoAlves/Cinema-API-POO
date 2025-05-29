package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import database.DBConnection;


public class MovieSessionsDAO 
{
    private Statement dbLink = null;

	public MovieSessionsDAO() 
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

	public int insert(  MovieSessions sessions ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( sessions.getIdSession() > 0 ) 
            {
                String cmd = "INSERT INTO DBCinema.TBGeneros ( "
                    + "id_sessao, "
                    + "filme_id, "
                    + "sala_id, "
                    + "horarioInicio, "
                    + "duracaoFilme) ";
                cmd +=  sessions.getIdSession() +"', '" + sessions.getIdSession() +
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

	public int update( MovieSessions sessions ) 
    {
		return 0;
	}

	public int delete( MovieSessions sessions ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( sessions.getIdSession() > 0 ) 
            {
                String cmd = " DELETE FROM DBCinema.TBSessoes " + " WHERE id_sessao = " + sessions.getIdSession();

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
        String cmd = "SELECT id_sessao, filme_id, sala_id, horarioInicio, duracaoFilme FROM DBCinema.TBSessoes";
        
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
