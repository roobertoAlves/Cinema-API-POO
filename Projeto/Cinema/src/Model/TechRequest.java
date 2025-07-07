package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class TechRequest 
{
   private int idRequest;
    private String description;
    private String status;
    private String openData;
    private int roomId;

    private String fieldsName = "id_chamado, descricao, statusChamado, dataAbertura, sala_id";
    private String fieldKey = "id_chamado";
    private String tableName = "tbchamadostec";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public TechRequest() 
    {
    }

    public TechRequest( int idRequest, String description, String status, String openData, int roomId ) 
    {
        this.idRequest = idRequest;
        this.description = description;
        this.status = status;
        this.openData = openData;
        this.roomId = roomId;
    }
    public TechRequest( String description, String status, String openData ) 
    {
        this.description = description;
        this.status = status;
        this.openData = openData;
    }

    public int save() 
	{
		if ( this.getIdRequest() > 0 ) 
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
		if ( this.getIdRequest() > 0 ) 
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
            "ID: " + this.getIdRequest() + 
            ", Description: " + this.getDescription() + 
            ", Status: " + this.getStatus() + 
            ", Open Date: " + this.getOpenData() + 
            ", Room ID: " + this.getRoomId()
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdRequest() ),
            this.getDescription(),
            this.getStatus(),
            this.getOpenData(),
            String.valueOf( this.getRoomId() )
		};

		return arrayStr;	
	}

    public int getIdRequest() 
    {
        return idRequest;
    }
    public void setIdRequest( int idRequest ) 
    {
        this.idRequest = idRequest;
    }
    public String getDescription() 
    {
        return description;
    }
    public void setDescription( String description ) 
    {
        this.description = description;
    }
    public String getStatus() 
    {
        return status;
    }
    public void setStatus( String status ) 
    {
        this.status = status;
    }
    public String getOpenData() 
    {
        return openData;
    }
    public void setOpenData( String openData ) 
    {
        this.openData = openData;
    }
    public int getRoomId() 
    {
        return roomId;
    }
    public void setRoomId( int roomId ) 
    {
        this.roomId = roomId;
    }
}
