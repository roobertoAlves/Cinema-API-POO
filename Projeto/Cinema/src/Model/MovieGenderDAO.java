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
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public int insert(MovieGender gender) 
    {
        try 
        {
        	String cmd = "INSERT INTO bdcinema.tbgeneros (nomeGenero, descricaoGenero) VALUES ('"
        		    + gender.getGenderName() + "', '"
        		    + gender.getGenderDescription() + "')";

            	
            int linesAffected = dbLink.executeUpdate(cmd);
            return linesAffected;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(MovieGender gender) 
    {
        try 
        {
        	String cmd = "UPDATE bdcinema.tbgeneros SET "
        		    + "nomeGenero = '" + gender.getGenderName() + "', "
        		    + "descricaoGenero = '" + gender.getGenderDescription() + "' "
        		    + "WHERE id_genero = " + gender.getIdGender();


            int linesAffected = dbLink.executeUpdate(cmd);
            return linesAffected;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(MovieGender gender) 
    {
        try 
        {
            if (gender.getIdGender() > 0) 
            {
                String cmd = "DELETE FROM bdcinema.tbgeneros WHERE id_genero = " + gender.getIdGender();
                int linesAffected = dbLink.executeUpdate(cmd);
                return linesAffected;
            } 
            else 
            {
                return 0;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet list(String where) 
    {
        String cmd = "SELECT id_genero, nomeGenero, descricaoGenero FROM bdcinema.tbgeneros";
        if (!where.isEmpty()) 
        {
            cmd += " WHERE " + where;
        }

        try 
        {
            return dbLink.executeQuery(cmd);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
}
