package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class MovieGenderDAO 
{
   private Statement dbLink = null;

	public MovieGenderDAO() 
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

	public int insert(  MovieGender gender ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( gender.getIdGender() > 0 ) 
            {
                String cmd =  "INSERT INTO DBCinema.TBGeneros ( "
                        + "id_genero, "
                        + "nomeGenero, "
                        + "descricaoGenero) "
                        + " values ('";
                    cmd +=  gender.getIdGender() +"', '" + gender.getIdGender() +
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

	public int update( MovieGender gender ) 
    {
		return 0;
	}

	public int delete( MovieGender gender ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( gender.getIdGender() > 0 ) 
            {
                String cmd = " DELETE FROM DBCinema.TBGeneros " + " WHERE id_genero = " + gender.getIdGender();

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
        String cmd = "SELECT id_genero, nomeGenero, descricaoGenero FROM DBCinema.TBGeneros"; 
        
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
