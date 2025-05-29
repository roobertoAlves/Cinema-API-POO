package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class Rooms 
{
    private int roomId;
    private int roomNumber;
    private int roomMaxCapacity;
    private int roomCurrentCapacity;
    private String roomStatus;
    private String roomType;
    private int seats;

    private String  tableName 	= "TBSalas";
    private String  fieldsName 	= "id_sala, numeroSala, capacidadeMaxima, capacidadeAtual, statusSala, tipoSala, poltronas";
    private String  fieldKey  	= "id_sala";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public Rooms() 
    {

    }

    public Rooms( int roomId, int roomNumber, int roomMaxCapacity, int roomCurrentCapacity, String roomStatus, String roomType, int seats ) 
    {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomMaxCapacity = roomMaxCapacity;
        this.roomCurrentCapacity = roomCurrentCapacity;
        this.roomStatus = roomStatus;
        this.roomType = roomType;
        this.seats = seats;
    }
    public Rooms( int roomNumber, int roomMaxCapacity, int roomCurrentCapacity, String roomStatus, String roomType, int seats ) 
    {
        this.roomNumber = roomNumber;
        this.roomMaxCapacity = roomMaxCapacity;
        this.roomCurrentCapacity = roomCurrentCapacity;
        this.roomStatus = roomStatus;
        this.roomType = roomType;
        this.seats = seats;
    }

    public int save() 
	{
		if ( this.getRoomId() > 0 ) 
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
		if ( this.getRoomId() > 0 ) 
        {
            return ( dbQuery.delete(this.toArray()) );
        }

		return (0);
	}
	
	public ResultSet listByEmail( String email ) 
	{
		return ( dbQuery.select("email = '" + email + "'") );
	}
	
	public ResultSet listAll() 
	{
		return ( dbQuery.select("") );
	}

	// TO STRING & TO ARRAY

	public String toString() 
	{
		return(
            "Room ID: " + this.getRoomId() + 
            ", Room Number: " + this.getRoomNumber() + 
            ", Max Capacity: " + this.getRoomMaxCapacity() + 
            ", Current Capacity: " + this.getRoomCurrentCapacity() + 
            ", Status: " + this.getRoomStatus() + 
            ", Type: " + this.getRoomType() + 
            ", Seats: " + this.getSeats()
		);
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getRoomId() ),
            String.valueOf( this.getRoomNumber() ),
            String.valueOf( this.getRoomMaxCapacity() ),
            String.valueOf( this.getRoomCurrentCapacity() ),
            this.getRoomStatus(),
            this.getRoomType(),
            String.valueOf( this.getSeats() )
		};

		return arrayStr;	
	}

    // Getters and Setters

    public int getRoomId() 
    {
        return roomId;
    }

    public void setRoomId( int roomId ) 
    {
        this.roomId = roomId;
    }
    public int getRoomNumber() 
    {
        return roomNumber;
    }
    public void setRoomNumber( int roomNumber ) 
    {
        this.roomNumber = roomNumber;
    }
    public int getRoomMaxCapacity() 
    {
        return roomMaxCapacity;
    }
    public void setRoomMaxCapacity( int roomMaxCapacity )  
    {
        this.roomMaxCapacity = roomMaxCapacity;
    }
    public int getRoomCurrentCapacity() 
    {
        return roomCurrentCapacity;
    }
    public void setRoomCurrentCapacity( int roomCurrentCapacity ) 
    {
        this.roomCurrentCapacity = roomCurrentCapacity;
    }
    public String getRoomStatus() 
    {
        return roomStatus;
    }
    public void setRoomStatus( String roomStatus ) 
    {
        this.roomStatus = roomStatus;
    }
    public String getRoomType() 
    {
        return roomType;
    }
    public void setRoomType( String roomType ) 
    {
        this.roomType = roomType;
    }
    public int getSeats() 
    {
        return seats;
    }
    public void setSeats( int seats ) 
    {
        this.seats = seats;
    }

}
