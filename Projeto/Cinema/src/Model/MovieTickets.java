package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class MovieTickets 
{
    
    private int idTicket;
    private int sessionId;
    private int clientId;
    private int seatNumber;
    private double price;
    private String ticketType;

    private String tableName = "tbingressos";
    private String fieldsName = "id_ingresso, sessao_id, cliente_id, poltrona, valor, tipoIngresso";
    private String fieldKey = "id_ingresso";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public MovieTickets() 
    {
        // Default constructor
    }
    public MovieTickets( int idTicket, int sessionId, int clientId, int seatNumber, double price, String ticketType ) 
    {
        this.idTicket = idTicket;
        this.sessionId = sessionId;
        this.clientId = clientId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.ticketType = ticketType;
    }
    public MovieTickets( int clientId, int seatNumber, double price, String ticketType ) 
    {
        this.clientId = clientId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.ticketType = ticketType;
    }

    public int save() 
	{
		if ( this.getIdTicket() > 0 ) 
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
		if ( this.getIdTicket() > 0 ) 
        {
            return ( dbQuery.delete(this.toArray()) );
        }

		return (0);
	}
	
	public ResultSet listAll() 
	{
		return ( dbQuery.select("") );
	}

    // To string and to Array methods

    public String toString() 
	{
		return(
            "Ticket ID: " + this.getIdTicket() + 
            ", Session ID: " + this.getSessionId() + 
            ", Client ID: " + this.getClientId() + 
            ", Seat Number: " + this.getSeatNumber() + 
            ", Price: " + this.getPrice() + 
            ", Ticket Type: " + this.getTicketType()
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdTicket() ),
            String.valueOf( this.getSessionId() ),
            String.valueOf( this.getClientId() ),
            String.valueOf( this.getSeatNumber() ),
            String.valueOf( this.getPrice() ),
            this.getTicketType()
		};

		return arrayStr;	
	}

    // Getters and Setters

    public int getIdTicket() 
    {
        return idTicket;
    }
    public void setIdTicket( int idTicket ) 
    {
        this.idTicket = idTicket;
    }
    public int getSessionId() 
    {
        return sessionId;
    }
    public void setSessionId( int sessionId ) 
    {
        this.sessionId = sessionId;
    }
    public int getClientId() 
    {
        return clientId;
    }
    public void setClientId( int clientId )  
    {
        this.clientId = clientId;
    }
    public int getSeatNumber() 
    {
        return seatNumber;
    }
    public void setSeatNumber( int seatNumber ) 
    {
        this.seatNumber = seatNumber;
    }
    public double getPrice() 
    {
        return price;
    }
    public void setPrice( double price ) 
    {
        this.price = price;
    }
    public String getTicketType() 
    {
        return ticketType;
    }
    public void setTicketType( String ticketType ) 
    {
        this.ticketType = ticketType;
    }

}
