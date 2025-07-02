package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class ReservationsDAO 
{
    private Statement dbLink = null;

	public ReservationsDAO() 
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

	public int insert(  Reservations reservations ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( reservations.getIdReservation() > 0 ) 
            {
                String cmd = "INSERT INTO DBCinema.TBReservas ( "
                    + "id_reserva, "
                    + "cliente_id, "
                    + "sessao_id, "
                    + "poltrona, "
                    + "dataReserva) ";
                cmd +=  reservations.getIdReservation() +"', '" + 
						reservations.getClientId() +"', '" +
						reservations.getSessionId() +"', '" +
						reservations.getSeatNumber() +"', '" +
						reservations.getReservationDate() +
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

	public int update( Reservations reservations ) 
    {
		return 0;
	}

	public int delete( Reservations reservations ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( reservations.getIdReservation() > 0 ) 
            {
                String cmd = "DELETE FROM DBCinema.TBReservas"; 
					   cmd += "WHERE id_reserva = " + reservations.getIdReservation();

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
        String cmd = "SELECT id_reserva, cliente_id, sessao_id, poltrona, dataReserva FROM DBCinema.TBReservas";
 
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
