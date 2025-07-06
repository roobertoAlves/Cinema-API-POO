package Model;


import database.DBQuery;
import java.sql.ResultSet;

public class Employee 
{
    private int idEmployee;
    private String name;
    private String position;
    private String cpf;
    private String email;
    private String phone;

    private String tableName = "tbfuncionarios";
    private String fieldsName = "id_funcionario, nome, cargo, cpf, email, telefone";
    private String fieldKey = "id_funcionario";
    private DBQuery dbQuery = new DBQuery(tableName, fieldsName, fieldKey);

    public Employee() 
    {
        // Default constructor
    }
    public Employee(int idEmployee, String name, String position, String cpf, String email, String phone) 
    {
        this.idEmployee = idEmployee;
        this.name = name;
        this.position = position;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
    }
    public Employee(String name, String position, String cpf, String email, String phone) 
    {
        this.name = name;
        this.position = position;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
    }

    public int save() 
	{
		if (this.getIdEmployee() > 0) 
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
		if ( this.getIdEmployee() > 0 ) 
        {
            return ( dbQuery.delete(this.toArray()) );
        }

		return (0);
	}
	
	public ResultSet listAll() 
	{
		return ( dbQuery.select("") );
	}

    // To string and to Array methods

    public String toString() 
	{
		return(
            "Employee ID: " + this.getIdEmployee() + 
            ", Name: " + this.getName() + 
            ", Position: " + this.getPosition() + 
            ", CPF: " + this.getCpf() + 
            ", Email: " + this.getEmail() + 
            ", Phone: " + this.getPhone()
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdEmployee() ),
            this.getName(),
            this.getPosition(),
            this.getCpf(),
            this.getEmail(),
            this.getPhone()
		};

		return arrayStr;	
	}

    // Getters and Setters

    public int getIdEmployee() 
    {
        return idEmployee;
    }
    public void setIdEmployee( int idEmployee ) 
    {
        this.idEmployee = idEmployee;
    }

    public String getName() 
    {
        return name;
    }
    public void setName( String name ) 
    {
        this.name = name;
    }
    public String getPosition() 
    {
        return position;
    }
    public void setPosition( String position ) 
    {
        this.position = position;
    }
    public String getCpf() 
    {
        return cpf;
    }
    public void setCpf( String cpf  ) 
    {
        this.cpf = cpf;
    }
    public String getEmail() 
    {
        return email;
    }
    public void setEmail( String email ) 
    {
        this.email = email;
    }
    public String getPhone() 
    {
        return phone;
    }
    public void setPhone( String phone ) 
    {
        this.phone = phone;
    }
    
}
