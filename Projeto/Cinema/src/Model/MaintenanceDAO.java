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
                String cmd = "INSERT INTO DBCinema.TBManutencao ( "
                    + "id_manutencao, "
                    + "sala_id, "
                    + "descricao, "
                    + "dataInicio, "
                    + "dataFim, "
                    + "funcionario_id) ";
                cmd +=  maintenance.getIdMaintenance() +"', '" +
						maintenance.getRoomId() +"', '" +
						maintenance.getDescription() +"', '" +
						maintenance.getStartDate() +"', '" +
						maintenance.getEndDate() +"', '" +
						maintenance.getEmployeeId() +
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

	public int update( Maintenance maintenance ) 
    {
		return 0;
	}

	public int delete( Maintenance maintenance ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( maintenance.getIdMaintenance() > 0 ) 
            {
                String cmd = "DELETE FROM DBCinema.TBManutencao";
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
        String cmd = "SELECT id_manutencao, sala_id, descricao, dataInicio, dataFim, funcionario_id FROM DBCinema.TBManutencao";
 
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
