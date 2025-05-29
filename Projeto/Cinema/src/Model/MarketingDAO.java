package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class MarketingDAO 
{
     private Statement dbLink = null;

	public MarketingDAO() 
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

	public int insert(  Marketing marketing ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( marketing.getIdCampaign() > 0 ) 
            {
               String cmd = "INSERT INTO DBCinema.TBMarketing ( "
                    + "id_campanha, "
                    + "nomeCampanha, "
                    + "dataInicioCampanha, "
                    + "dataFimCampanha, "
                    + "impactoCampanha) ";
                cmd +=  marketing.getIdCampaign() +"', '" + marketing.getIdCampaign() +
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

	public int update( Marketing marketing ) 
    {
		return 0;
	}

	public int delete( Marketing marketing ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( marketing.getIdCampaign() > 0 ) 
            {
                String cmd = "DELETE FROM DBCinema.TBMarketing WHERE id_campanha = " + marketing.getIdCampaign();

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
        String cmd = "SELECT id_campanha, nomeCampanha, dataInicioCampanha, dataFimCampanha, impactoCampanha FROM DBCinema.TBMarketing";
        
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
