package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class Movies 
{
    private int movieId;
    private String movieTitle;
    private String movieRating;
    private String movieDuration;
    private String movieType;
    private String movieLanguage;
    private String movieDubbing;
    private String movieSubtitle;
    private String movieDistributor;

    private String tableName = "tbfilmes";
    private String fieldsName = "id_filme, titulo, classificacao, duracao, tipoFilme, idiomaFilme, dublagem, legenda, distribuidor";
    private String fieldKey = "id_filme";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public Movies() 
    {

    }
    public Movies( int movieId, String movieTitle, String movieRating, String movieDuration, String movieType, 
                    String movieLanguage, String movieDubbing, String movieSubtitle, String movieDistributor ) 
    {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.movieDuration = movieDuration;
        this.movieType = movieType;
        this.movieLanguage = movieLanguage;
        this.movieDubbing = movieDubbing;
        this.movieSubtitle = movieSubtitle;
        this.movieDistributor = movieDistributor;
    }
    public Movies( String movieTitle, String movieRating, String movieDuration, String movieType, String movieLanguage, 
                    String movieDubbing, String movieSubtitle, String movieDistributor ) 
    {
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.movieDuration = movieDuration;
        this.movieType = movieType;
        this.movieLanguage = movieLanguage;
        this.movieDubbing = movieDubbing;
        this.movieSubtitle = movieSubtitle;
        this.movieDistributor = movieDistributor;
    }

    public int save() 
	{
		if ( this.getMovieId() > 0 ) 
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
		if ( this.getMovieId() > 0 ) 
        {
            return ( dbQuery.delete(this.toArray()) );
        }

		return (0);
	}
	
	public ResultSet listByEmail( String email ) 
	{
		return ( dbQuery.select("email = '" + email + "'") );
	}
	
	public ResultSet listAll() 
	{
		return ( dbQuery.select("") );
	}


public String toString() 
	{
		return(
            "Movie ID: " + this.getMovieId() + "\n" +
            "Title: " + this.getMovieTitle() + "\n" +
            "Rating: " + this.getMovieRating() + "\n" +
            "Duration: " + this.getMovieDuration() + "\n" +
            "Type: " + this.getMovieType() + "\n"
		);
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf(this.getMovieId()),
            this.getMovieTitle(),
            this.getMovieRating(),
            this.getMovieDuration(),
            this.getMovieType(),
            this.getMovieLanguage(),
            this.getMovieDubbing(),
            this.getMovieSubtitle(),
            this.getMovieDistributor()
		};

		return arrayStr;	
	}



    public int getMovieId() 
    {
        return movieId;
    }
    public void setMovieId( int movieId ) 
    {
        this.movieId = movieId;
    }
    public String getMovieTitle() 
    {
        return movieTitle;
    }
    public void setMovieTitle( String movieTitle ) 
    {
        this.movieTitle = movieTitle;
    }
    public String getMovieRating() 
    {
        return movieRating;
    }
    public void setMovieRating( String movieRating ) 
    {
        this.movieRating = movieRating;
    }
    public String getMovieDuration() 
    {
        return movieDuration;
    }
    public void setMovieDuration( String movieDuration ) 
    {
        this.movieDuration = movieDuration;
    }
    public String getMovieType() 
    {
        return movieType;
    }
    public void setMovieType( String movieType ) 
    {
        this.movieType = movieType;
    }
    public String getMovieLanguage() 
    {
        return movieLanguage;
    }
    public void setMovieLanguage( String movieLanguage ) 
    {
        this.movieLanguage = movieLanguage;
    }
    public String getMovieDubbing() 
    {
        return movieDubbing;
    }
    public void setMovieDubbing( String movieDubbing ) 
    {
        this.movieDubbing = movieDubbing;
    }
    public String getMovieSubtitle() 
    {
        return movieSubtitle;
    }
    public void setMovieSubtitle( String movieSubtitle ) 
    {
        this.movieSubtitle = movieSubtitle;
    }
    public String getMovieDistributor() 
    {
        return movieDistributor;
    }
    public void setMovieDistributor( String movieDistributor ) 
    {
        this.movieDistributor = movieDistributor;
    }
    
}
