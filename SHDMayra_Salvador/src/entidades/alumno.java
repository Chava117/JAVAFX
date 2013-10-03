package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import modelo.conexion;

public class alumno {
	
	private String nombre,apaterno,amaterno,fecha,sexo, carrera;
	private int id;
	private conexion con;
	
	public alumno()
	{
		this.nombre = "";
		this.apaterno = "";
		this.amaterno = "";
		this.fecha ="";
		this.sexo = "";
		this.carrera = "";
		this.con=new conexion();
	}
	

	

	public alumno(String nombre, String apaterno, String amaterno,
			String fecha, String sexo, String carrera) {
		this.nombre = nombre;
		this.apaterno = apaterno;
		this.amaterno = amaterno;
		this.fecha = fecha;
		this.sexo = sexo;
		this.carrera =carrera;
		this.con=new conexion();
	}



	




	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApaterno() {
		return apaterno;
	}

	public void setApaterno(String apaterno) {
		this.apaterno = apaterno;
	}

	public String getAmaterno() {
		return amaterno;
	}

	public void setAmaterno(String amaterno) {
		this.amaterno = amaterno;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "alumno [nombre=" + nombre + ", apaterno=" + apaterno
				+ ", amaterno=" + amaterno + ", fecha=" + fecha + ", sexo="
				+ sexo + ", carrera=" + carrera + "]";
	}
	
	
	public String Guardar(String carrera){
		String mensaje="";
		Integer idCarrera=0;
		try{
			con.conectar();
			if(con.conectar()){
				System.out.println("yaaa");

				String consultaID= "SELECT id from Carrera where siglas='"+carrera+"'";
				PreparedStatement coman = con.getConexion().prepareStatement(consultaID);
				ResultSet res = coman.executeQuery();
				
				while(res.next()){
					idCarrera = res.getInt("id");
					System.out.println(idCarrera);
				}
				
				String consulta ="INSERT INTO alumno VALUES(default, ?, ?, ?, ?, ?, ?)";
				PreparedStatement comando = con.getConexion().prepareStatement(consulta);
				
				comando.setString(1,this.nombre);
				comando.setString(2, this.apaterno);
				comando.setString(3, this.amaterno);
				comando.setString(4, this.fecha);
				comando.setString(5, this.sexo);
				comando.setInt(6, idCarrera);
				comando.executeUpdate();
				System.out.println(consulta);
				mensaje="Datos insertados con exito.";
				System.out.println(mensaje);
				listarDatos();
			}
		}catch(Exception e){
			mensaje="No se insertaron los datos.";
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			con.desconectar();
			mensaje="nada";
		}
		return mensaje;
	}
	
	
	
	
	
	
	public String modificar(String nombre){
		String mensaje="";
		try
		{
			if(con.conectar())
			{
				int id=0;
				String sql = "select idalumno from alumno where fecha ='"+nombre+"'";
				PreparedStatement com= con.getConexion().prepareStatement(sql);
				ResultSet re = com.executeQuery();
				System.out.println(sql);
				while(re.next()){
				id=re.getInt("idalumno");	
				}
				
				String modificar="update alumno set nombre=?,apaterno=?,amaterno=?,fecha=?,sexo=? where idalumno="+id+"";
				PreparedStatement comando = con.getConexion().prepareStatement(modificar);
				comando.setString(1, this.nombre);
				comando.setString(2, this.apaterno);
				comando.setString(3, this.amaterno);
				comando.setString(4, this.fecha);
				comando.setString(5, this.sexo);
				comando.executeUpdate();
				System.out.println(modificar);
				mensaje="Datos modificados corretamente";
				listarDatos();
			}
		} catch (Exception e) 
		{
			mensaje="No se pudieron  modificar los datos";
			e.getMessage();
		}
		finally
		{
			con.desconectar();
		}
		return mensaje;
	}
	
	public String Eliminar(String fecha)
	{
		String mensaje="";
		try 
		{
			if(con.conectar()==true)
			{
				String Eliminar="delete from alumno where fecha='"+fecha+"'";
				PreparedStatement comando = con.getConexion().prepareStatement(Eliminar);
				comando.executeUpdate();
				
				mensaje="Datos eliminados corretamente";
				listarDatos();
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
	

	public ObservableList<alumno> listarDatos(){
		try{
			ObservableList<alumno> lista = FXCollections.observableArrayList();
			if(con.conectar()){
				String consulta = "select * from alumno";
				Statement comando = con.getConexion().createStatement();
				ResultSet resultado = comando.executeQuery(consulta);
				if(resultado!=null){
					while(resultado.next()){
						alumno a = new alumno();
						a.setNombre(resultado.getString("nombre"));
						a.setApaterno(resultado.getString("apaterno"));
						a.setAmaterno(resultado.getString("amaterno"));
						a.setFecha(resultado.getString("fecha"));
						a.setSexo(resultado.getString("sexo"));
						//a.setCarrera(resultado.getString("idCarrera"));
						lista.add(a);
					}
				}
				resultado.close();
			}
			return lista;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return null;
		}finally{
			con.desconectar();
		}
	}
}
