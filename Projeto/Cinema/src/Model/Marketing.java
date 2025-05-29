package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class Marketing 
{
    private int idCampaign;
    private String campaignName;
    private String startCampaign;
    private String endCampaign;
    private int campaignImpact;

    private String tableName = "TBMarketing";
    private String fieldsName = "id_campanha, nomeCampanha, dataInicioCampanha, dataFimCampanha, impactoCampanha";
    private String fieldKey = "id_campanha";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public Marketing() 
    {
        // Default constructor
    }
    public Marketing( int idCampaign, String campaignName, String startCampaign, String endCampaign, int campaignImpact ) 
    {
        this.idCampaign = idCampaign;
        this.campaignName = campaignName;
        this.startCampaign = startCampaign;
        this.endCampaign = endCampaign;
        this.campaignImpact = campaignImpact;
    }
    public Marketing( String campaignName, String startCampaign, String endCampaign, int campaignImpact ) 
    {
        this.campaignName = campaignName;
        this.startCampaign = startCampaign;
        this.endCampaign = endCampaign;
        this.campaignImpact = campaignImpact;
    }
    public int save() 
	{
		if ( this.getIdCampaign() > 0 ) 
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
		if ( this.getIdCampaign() > 0 ) 
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
            "Campaign ID: " + this.getIdCampaign() + 
            ", Name: " + this.getCampaignName() + 
            ", Start Date: " + this.getStartCampaign() + 
            ", End Date: " + this.getEndCampaign() + 
            ", Impact: " + this.getCampaignImpact()
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdCampaign() ),
            this.getCampaignName(),
            this.getStartCampaign(),
            this.getEndCampaign(),
            String.valueOf( this.getCampaignImpact() )
		};

		return arrayStr;	
	}

    // Getters and Setters

    public int getIdCampaign() 
    {
        return idCampaign;
    }
    public void setIdCampaign( int idCampaign ) 
    {
        this.idCampaign = idCampaign;
    }
    public String getCampaignName() 
    {
        return campaignName;
    }   
    public void setCampaignName( String campaignName )  
    {
        this.campaignName = campaignName;
    }
    public String getStartCampaign() 
    {
        return startCampaign;
    }
    public void setStartCampaign( String startCampaign ) 
    {
        this.startCampaign = startCampaign;
    }
    public String getEndCampaign() 
    {
        return endCampaign;
    }
    public void setEndCampaign( String endCampaign ) 
    {
        this.endCampaign = endCampaign;
    }
    public int getCampaignImpact() 
    {
        return campaignImpact;
    }
    public void setCampaignImpact( int campaignImpact ) 
    {
        this.campaignImpact = campaignImpact;
    }
}
