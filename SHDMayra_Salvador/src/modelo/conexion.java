package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexion 
{
	/*Clase que me permite conectarme a postgresql*/
	
	private String usuario;
	private String contrasenia;
	private String baseDatos;
	private String driver;
	private Connection con;
	
	public conexion()
	{
		usuario="postgres";
		contrasenia="misael";
		baseDatos="jdbc:postgresql://127.0.0.1:5432/SHDMayraAndradeLopez";
		driver="org.postgresql.Driver";
	}
	
	/*El metodo conectasr permite establecer la conexion a al base de datos*/
	
	public boolean conectar()
	{
		try 
		{
			Class.forName(driver);
			con=DriverManager.getConnection(baseDatos,usuario,contrasenia);
			System.out.println("Conectado");
			return true;
		} 
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/*permite cerrar la conexion*/
	
	public boolean desconectar()
	{
		try 
		{
			con.close();
			System.out.println("Desconectado");
			return true;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/*permite recuperar la conexion*/
	
	public Connection getConexion()
	{
		return con;
	}
	
}
