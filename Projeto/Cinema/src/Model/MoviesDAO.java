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
        catch ( SQLException e ) 
        {
			e.printStackTrace();
		}

	}

	public int insert( Movies movie ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( movie.getMovieId() > 0 ) 
            {
                String cmd =  "INSERT INTO DBCinema.TBFilmes ( "
                        + "id_filme, "
                        + "titulo, "
                        + "classificacao, "
                        + "duracao, "
                        + "tipoFilme, "
                        + "idiomaFilme, "
                        + "dublagem, "
                        + "legenda, "
                        + "distribuidor) "
                        + " values ('";

					cmd +=  movie.getMovieId() +"', '" +
						movie.getMovieTitle() +"', '" +
						movie.getMovieRating() +"', '" +
						movie.getMovieDuration() +"', '" +
						movie.getMovieType() +"', '" +
						movie.getMovieLanguage() +"', '" +
						movie.getMovieDubbing() +"', '" +
						movie.getMovieSubtitle() +"', '" +
						movie.getMovieDistributor() +
						
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

	public int update( Movies movie ) 
    {
		return 0;
	}

	public int delete( Movies movie ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( movie.getMovieId() > 0 ) 
            {
				String cmd =  " DELETE FROM DBCinema.TBFilmes ";
					   cmd += " WHERE id_filme = " + movie.getMovieId();
				
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
		String cmd = "SELECT id_filme, titulo, classificacao, duracao, tipoFilme, idiomaFilme, dublagem, legenda, distribuidor FROM DBCinema.TBFilmes";
        
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