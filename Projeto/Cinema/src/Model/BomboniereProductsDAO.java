package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class BomboniereProductsDAO 
{
    private Statement dbLink = null;

	public BomboniereProductsDAO() 
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

	public int insert(  BomboniereProducts products ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( products.getIdProduct() > 0 ) 
            {
                String cmd = "INSERT INTO DBCinema.TBProdutosB ( "
                    + "id_produto, "
                    + "nome, "
                    + "preco, "
                    + "estoqueDisponivel) ";
                cmd +=  products.getIdProduct() +"', '" + 
						products.getName() +"', '" +
						products.getPrice() +"', '" +
						products.getAvailability() +
                    ")'" ;

				linesAfected = dbLink.executeUpdate( cmd );

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

	public int update( BomboniereProducts products ) 
    {
		return 0;
	}

	public int delete( BomboniereProducts products ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( products.getIdProduct() > 0 ) 
            {
                String cmd = "DELETE FROM DBCinema.TBProdutosB"; 
					   cmd += "WHERE id_produto = '" + products.getIdProduct() + "'";

				linesAfected = dbLink.executeUpdate( cmd );

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
        String cmd = "SELECT id_produto, nome, preco, estoqueDisponivel FROM DBCinema.TBProdutosB";
 
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
