package Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class RoomsDAO 
{

    private Statement dbLink = null;

	public RoomsDAO() 
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

	public int insert( Rooms rooms ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( rooms.getRoomId() > 0 ) 
            {
				String cmd =  "INSERT INTO DBCinema.TBSalas ( "
                        + "id_sala, "
                        + "numeroSala, "
                        + "capacidadeMaxima, "
                        + "capacidadeAtual, "
                        + "statusSala, "
                        + "tipoSala, "
                        + "poltronas) "
                        + " values ('";
                        
                    cmd +=  rooms.getRoomId() +"', '" + rooms.getRoomId() +
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

	public int update( Rooms rooms ) 
    {
		return 0;
	}

	public int delete( Rooms rooms ) 
    {
		try 
        {
			int linesAfected = 0;

			if (rooms.getRoomId() > 0) 
            {
				String cmd =  " DELETE FROM DBCinema.TBSalas " + " WHERE id_sala = " + rooms.getRoomId();
				
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
        String cmd = "SELECT id_sala, numeroSala, capacidadeMaxima, capacidadeAtual, statusSala, tipoSala, poltronas FROM DBCinema.TBSalas";
        
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
