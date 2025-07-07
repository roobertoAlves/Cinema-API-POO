package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import database.DBConnection;

public class MovieSessionsDAO 
{
    private Statement dbLink = null;

    public MovieSessionsDAO() 
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

    public int insert(MovieSessions session) 
    {
        try 
        {
            String cmd = "INSERT INTO bdcinema.tbsessoes (filme_id, sala_id, horarioInicio, "
                    + "duracaoFilme, tipoSessao, precoIngresso) VALUES (" 
                    + session.getMovieId() + ", "
                    + session.getRoomId() + ", '"
                    + session.getStartTime() + "', '"
                    + session.getMovieDuration() + "', '"
                    + session.getSessionType() + "', "
                    + session.getTicketPrice() + ")";

            int linesAffected = dbLink.executeUpdate(cmd);
            return linesAffected;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(MovieSessions session) 
    {
        try 
        {
            String cmd = "UPDATE bdcinema.tbsessoes SET "
                    + "filme_id = " + session.getMovieId() + ", "
                    + "sala_id = " + session.getRoomId() + ", "
                    + "horarioInicio = '" + session.getStartTime() + "', "
                    + "duracaoFilme = '" + session.getMovieDuration() + "', "
                    + "tipoSessao = '" + session.getSessionType() + "', "
                    + "precoIngresso = " + session.getTicketPrice() + " "
                    + "WHERE id_sessao = " + session.getIdSession();

            int linesAffected = dbLink.executeUpdate(cmd);
            return linesAffected;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(MovieSessions session) 
    {
        try 
        {
            if (session.getIdSession() > 0) 
            {
                String cmd = "DELETE FROM bdcinema.tbsessoes WHERE id_sessao = " + session.getIdSession();
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
        String cmd = "SELECT id_sessao, filme_id, sala_id, horarioInicio, duracaoFilme, tipoSessao, precoIngresso " +
                     "FROM bdcinema.tbsessoes";
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
