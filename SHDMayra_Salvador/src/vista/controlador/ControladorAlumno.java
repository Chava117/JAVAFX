package vista.controlador;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import modelo.conexion;
import entidades.alumno;
import entidades.carrera;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControladorAlumno implements Initializable{

	@FXML private ComboBox<carrera> cboCarrera;
	@FXML private TextField txtNombre;
	@FXML private TextField txtPaterno;
	@FXML private TextField txtFecha;
	@FXML private TextField txtMaterno;
	@FXML private ComboBox<String>cboSexo;
	@FXML private TableView<alumno> jtbAlumno;
	@FXML private TableColumn<alumno, String> colNombre;
	@FXML private TableColumn <alumno, String>colPaterno;
	@FXML private TableColumn <alumno, String>colMaterno;
	@FXML private TableColumn <alumno, String>colFecha;
	@FXML private TableColumn <alumno, String>colSexo;
	@FXML private TableColumn <alumno, String>colCarrera;
	alumno alumnoModificado= new alumno();

	conexion con = new conexion();
	ObservableList<carrera> datos;
	ObservableList<alumno> datos2;
	
	
	@FXML protected void Guardar(){
		try {
			if(txtNombre.getText().trim().isEmpty()|txtPaterno.getText().trim().isEmpty()|
					txtMaterno.getText().trim().isEmpty()|txtFecha.getText().trim().isEmpty()){
			}else{

				alumno a = new alumno(
						txtNombre.getText(),
						txtPaterno.getText(),
						txtMaterno.getText(),
						txtFecha.getText(),
						cboSexo.getValue().toString(),
						cboCarrera.getValue().toString());
				
				String idCarrera = cboCarrera.getValue().toString();
				System.out.println(idCarrera);
						this.llenarTabla();
						a.Guardar(idCarrera);
						listar();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
}

	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		con.conectar();
		ResultSet result=null;
		String sigla= "";
		String nom = "";
		String pa="";
		String ma="";
		String fe="";
		String se="";
		String id="";
		datos=FXCollections.observableArrayList(new carrera(sigla));
		datos2=FXCollections.observableArrayList(new alumno());
		try 
		{
			if(con.conectar()==true)
			{
				String query="select siglas from carrera where activo='0'";
				PreparedStatement comando = con.getConexion().prepareStatement(query);
				result=comando.executeQuery();
				while(result.next())
				{
					sigla=result.getString("siglas");
					datos.add(new carrera(sigla));
				   
				}
				cboCarrera.setItems(datos);
				listar();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.desconectar();
		}
        

	}
	
	
	@FXML protected void modificar(){
		try 
		{
			if(txtNombre.getText().trim().isEmpty() | txtPaterno.getText().isEmpty()
					| txtMaterno.getText().isEmpty() | txtFecha.getText().isEmpty())
			{
			}
			else
			{
				String m="DNM";
				alumno a = new alumno(
						txtNombre.getText(),
						txtPaterno.getText(),
						txtMaterno.getText(),
						txtFecha.getText(),
						cboSexo.getValue().toString(),
						m);
				
				a.modificar(txtFecha.getText());
				listar();
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	@FXML protected void Eliminar()
	{
		try 
		{
			if(txtNombre.getText().trim().isEmpty() | txtPaterno.getText().isEmpty()
					| txtMaterno.getText().isEmpty() | txtFecha.getText().isEmpty())
			{
			}
			else
			{
				alumno a = new alumno();
				a.Eliminar(txtFecha.getText());
				listar();
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void listar() throws SQLException
	{
		con.conectar();
		ResultSet result; 
		String nom = "";
		String pa="";
		String ma="";
		String fe="";
		String se="";
		String id="";
		int dd=0;
		alumno al = new alumno();
	           datos2= FXCollections.observableArrayList(
	            new alumno(nom,pa,ma,fe,se,id)
	            );
		
		try 
		{
			if(con.conectar()==true)
			{
				String query="select nombre,apaterno,amaterno,fecha,sexo, carrera from alumno";
				PreparedStatement comando = con.getConexion().prepareStatement(query);
				result=comando.executeQuery();
				while(result.next())
				{
					
					nom=result.getString("nombre");
					pa=result.getString("apaterno");
					ma=result.getString("amaterno");
					fe=result.getString("fecha");
					se=result.getString("sexo");
					id=result.getString("carrera");
					datos2.add(new alumno(nom,pa,ma,fe,se,id));
				
				   
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.desconectar();
		}
		
		
        colNombre.setCellValueFactory(new PropertyValueFactory<alumno, String>("nombre"));
        colPaterno.setCellValueFactory(new PropertyValueFactory<alumno, String>("apaterno"));
        colMaterno.setCellValueFactory(new PropertyValueFactory<alumno, String>("amaterno"));
		 colFecha.setCellValueFactory(new PropertyValueFactory<alumno, String>("fecha"));
		 colSexo.setCellValueFactory(new PropertyValueFactory<alumno, String>("sexo"));
			colCarrera.setCellValueFactory(new PropertyValueFactory<alumno, String>("carrera"));

		 jtbAlumno.setItems(datos2);
		 datos2=jtbAlumno.getSelectionModel().getSelectedItems();
		 datos2.addListener(seleccion);
		 con.desconectar();
	}
	
	private final ListChangeListener<alumno> seleccion = new ListChangeListener<alumno>()
			{
				public void onChanged(ListChangeListener.Change<? extends alumno> lista)
				{
                    ponerPersonaSeleccionada();
        
					}
			};
			
			private void ponerPersonaSeleccionada() {
		        final alumno alumno = getTablaPersonasSeleccionada();
		       int posicionPersonaEnTabla = datos.indexOf(alumno);

		        if (alumno != null) {

		        	
		        	
		        	
					alumnoModificado =jtbAlumno.getSelectionModel().getSelectedItem();

		        	carrera c = new carrera();
		            // Pongo los textFields con los datos correspondientes
		            txtNombre.setText(alumno.getNombre());
		            txtPaterno.setText(alumno.getApaterno());
		            txtMaterno.setText(alumno.getAmaterno());
		            txtFecha.setText(alumno.getFecha());
		            cboSexo.setValue(alumno.getSexo());
		            //cboCarrera.setValue(c);
		            

		            // Pongo los botones en su estado correspondiente
		            

		        }
		    }
			public void limpiar(){
				txtNombre.setText("");
				txtMaterno.setText("");
				txtPaterno.setText("");
				txtFecha.setText("");
			}
			public alumno getTablaPersonasSeleccionada() {
		        if (jtbAlumno != null) {
		            List<alumno> tabla = jtbAlumno.getSelectionModel().getSelectedItems();
		            if (tabla.size() == 1) {
		                final alumno competicionSeleccionada = tabla.get(0);
		                return competicionSeleccionada;
		            }
		        }
		        return null;
		    }
			
			public void llenarTabla(){
				alumno a = new alumno();
				ObservableList<alumno> lista = a.listarDatos();
				jtbAlumno.setItems(lista);
				jtbAlumno.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			}
	
}
