package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class MoviesCommentsDAO 
{
     private Statement dbLink = null;

    public MoviesCommentsDAO() 
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

    public int insert(  MoviesComments comments ) 
    {
        try 
        {
            int linesAfected = 0;

            if ( comments.getIdComementary() > 0 ) 
            {
                String cmd = "INSERT INTO bdcinema.tbcomentariosfilmes (id_comentario, cliente_id," + 
                             " filme_id, comentario, avaliacao, dataComentario) VALUES ("
                            + comments.getIdComementary() + ", "
                            + comments.getClientId() + ", "
                            + comments.getMovieId() + ", '"
                            + comments.getComment() + "', "
                            + comments.getRating() + ", '"
                            + comments.getCommentDate() + "')" ;
                

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

    public int update( MoviesComments comments ) 
    {
        try 
        {			
            int linesAfected = 0;

            if ( comments.getIdComementary() > 0 ) 
            {
                String cmd = "UPDATE bdcinema.tbcomentariosfilmes SET" 
                            + "cliente_id = " + comments.getClientId() + ", "
                            + "filme_id = " + comments.getMovieId() + ", "
                            + "comentario = '" + comments.getComment() + "', "
                            + "avaliacao = " + comments.getRating() + ", "
                            + "dataComentario = '" + comments.getCommentDate() + "' "
                            + "WHERE id_comentario = " + comments.getIdComementary();

                linesAfected = dbLink.executeUpdate( cmd );

                return linesAfected;
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

    public int delete( MoviesComments comments ) 
    {
        try 
        {
            int linesAfected = 0;

            if ( comments.getIdComementary() > 0 ) 
            {
                String cmd = "DELETE FROM bdcinema.tbcomentariosfilmes";
                       cmd += "WHERE id_comentario = " + comments.getIdComementary();

                linesAfected = dbLink.executeUpdate( cmd );

                return linesAfected;

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

    public ResultSet list( String where ) 
    {
        String cmd = "SELECT id_comentario, cliente_id, filme_id, comentario, avaliacao," + 
                     "dataComentario FROM bdcinema.tbcomentariosfilmes ";
        
        if (!where.isEmpty()) 
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
