package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class MoviesComments 
{

    private int idComementary;
    private int clientId;
    private int movieId;
    private String comment;
    private int rating;
    private String commentDate;

    private String tableName = "TBComentariosFilmes";
    private String fieldsName = "id_comentario, id_cliente, id_filme, comentario, avaliacao, dataComentario";
    private String fieldKey = "id_comentario";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );
    public int save() 
	{
		if ( this.getIdComementary() > 0)  
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
		if ( this.getIdComementary() > 0 ) 
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
            "MoviesComentaries [idComementary=" + idComementary + 
            ", clientId=" + clientId + 
            ", movieId=" + movieId + 
            ", comment=" + comment + 
            ", rating=" + rating + 
            ", commentDate=" + commentDate + "]"
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdComementary() ),
            String.valueOf( this.getClientId() ),
            String.valueOf( this.getMovieId() ),
            this.getComment(),
            String.valueOf( this.getRating() ),
            this.getCommentDate()
		};

		return arrayStr;	
	}

    // Getters and Setters

    public int getIdComementary() 
    {
        return idComementary;
    }
    public void setIdComementary( int idComementary ) 
    {
        this.idComementary = idComementary;
    }
    public int getClientId() 
    {
        return clientId;
    }
    public void setClientId( int clientId ) 
    {
        this.clientId = clientId;
    }
    public int getMovieId() 
    {
        return movieId;
    }
    public void setMovieId( int movieId ) 
    {
        this.movieId = movieId;
    }
    public String getComment() 
    {
        return comment;
    }
    public void setComment( String comment ) 
    {
        this.comment = comment;
    }
    public int getRating() 
    {
        return rating;
    }
    public void setRating( int rating ) 
    {
        this.rating = rating;
    }
    public String getCommentDate() 
    {
        return commentDate;
    }
    public void setCommentDate( String commentDate ) 
    {
        this.commentDate = commentDate;
    }
}
