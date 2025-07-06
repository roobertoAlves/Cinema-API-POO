package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import database.DBConnection;

public class EmployeeDAO 
{
     private Statement dbLink = null;

	public EmployeeDAO() 
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

	public int insert(  Employee employee ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( employee.getIdEmployee() > 0 ) 
            {
               String cmd = "INSERT INTO bdcinema.tbfuncionarios (id_funcionario, nome, cargo, cpf, email, telefone) VALUES ("
						+ employee.getIdEmployee() + ", '"
						+ employee.getName() + "', '"
						+ employee.getPosition() + "', '"
						+ employee.getCpf() + "', '"
						+ employee.getEmail() + "', '"
						+ employee.getPhone() + "')";
               

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

	public int update( Employee employee ) 
    {
		try 
		{
			int linesAfected = 0;

			if ( employee.getIdEmployee() > 0 ) 
			{
				String cmd = "UPDATE bdcinema.tbfuncionarios SET" 
						+ "nome = '" + employee.getName() + "', "
						+ "cargo = '" + employee.getPosition() + "', "
						+ "cpf = '" + employee.getCpf() + "', "
						+ "email = '" + employee.getEmail() + "', "
						+ "telefone = '" + employee.getPhone() + "' "
						+ "WHERE id_funcionario = " + employee.getIdEmployee();

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

	public int delete( Employee employee ) 
    {
		try 
        {
			int linesAfected = 0;

			if ( employee.getIdEmployee() > 0 ) 
            {
                String cmd = "DELETE FROM bdcinema.tbfuncionarios";
					   cmd += "WHERE id_funcionario = " + employee.getIdEmployee();

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
        String cmd = "SELECT id_funcionario, nome, cargo, cpf, email, telefone FROM bdcinema.tbfuncionarios";
        
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
