package Model;

import database.DBQuery;
import java.sql.ResultSet;

public class Clients 
{

    private int idClient;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private String phone;
    private String loyaltyProfile;
    private int points = 0;

    private String tableName = "tbclientes";
    private String fieldsName = "id_cliente, nome, email, senha, cpf, telefone, perfilFidelidade, pontos";
    private String fieldKey = "id_cliente";
    private DBQuery dbQuery = new DBQuery( tableName, fieldsName, fieldKey );

    public Clients() 
    {
        // Default constructor
    }
    public Clients( int idClient, String name, String email, String password, String cpf, String phone, String loyaltyProfile, int points ) 
    {
        this.idClient = idClient;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phone = phone;
        this.loyaltyProfile = loyaltyProfile;
        this.points = points;
    }
    public Clients( String name, String email, String password, String cpf, String phone, String loyaltyProfile ) 
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phone = phone;
        this.loyaltyProfile = loyaltyProfile;
    }

    public int save() 
	{
		if ( this.getIdClient() > 0 ) 
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
		if (this.getIdClient() > 0) 
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
            "ID: " + this.getIdClient() + 
            ", Name: " + this.getName() + 
            ", Email: " + this.getEmail() + 
            ", CPF: " + this.getCpf() + 
            ", Phone: " + this.getPhone() + 
            ", Loyalty Profile: " + this.getLoyaltyProfile() +
            ", Points: " + this.getPoints()
        );
	}

	public String[] toArray() 
	{
		String[] arrayStr = 
		{
            String.valueOf( this.getIdClient() ),
            this.getName(),
            this.getEmail(),
            this.getPassword(),
            this.getCpf(),
            this.getPhone(),
            this.getLoyaltyProfile(),
            String.valueOf( this.getPoints() )
		};

		return arrayStr;	
	}

    // Getters and Setters

    public int getIdClient() 
    {
        return idClient;
    }
    public void setIdClient( int idClient ) 
    {
        this.idClient = idClient;
    }
    public String getName() 
    {
        return name;
    }
    public void setName( String name ) 
    {
        this.name = name;
    }
    public String getEmail() 
    {
        return email;
    }
    public void setEmail( String email ) 
    {
        this.email = email;
    }
    public String getPassword() 
    {
        return password;
    }
    public void setPassword( String password ) 
    {
        this.password = password;
    }
    public String getCpf() 
    {
        return cpf;
    }
    public void setCpf( String cpf ) 
    {
        this.cpf = cpf;
    }
    public String getPhone() 
    {
        return phone;
    }
    public void setPhone( String phone ) 
    {
        this.phone = phone;
    }
    public String getLoyaltyProfile() 
    {
        return loyaltyProfile;
    }
    public void setLoyaltyProfile( String loyaltyProfile ) 
    {
        this.loyaltyProfile = loyaltyProfile;
    }
    public int getPoints() 
    {
        return points;
    }
    public void setPoints( int points ) 
    {
        this.points = points;
    }

}
