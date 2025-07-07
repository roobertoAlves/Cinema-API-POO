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
    {        try 
        {
			int linesAfected = 0;

			String employeeIdValue = purchase.getEmployeeId() == 0 ? "NULL" : String.valueOf(purchase.getEmployeeId());
			
			String cmd = "INSERT INTO bdcinema.tbcompras (cliente_id, funcionario_id, " + 
        		   		"dataCompra, valorTotal) VALUES ("
        		   		+ purchase.getClientId() + ", "
        		   		+ employeeIdValue + ", '"
        		   		+ purchase.getPurchaseDate() + "', "
        		   		+ purchase.getTotalValue() + ")";

			linesAfected = dbLink.executeUpdate( cmd );

			return linesAfected;
		} 
        catch ( SQLException e ) 
        {
			e.printStackTrace();
			return 0;
		}		
	}

	public int update( Purchase purchase ) 
    {
		try 
		{
			int linesAfected = 0;

			if ( purchase.getIdPurchase() > 0 ) 
			{
				String cmd = "UPDATE bdcinema.tbcompras SET " + 
							 "cliente_id = " + purchase.getClientId() +
							 ", funcionario_id = " + purchase.getEmployeeId() +
							 ", dataCompra = '" + purchase.getPurchaseDate() +
							 "', valorTotal = " + purchase.getTotalValue() +
							 " WHERE id_compra = " + purchase.getIdPurchase();

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

	public int delete( Purchase purchase ) 
    {
		try 
        {
			int linesAfected = 0;            if ( purchase.getIdPurchase() > 0 ) 
            {
                String cmd = "DELETE FROM bdcinema.tbcompras WHERE id_compra = " + purchase.getIdPurchase();

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
        String cmd = "SELECT id_compra, cliente_id, funcionario_id, dataCompra, " + 
        			 "valorTotal FROM bdcinema.tbcompras";
        
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
	
	public int insertAndGetId(Purchase purchase) {
		try {
			String employeeIdValue = purchase.getEmployeeId() == 0 ? "NULL" : String.valueOf(purchase.getEmployeeId());
			
			String cmd = "INSERT INTO bdcinema.tbcompras (cliente_id, funcionario_id, " + 
        		   		"dataCompra, valorTotal) VALUES ("
        		   		+ purchase.getClientId() + ", "
        		   		+ employeeIdValue + ", '"
        		   		+ purchase.getPurchaseDate() + "', "
        		   		+ purchase.getTotalValue() + ")";

			int linesAffected = dbLink.executeUpdate(cmd, Statement.RETURN_GENERATED_KEYS);
			
			if (linesAffected > 0) {
				ResultSet generatedKeys = dbLink.getGeneratedKeys();
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	} 
}
