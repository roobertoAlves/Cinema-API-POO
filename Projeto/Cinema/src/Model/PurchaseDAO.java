package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class PurchaseDAO 
{
     private Statement dbLink = null;

	public PurchaseDAO() 
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

	public int insert(  Purchase purchase ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( purchase.getIdPurchase() > 0 ) 
            {
               String cmd = "INSERT INTO DBCinema.TBCompras ( "
                    + "id_compra, "
                    + "cliente_id, "
                    + "funcionario_id, "
                    + "dataCompra, "
                    + "valorTotal) ";
                cmd +=  purchase.getIdPurchase() +"', '" + purchase.getIdPurchase() +
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

	public int update( Purchase purchase ) 
    {
		return 0;
	}

	public int delete( Purchase purchase ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( purchase.getIdPurchase() > 0 ) 
            {
                String cmd = "DELETE FROM DBCinema.TBCompras WHERE id_compra = " + purchase.getIdPurchase();

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
        String cmd = "SELECT id_compra, cliente_id, funcionario_id, dataCompra, valorTotal FROM DBCinema.TBCompras";
        
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
