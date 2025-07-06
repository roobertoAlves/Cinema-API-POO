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
               String cmd = "INSERT INTO bdcinema.tbmarketing (id_campanha, nomeCampanha, inicioCampanha," 
            		   		+ "fimCampanha, impactoCampanha) VALUES ("
            	+ marketing.getIdCampaign() + ", '"
            	+ marketing.getCampaignName() + "', '"
            	+ marketing.getStartCampaign() + "', '"
            	+ marketing.getEndCampaign() + "', '"
            	+ marketing.getCampaignImpact() + "')";
               
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
		try 
		{		
			int linesAfected = 0;

			if ( marketing.getIdCampaign() > 0 ) 
			{
				String cmd = "UPDATE bdcinema.tbmarketing SET" 
						+ "nomeCampanha = '" + marketing.getCampaignName() + "', "
						+ "inicioCampanha = '" + marketing.getStartCampaign() + "', "
						+ "fimCampanha = '" + marketing.getEndCampaign() + "', "
						+ "impactoCampanha = '" + marketing.getCampaignImpact() + "' "
						+ "WHERE id_campanha = " + marketing.getIdCampaign();

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

	public int delete( Marketing marketing ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( marketing.getIdCampaign() > 0 ) 
            {
                String cmd = "DELETE FROM bdcinema.tbmarketing";
					   cmd += "WHERE id_campanha = " + marketing.getIdCampaign();

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
        String cmd = "SELECT id_campanha, nomeCampanha, inicioCampanha, fimCampanha, impactoCampanha" 
        			 + "FROM bdcinema.tbmarketing";
        
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
