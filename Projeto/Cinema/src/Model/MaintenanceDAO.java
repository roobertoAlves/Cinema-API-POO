package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class MaintenanceDAO 
{
    private Statement dbLink = null;

	public MaintenanceDAO() 
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

	public int insert(  Maintenance maintenance ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( maintenance.getIdMaintenance() > 0 ) 
            {
                String cmd = "INSERT INTO bdcinema.tbmanutencao (id_manutencao, sala_id," + 
                			 "descricao, dataInicio, dataFim, funcionario_id) VALUES ("
                	+ maintenance.getIdMaintenance() + ", '"
                	+ maintenance.getRoomId() + "', '"
                	+ maintenance.getDescription() + "', '"
                	+ maintenance.getStartDate() + "', '"
                	+ maintenance.getEndDate() + "', '"
                	+ maintenance.getEmployeeId() + "')";
                

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

	public int update( Maintenance maintenance ) 
    {
		try 
		{
			int linesAfected = 0;

			if ( maintenance.getIdMaintenance() > 0 ) 
			{
				String cmd = "UPDATE bdcinema.tbmanutencao SET" 
							 + "sala_id = '" + maintenance.getRoomId() + "', "
							 + "descricao = '" + maintenance.getDescription() + "', "
							 + "dataInicio = '" + maintenance.getStartDate() + "', "
							 + "dataFim = '" + maintenance.getEndDate() + "', "
							 + "funcionario_id = '" + maintenance.getEmployeeId() + "' "
							 + "WHERE id_manutencao = " + maintenance.getIdMaintenance();

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

	public int delete( Maintenance maintenance ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( maintenance.getIdMaintenance() > 0 ) 
            {
                String cmd = "DELETE FROM bdcinema.tbmanutencao";
					   cmd += "WHERE id_manutencao = " + maintenance.getIdMaintenance();

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
        String cmd = "SELECT id_manutencao, sala_id, descricao, dataInicio, dataFim, funcionario_id FROM bdcinema.tbmanutencao";
 
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
