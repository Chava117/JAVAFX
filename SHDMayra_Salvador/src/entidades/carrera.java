package entidades;

import java.sql.PreparedStatement;

import modelo.conexion;

public class carrera {
	
	private String nombre,siglas,jefeCarrera,matricula,acreditado;
	private int id;
	private conexion con;
	
	public carrera(String nombre, String siglas, String jefeCarrera,
			String matricula, String acreditado) {
		this.nombre = nombre;
		this.siglas = siglas;
		this.jefeCarrera = jefeCarrera;
		this.matricula = matricula;
		this.acreditado = acreditado;
		this.con= new conexion();
	}
	
	public carrera(String sigla)
	{
		this.siglas=sigla;
	}
	
	public carrera()
	{
		this.nombre="";
		this.siglas = "";
		this.jefeCarrera = "";
		this.matricula = "";
		this.acreditado = "";
		this.id = 0;
		this.con= new conexion();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSiglas() {
		return siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	public String getJefeCarrera() {
		return jefeCarrera;
	}

	public void setJefeCarrera(String jefeCarrera) {
		this.jefeCarrera = jefeCarrera;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getAcreditado() {
		return acreditado;
	}

	public void setAcreditado(String acreditado) {
		this.acreditado = acreditado;
	}

	
	public String guardar()
	{
		String mensaje="";
		try 
		{
			if(con.conectar()==true)
			{
				String consulta="insert into carrera values(default,?,?,?,?,?)";
				PreparedStatement comando = con.getConexion().prepareStatement(consulta);
				comando.setString(1, this.nombre);
				comando.setString(2, this.siglas);
				comando.setString(3, this.jefeCarrera);
				comando.setString(4, this.matricula);
				comando.setString(5, this.acreditado);
				comando.executeUpdate();
				
				mensaje="Datos insertados con éxito.";
			}
		}
		catch (Exception e)
		{
			mensaje="No se insertaron datos.";
			e.printStackTrace();
		}
		finally
		{
			con.desconectar();
		}
		
		return mensaje;
	}

	public String modificar(String matricula){
		String mensaje="";
		try
		{
			if(con.conectar()==true)
			{
				String modificar="update carrera set nombre=?,siglas=?,jefeCarrera=?,matricula=?,acreditado=? where matricula='"+matricula+"'";
				PreparedStatement comando = con.getConexion().prepareStatement(modificar);
				comando.setString(1, this.nombre);
				comando.setString(2, this.siglas);
				comando.setString(3, this.jefeCarrera);
				comando.setString(4, this.matricula);
				comando.setString(5, this.acreditado);
				comando.executeUpdate();
				mensaje="Datos modificados corretamente";
			}
		} catch (Exception e) 
		{
			mensaje="No se pudieron  modificarf los datos";
			e.getMessage();
		}
		finally
		{
			con.desconectar();
		}
		return mensaje;
	}
	
	
	public String Eliminar()
	{
		String mensaje="";
		try 
		{
			if(con.conectar()==true)
			{
				String Eliminar="update carrera set activo='1' where matricula=?";
				PreparedStatement comando = con.getConexion().prepareStatement(Eliminar);
				comando.setString(1, this.matricula);
				comando.executeUpdate();
				mensaje="Datos eliminados corretamente";
			}
		} 
		catch (Exception e) 
		{
			mensaje="No se pudieron eliminar los datos";
			e.printStackTrace();
		}
		finally
		{
			con.desconectar();
		}
		return mensaje;
	}	
	
	
	@Override
	public String toString() {
		return siglas;
	}
	
	

}
