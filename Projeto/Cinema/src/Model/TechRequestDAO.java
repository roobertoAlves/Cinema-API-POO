package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class TechRequestDAO 
{
    private Statement dbLink = null;

	public TechRequestDAO() 
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

	public int insert(  TechRequest request ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( request.getIdRequest() > 0 ) 
            {
                String cmd = "INSERT INTO bdcinema.tbchamadostec (descricao, statusChamado," + 
                			 "dataAbertura, sala_id) VALUES ('" 
                			 + request.getDescription() + "', '"
                			 + request.getStatus() + "', '"
                			 + request.getOpenData() + "', "
                			 + request.getRoomId() + ")";
                

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

	public int update( TechRequest request ) 
    {
		try 
		{
			int linesAfected = 0;

			if ( request.getIdRequest() > 0 ) 
			{
				String cmd = "UPDATE bdcinema.tbchamadostec SET" + ""
							 + "descricao = '" + request.getDescription()  
							 + "', statusChamado = '" + request.getStatus() 
							 + "', dataAbertura = '" + request.getOpenData() 
							 + "', sala_id = " + request.getRoomId() 
							 + " WHERE id_chamado = " + request.getIdRequest();

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

	public int delete( TechRequest request ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( request.getIdRequest() > 0 ) 
            {
                String cmd = "DELETE FROM bdcinema.tbchamadostec ";
					   cmd += "WHERE id_chamado = " + request.getIdRequest();

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
        String cmd = "SELECT id_chamado, descricao, statusChamado, dataAbertura," + 
        			 "sala_id FROM bdcinema.tbchamadostec";
 
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
