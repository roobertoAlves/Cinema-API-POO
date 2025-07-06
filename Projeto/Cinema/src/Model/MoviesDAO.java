package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import database.DBConnection;

public class MoviesDAO 
{
    private Statement dbLink = null;

    public MoviesDAO() 
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

    public int insert(Movies movie) 
    {
        try 
        {
            String cmd = "INSERT INTO bdcinema.tbfilmes (titulo, classificacao, duracao, tipoFilme, " +
            			 "idiomaFilme, dublagem,legenda, distribuidor) VALUES ('"
                    + movie.getMovieTitle() + "', '"
                    + movie.getMovieRating() + "', '"
                    + movie.getMovieDuration() + "', '"
                    + movie.getMovieType() + "', '"
                    + movie.getMovieLanguage() + "', '"
                    + movie.getMovieDubbing() + "', '"
                    + movie.getMovieSubtitle() + "', '"
                    + movie.getMovieDistributor() + "')";

            int linesAffected = dbLink.executeUpdate(cmd);
            return linesAffected;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }       
    }

    public int update(Movies movie) 
    {
        try 
        {
            String cmd = "UPDATE bdcinema.tbfilmes SET "
                    + "titulo = '" + movie.getMovieTitle() + "', "
                    + "classificacao = '" + movie.getMovieRating() + "', "
                    + "duracao = '" + movie.getMovieDuration() + "', "
                    + "tipoFilme = '" + movie.getMovieType() + "', "
                    + "idiomaFilme = '" + movie.getMovieLanguage() + "', "
                    + "dublagem = '" + movie.getMovieDubbing() + "', "
                    + "legenda = '" + movie.getMovieSubtitle() + "', "
                    + "distribuidor = '" + movie.getMovieDistributor() + "' "
                    + "WHERE id_filme = " + movie.getMovieId();

            int linesAffected = dbLink.executeUpdate(cmd);
            return linesAffected;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(Movies movie) 
    {
        try 
        {
            if (movie.getMovieId() > 0) 
            {
                String cmd = "DELETE FROM bdcinema.tbfilmes WHERE id_filme = " + movie.getMovieId();
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
        String cmd = "SELECT id_filme, titulo, classificacao, duracao, tipoFilme, idiomaFilme, dublagem," + 
        			 "legenda, distribuidor FROM bdcinema.tbfilmes";
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
