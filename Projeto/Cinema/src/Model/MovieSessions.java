package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class MovieSessions 
{
    private int idSession;
    private int movieId;
    private int roomId;
    private String startTime;
    private String movieDuration;
    private String sessionType;
    private double ticketPrice;

    private String tableName = "tbsessoes";
    private String fieldsName = "id_sessao, filme_id, sala_id, horarioInicio, duracaoFilme, tipoSessao, precoIngresso";
    private String fieldKey = "id_sessao";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public MovieSessions() 
    {

    }
    
    public MovieSessions( int idSession, int movieId, int roomId, String startTime, String movieDuration, String sessionType, double ticketPrice ) 
    {
        this.idSession = idSession;
        this.movieId = movieId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.movieDuration = movieDuration;
        this.sessionType = sessionType;
        this.ticketPrice = ticketPrice;
    }
    
    public MovieSessions( int movieId, int roomId, String startTime, String movieDuration, String sessionType, double ticketPrice ) 
    {
        this.movieId = movieId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.movieDuration = movieDuration;
        this.sessionType = sessionType;
        this.ticketPrice = ticketPrice;
    }

    public int save() 
    {
        if ( this.getIdSession() > 0 ) 
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
        if ( this.getIdSession() > 0 ) 
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
            "ID: " + this.getIdSession() + 
            ", Movie ID: " + this.getMovieId() + 
            ", Room ID: " + this.getRoomId() + 
            ", Start Time: " + this.getStartTime() + 
            ", Duration: " + this.getMovieDuration() +
            ", Type: " + this.getSessionType() +
            ", Price: " + this.getTicketPrice()
        );
    }

    public String[] toArray() 
    {
        String[] arrayStr = 
        {
            String.valueOf( this.getIdSession() ),
            String.valueOf( this.getMovieId() ),
            String.valueOf( this.getRoomId() ),
            this.getStartTime(),
            this.getMovieDuration(),
            this.getSessionType(),
            String.valueOf( this.getTicketPrice() )
        };

        return arrayStr;	
    }

    public int getIdSession() 
    {
        return idSession;
    }
    public void setIdSession( int idSession ) 
    {
        this.idSession = idSession;
    }
    public int getMovieId() 
    {
        return movieId;
    }
    public void setMovieId( int movieId ) 
    {
        this.movieId = movieId;
    }
    public int getRoomId() 
    {
        return roomId;
    }
    public void setRoomId( int roomId ) 
    {
        this.roomId = roomId;
    }
    public String getStartTime() 
    {
        return startTime;
    }
    public void setStartTime( String startTime ) 
    {
        this.startTime = startTime;
    }
    public String getMovieDuration() 
    {
        return movieDuration;
    }
    public void setMovieDuration( String movieDuration ) 
    {
        this.movieDuration = movieDuration;
    }
    public String getSessionType() 
    {
        return sessionType;
    }
    public void setSessionType( String sessionType ) 
    {
        this.sessionType = sessionType;
    }
    public double getTicketPrice() 
    {
        return ticketPrice;
    }
    public void setTicketPrice( double ticketPrice ) 
    {
        this.ticketPrice = ticketPrice;
    }
}
