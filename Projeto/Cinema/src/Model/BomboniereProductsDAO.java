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

    public int insert(BomboniereProducts product) 
    {
        try 
        {
            int linesAffected = 0;

            String cmd = "INSERT INTO bdcinema.tbprodutosb (nome, preco, estoqueDisponivel) VALUES ('"
                + product.getName() + "', "
                + product.getPrice() + ", '"
                + product.getAvailability() + "')";

            linesAffected = dbLink.executeUpdate(cmd);
            return linesAffected;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(BomboniereProducts product) 
    {
        try 
		{
			int linesAffected = 0;

			if (product.getIdProduct() > 0) 
			{
				String cmd = "UPDATE bdcinema.tbprodutosb SET " + 
					"nome = '" + product.getName() + "', " +
					"preco = " + product.getPrice() + ", " +
					"estoqueDisponivel = '" + product.getAvailability() + "' " +
					"WHERE id_produto = " + product.getIdProduct();

				linesAffected = dbLink.executeUpdate(cmd);
				return linesAffected;
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

    public int delete(BomboniereProducts product) 
    {
        try 
        {
            int linesAffected = 0;

            if (product.getIdProduct() > 0) 
            {
                String cmd = "DELETE FROM bdcinema.tbprodutosb WHERE id_produto = " + product.getIdProduct();

                linesAffected = dbLink.executeUpdate(cmd);
                return linesAffected;
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

    public ResultSet list(String where) 
    {
        String cmd = "SELECT id_produto, nome, preco, estoqueDisponivel FROM bdcinema.tbprodutosb";

        if (!where.isEmpty()) 
        {
            cmd += " WHERE " + where;
        }

        ResultSet rs = null;

        try 
        {
            rs = dbLink.executeQuery(cmd);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return rs;
    }
}
