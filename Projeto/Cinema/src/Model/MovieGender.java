package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class MovieGender 
{
    private int idGender;
    private String genderName;
    private String genderDescription;   
    
    private String tableName = "tbgeneros";
    private String fieldsName = "id_genero, nomeGenero, descricaoGenero";
    private String fieldKey = "id_genero";
    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public MovieGender() 
    {

    }
    public MovieGender( int idGender, String genderName, String genderDescription ) 
    {
        this.idGender = idGender;
        this.genderName = genderName;
        this.genderDescription = genderDescription;
    }

    public MovieGender( String genderName, String genderDescription )
    {
        this.genderName = genderName;
        this.genderDescription = genderDescription; 
    }

    public int save() 
	{
		if ( this.getIdGender() > 0 ) 
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
		if ( this.getIdGender() > 0 )  
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
            "ID: " + this.getIdGender() + 
            ", Name: " + this.getGenderName() + 
            ", Description: " + this.getGenderDescription()
		);
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf(this.getIdGender()),
            this.getGenderName(),
            this.getGenderDescription()
		};

		return arrayStr;	
	}

    public int getIdGender() 
    {
        return idGender;
    }
    public void setIdGender( int idGender ) 
    {
        this.idGender = idGender;
    }
    public String getGenderName() 
    {
        return genderName;
    }
    public void setGenderName( String genderName ) 
    {
        this.genderName = genderName;
    }
    public String getGenderDescription() 
    {
        return genderDescription;
    }
    public void setGenderDescription( String genderDescription ) 
    {
        this.genderDescription = genderDescription;
    }
}
