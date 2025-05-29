package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class PurchaseItems 
{
    private int idItem;
    private int purchaseId;
    private int productId;
    private int quantity;
    private double unitPrice;

    private String tableName = "TBItensCompra";
    private String fieldsName = "id_item, compra_id, produto_id, quantidade, precoUnitario";
    private String fieldKey = "id_item";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public PurchaseItems() 
    {
        // Default constructor
    }
    public PurchaseItems( int idItem, int purchaseId, int productId, int quantity, double unitPrice ) 
    {
        this.idItem = idItem;
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    public PurchaseItems( int quantity, double unitPrice ) 
    {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    public int save() 
	{
		if ( this.getIdItem() > 0) 
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
		if ( this.getIdItem() > 0 ) 
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
            "PurchaseItem [idItem=" + idItem + 
            ", purchaseId=" + purchaseId + 
            ", productId=" + productId + 
            ", quantity=" + quantity + 
            ", unitPrice=" + unitPrice + "]"
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdItem() ),
            String.valueOf( this.getPurchaseId() ),
            String.valueOf( this.getProductId() ),
            String.valueOf( this.getQuantity() ),
            String.valueOf( this.getUnitPrice() )
		};

		return arrayStr;	
	}

    // Getters and Setters

    public int getIdItem() 
    {
        return idItem;
    }
    public void setIdItem( int idItem ) 
    {
        this.idItem = idItem;
    }
    public int getPurchaseId() 
    {
        return purchaseId;
    }
    public void setPurchaseId( int purchaseId ) 
    {
        this.purchaseId = purchaseId;
    }
    public int getProductId() 
    {
        return productId;
    }
    public void setProductId( int productId ) 
    {
        this.productId = productId;
    }
    public int getQuantity() 
    {
        return quantity;
    }
    public void setQuantity( int quantity ) 
    {
        this.quantity = quantity;
    }
    public double getUnitPrice() 
    {
        return unitPrice;
    }
    public void setUnitPrice( double unitPrice ) 
    {
        this.unitPrice = unitPrice;
    }
}
