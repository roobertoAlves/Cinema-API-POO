package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class PurchaseItemsDAO 
{
     private Statement dbLink = null;

	public PurchaseItemsDAO() 
    {
		try 
        {
			this.dbLink = new DBConnection().getConnection().createStatement();
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}

	}

	public int insert(  PurchaseItems items ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( items.getIdItem() > 0 ) 
            {
               String cmd = "INSERT INTO DBCinema.TBItensCompra ( "
                    + "id_item, "
                    + "compra_id, "
                    + "produto_id, "
                    + "quantidade, "
                    + "precoUnitario) ";
                cmd +=  items.getIdItem() +"', '" + 
						items.getPurchaseId() +"', '" +
						items.getProductId() +"', '" +
						items.getQuantity() +"', '" +
						items.getUnitPrice() +
                    ")'" ;

				linesAfected = dbLink.executeUpdate(cmd);

				return linesAfected;
			}
            else
            {
				return 0;
			}
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
			return 0;
		}		
	}

	public int update( PurchaseItems items ) 
    {
		return 0;
	}

	public int delete( PurchaseItems items ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( items.getIdItem() > 0 ) 
            {
                String cmd = "DELETE FROM DBCinema.TBItensCompra";
					   cmd += "WHERE id_item = " + items.getIdItem();

				linesAfected = dbLink.executeUpdate(cmd);

				return linesAfected;

			}
            else
            {
				return 0;
			}
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
			return 0;
		}		
	}

	public ResultSet list( String where ) 
    {
        String cmd = "SELECT id_item, compra_id, produto_id, quantidade, precoUnitario FROM DBCinema.TBItensCompra";
        
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
