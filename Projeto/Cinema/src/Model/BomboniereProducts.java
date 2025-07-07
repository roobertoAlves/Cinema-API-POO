package Model;

import java.sql.ResultSet;
import database.DBQuery;

public class BomboniereProducts 
{

    private int idProduct;
    private String name;
    private double price;
    private String availability;

    private String tableName = "tbprodutosb";
    private String fieldsName = "id_produto, nome, preco, estoqueDisponivel";
    private String fieldKey = "id_produto";

    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public BomboniereProducts() 
    {

    }
    public BomboniereProducts( int idProduct, String name, double price, String availability ) 
    {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }
    public BomboniereProducts( String name, double price, String availability ) 
    {
        this.name = name;
        this.price = price;
        this.availability = availability;
    }
    public int save() 
	{
		if (this.getIdProduct() > 0) 
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
		if ( this.getIdProduct() > 0 ) 
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
            "Product ID: " + this.getIdProduct() + 
            ", Name: " + this.getName() + 
            ", Price: " + this.getPrice() + 
            ", Availability: " + this.getAvailability()
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdProduct() ),
            this.getName(),
            String.valueOf( this.getPrice() ),
            this.getAvailability()
		};

		return arrayStr;	
	}



    public int getIdProduct() 
    {
        return idProduct;
    }
    public void setIdProduct( int idProduct ) 
    {
        this.idProduct = idProduct;
    }
    public String getName() 
    {
        return name;
    }
    public void setName( String name ) 
    {
        this.name = name;
    }
    public double getPrice() 
    {
        return price;
    }
    public void setPrice( double price ) 
    {
        this.price = price;
    }
    public String getAvailability() 
    {
        return availability;
    }
    public void setAvailability( String availability ) 
    {
        this.availability = availability;
    }
}
