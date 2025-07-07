package database;


import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection 
{

	private String host;
	private String port;
	private String schema;
	private String user;
	private String password;
	
	private Connection connection = null;
	
	public DBConnection(String host, String port, String schema, String user, String password) 
	{
		this.setHost(host);
		this.setPort(port);
		this.setSchema(schema);
		this.setUser(user);
		this.setPassword(password);
		this.doConnection();
	}
	
	public DBConnection() 
	{
		this.setHost	("localhost");
		this.setPort	("3306");
		this.setSchema	("bdcinema");
		this.setUser	("root");
		this.setPassword(""); // XAMPP geralmente não tem senha para root
		this.doConnection();
	}
	
	private void doConnection() 
	{
		String timezone = "&useTimezone=true&serverTimezone=UTC";// use o &useTimezone=true&serverTimezone=UTC para não ter problemas de data;
		String url = "jdbc:mysql://"+this.host+":"+port+"/"+this.schema+"?user="+this.user+"&password="+this.password+timezone;
		
		try 
		{
			// Class.forName("com.mysql.jdbc.Driver").newInstance();
			// A linha acima foi depreciada após o mysql 8.0
			// A partir do mysql-connector-java-8.0.17.jar utilize as duas linhas abaixo
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			
			System.out.println("Tentando conectar com: " + url);
			this.connection = DriverManager.getConnection(url);
			
			if (this.connection != null) {
				System.out.println("Conexão estabelecida com sucesso!");
			}
		} 
		catch (InstantiationException e)
		{
			System.err.println("Erro de InstantiationException: " + e.getMessage());
			e.printStackTrace();
		} 
		catch (IllegalAccessException e)
		{
			System.err.println("Erro de IllegalAccessException: " + e.getMessage());
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			System.err.println("Driver MySQL não encontrado! Verifique se o mysql-connector-java está no classpath.");
			System.err.println("Erro: " + e.getMessage());
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			System.err.println("Erro de conexão com o banco de dados:");
			System.err.println("URL: " + url);
			System.err.println("Host: " + this.host);
			System.err.println("Port: " + this.port);
			System.err.println("Schema: " + this.schema);
			System.err.println("User: " + this.user);
			System.err.println("Erro SQL: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("ErrorCode: " + e.getErrorCode());
			
			// Sugestões de solução
			if (e.getErrorCode() == 0) {
				System.err.println("\nPossíveis soluções:");
				System.err.println("1. Verifique se o MySQL/XAMPP está rodando");
				System.err.println("2. Verifique se a porta 3306 está disponível");
				System.err.println("3. Verifique se o banco 'BDCinema' existe");
			} else if (e.getErrorCode() == 1045) {
				System.err.println("\nErro de autenticação:");
				System.err.println("1. Verifique o usuário e senha");
				System.err.println("2. Para XAMPP, geralmente user='root' e password=''");
			} else if (e.getErrorCode() == 1049) {
				System.err.println("\nBanco de dados não encontrado:");
				System.err.println("1. Execute o script create_bdcinema.sql");
				System.err.println("2. Certifique-se que o banco 'BDCinema' foi criado");
			}
			
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) 
		{
			System.err.println("Erro de IllegalArgumentException: " + e.getMessage());
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) 
		{
			System.err.println("Erro de InvocationTargetException: " + e.getMessage());
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) 
		{
			System.err.println("Erro de NoSuchMethodException: " + e.getMessage());
			e.printStackTrace();
		} 
		catch (SecurityException e) 
		{
			System.err.println("Erro de SecurityException: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String getHost() 
	{
		return host;
	}
	
	public void setHost(String host) 
	{
		this.host = ( host.isEmpty() ? "localhost" : host ) ;
	}
	
	public String getPort() 
	{
		return port;
	}
	
	public void setPort(String port) 
	{
		this.port = ( port.isEmpty() ? "3306" : port ) ;
	}
	
	public String getSchema() 
	{
		return schema;
	}
	
	public void setSchema(String schema) 
	{
		this.schema = schema;
	}
	
	public String getUser() 
	{
		return user;
	}
	
	public void setUser(String user) 
	{
		this.user = user;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public Connection getConnection() 
	{
		return (this.connection);
	}

}
