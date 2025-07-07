package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class Maintenance 
{
    private int idMaintenance;
    private int roomId;
    private String description;
    private String startDate;
    private String endDate;
    private int employeeId;

    private String tableName = "tbmanutencao";
    private String fieldsName = "id_manutencao, sala_id, descricao, dataInicio, dataFim, funcionario_id";
    private String fieldKey = "id_manutencao";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public Maintenance() 
    {

    }
    public Maintenance( int idMaintenance, int roomId, String description, String startDate, String endDate, int employeeId ) 
    {
        this.idMaintenance = idMaintenance;
        this.roomId = roomId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employeeId = employeeId;
    }
    public Maintenance( String description, String startDate, String endDate ) 
    {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public int save() 
	{
		if ( this.getIdMaintenance() > 0 ) 
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
		if ( this.getIdMaintenance() > 0 ) 
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
            "Maintenance ID: " + this.getIdMaintenance() + 
            ", Room ID: " + this.getRoomId() + 
            ", Description: " + this.getDescription() + 
            ", Start Date: " + this.getStartDate() + 
            ", End Date: " + this.getEndDate() + 
            ", Employee ID: " + this.getEmployeeId()
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdMaintenance() ),
            String.valueOf( this.getRoomId() ),
            this.getDescription(),
            this.getStartDate(),
            this.getEndDate(),
            String.valueOf( this.getEmployeeId() )
		};

		return arrayStr;	
	}



    public int getIdMaintenance() 
    {
        return idMaintenance;
    }
    public void setIdMaintenance( int idMaintenance ) 
    {
        this.idMaintenance = idMaintenance;
    }
    public int getRoomId() 
    {
        return roomId;
    }
    public void setRoomId( int roomId ) 
    {
        this.roomId = roomId;
    }
    public String getDescription() 
    {
        return description;
    }
    public void setDescription( String description ) 
    {
        this.description = description;
    }
    public String getStartDate() 
    {
        return startDate;
    }
    public void setStartDate( String startDate ) 
    {
        this.startDate = startDate;
    }
    public String getEndDate() 
    {
        return endDate;
    }
    public void setEndDate( String endDate ) 
    {
        this.endDate = endDate;
    }
    public int getEmployeeId() 
    {
        return employeeId;
    }
    public void setEmployeeId( int employeeId )  
    {
        this.employeeId = employeeId;
    }
}
