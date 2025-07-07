package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class Reservations 
{

    private int idReservation;
    private int clientId;
    private int sessionId;
    private int seatNumber;
    private String reservationDate;

    private String tableName = "tbreservas";
    private String fieldsName = "id_reserva, cliente_id, sessao_id, poltrona, dataReserva";
    private String fieldKey = "id_reserva";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public Reservations() 
    {
    }
    public Reservations( int idReservation, int clientId, int sessionId, int seatNumber, String reservationDate ) 
    {
        this.idReservation = idReservation;
        this.clientId = clientId;
        this.sessionId = sessionId;
        this.seatNumber = seatNumber;
        this.reservationDate = reservationDate;
    }
    public Reservations( int seatNumber, String reservationDate) 
    {
        this.seatNumber = seatNumber;
        this.reservationDate = reservationDate;
    }

    public int save() 
	{
		if ( this.getIdReservation() > 0) 
        {
            return ( dbQuery.update(this.toArray()) );
        } 
        else 
        {
            return ( dbQuery.insert(this.toArray()) );
        }
	}

	public int delete() 
	{
		if ( this.getIdReservation() > 0 ) 
        {
            return ( dbQuery.delete(this.toArray()) );
        }

		return (0);
	}
	
	public ResultSet listAll() 
	{
		return ( dbQuery.select("") );
	}



    public String toString() 
	{
		return(
            "Reservation ID: " + this.getIdReservation() + 
            ", Client ID: " + this.getClientId() + 
            ", Session ID: " + this.getSessionId() + 
            ", Seat Number: " + this.getSeatNumber() + 
            ", Reservation Date: " + this.getReservationDate()
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdReservation() ),
            String.valueOf( this.getClientId() ),
            String.valueOf( this.getSessionId() ),
            String.valueOf( this.getSeatNumber() ),
            this.getReservationDate()
		};

		return arrayStr;	
	}



    public int getIdReservation() 
    {
        return idReservation;
    }
    public void setIdReservation( int idReservation ) 
    {
        this.idReservation = idReservation;
    }
    public int getClientId() 
    {
        return clientId;
    }
    public void setClientId( int clientId ) 
    {
        this.clientId = clientId;
    }
    public int getSessionId() 
    {
        return sessionId;
    }
    public void setSessionId( int sessionId ) 
    {
        this.sessionId = sessionId;
    }
    public int getSeatNumber() 
    {
        return seatNumber;
    }
    public void setSeatNumber( int seatNumber ) 
    {
        this.seatNumber = seatNumber;
    }
    public String getReservationDate() 
    {
        return reservationDate;
    }
    public void setReservationDate( String reservationDate ) 
    {
        this.reservationDate = reservationDate;
    }
}
