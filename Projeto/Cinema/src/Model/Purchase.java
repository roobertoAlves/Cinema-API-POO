package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class Purchase 
{  
    private int idPurchase;
    private int clientId;
    private int employeeId;
    private String purchaseDate;
    private double totalValue;

    private String tableName = "TBCompras";
    private String fieldsName = "id_compra, cliente_id, funcionario_id, dataCompra, valorTotal";
    private String fieldKey = "id_compra";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public Purchase() 
    {
        // Default constructor
    }
    public Purchase( int idPurchase, int clientId, int employeeId, String purchaseDate, double totalValue ) 
    {
        this.idPurchase = idPurchase;
        this.clientId = clientId;
        this.employeeId = employeeId;
        this.purchaseDate = purchaseDate;
        this.totalValue = totalValue;
    }
    public Purchase( String purchaseDate, double totalValue ) 
    {
        this.purchaseDate = purchaseDate;
        this.totalValue = totalValue;
    }
    
    public int save() 
	{
		if (this.getIdPurchase() > 0) 
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
		if (this.getIdPurchase() > 0) 
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
            "Purchase ID: " + this.getIdPurchase() + 
            ", Client ID: " + this.getClientId() + 
            ", Employee ID: " + this.getEmployeeId() + 
            ", Purchase Date: " + this.getPurchaseDate() + 
            ", Total Value: " + this.getTotalValue()
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdPurchase() ),
            String.valueOf( this.getClientId() ),
            String.valueOf( this.getEmployeeId() ),
            this.getPurchaseDate(),
            String.valueOf( this.getTotalValue() )
		};

		return arrayStr;	
	}

    // Getters and Setters

    public int getIdPurchase() 
    {
        return idPurchase;
    }
    public void setIdPurchase( int idPurchase ) 
    {
        this.idPurchase = idPurchase;
    }
    public int getClientId() 
    {
        return clientId;
    }
    public void setClientId( int clientId ) 
    {
        this.clientId = clientId;
    }
    public int getEmployeeId() 
    {
        return employeeId;
    }
    public void setEmployeeId( int employeeId ) 
    {
        this.employeeId = employeeId;
    }
    public String getPurchaseDate() 
    {
        return purchaseDate;
    }
    public void setPurchaseDate( String purchaseDate ) 
    {
        this.purchaseDate = purchaseDate;
    }
    public double getTotalValue() 
    {
        return totalValue;
    }
    public void setTotalValue( double totalValue ) 
    {
        this.totalValue = totalValue;
    }
}