package vista.controlador;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import modelo.conexion;

import entidades.carrera;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControladorCarrera implements Initializable{

	@FXML private TextField txtNombre,txtSiglas,txtJefe,txtMatricula;
	@FXML private ComboBox<String> cboAcreditado;
	@FXML private TableView<carrera> jtbCarrera;
	@FXML private TableColumn<carrera, String> colNombre;
	@FXML private TableColumn <carrera, String>colSiglas;
	@FXML private TableColumn <carrera, String>colJefe;
	@FXML private TableColumn <carrera, String>colMatricula;
	@FXML private TableColumn <carrera, String>colAcreditado;
	@FXML private TableColumn <carrera, Integer>colId;
	@FXML private Button btnGuardar,btnModificar,btnEliminar;
	
	
	conexion con = new conexion();
	ObservableList<carrera> datos;
	
	
	@FXML protected void guardar()
	{
		try 
		{
			if(txtNombre.getText().trim().isEmpty() | txtSiglas.getText().isEmpty()
					| txtJefe.getText().isEmpty() | txtMatricula.getText().isEmpty())
			{
			}
			else
			{
				carrera c = new carrera(txtNombre.getText(),txtSiglas.getText(),
						txtJefe.getText(),txtMatricula.getText(), cboAcreditado.getValue()
						);
				c.setNombre(txtNombre.getText());
				c.setSiglas(txtSiglas.getText());
				c.setJefeCarrera(txtJefe.getText());
				c.setMatricula(txtMatricula.getText());
				c.setAcreditado(cboAcreditado.getValue());
				if(buscarExistente())
				{
					JOptionPane.showMessageDialog(null, "Matrícula existente");
				}
				else
				{	
				c.guardar();
				this.refrescar();
				this.limpiar();
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	@FXML protected void modificar(){
		try 
		{
			if(txtNombre.getText().trim().isEmpty() | txtSiglas.getText().isEmpty()
					| txtJefe.getText().isEmpty() | txtMatricula.getText().isEmpty())
			{
			}
			else
			{
				carrera c = new carrera(txtNombre.getText(),txtSiglas.getText(),
						txtJefe.getText(),txtMatricula.getText(), cboAcreditado.getValue()
						);
				
				c.modificar(txtMatricula.getText());
				this.refrescar();
				this.limpiar();
				txtMatricula.setEditable(true);
				
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
			if(txtNombre.getText().trim().isEmpty() | txtSiglas.getText().isEmpty()
					| txtJefe.getText().isEmpty() | txtMatricula.getText().isEmpty())
			{
			}
			else
			{
				carrera c = new carrera(txtNombre.getText(),txtSiglas.getText(),
						txtJefe.getText(),txtMatricula.getText(), cboAcreditado.getValue());
				c.Eliminar();
				this.refrescar();
				this.limpiar();
				txtMatricula.setEditable(true);
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	public void limpiar(){
		txtNombre.setText("");
		txtJefe.setText("");
		txtMatricula.setText("");
		txtSiglas.setText("");
		cboAcreditado.getItems().get(0);
	}
	public void refrescar(){
		con.conectar();
		ResultSet result;
		String nom = "";
		String si="";
		String je="";
		String matri="";
		String acre="";

	           datos= FXCollections.observableArrayList(
	            new carrera(nom,si,je,matri,acre)
	            );
		
		try 
		{
			if(con.conectar()==true)
			{
				String query="select nombre,siglas,jefecarrera,matricula,acreditado from carrera where activo='0'";
				PreparedStatement comando = con.getConexion().prepareStatement(query);
				result=comando.executeQuery();
				while(result.next())
				{
					nom=result.getString("nombre");
					si=result.getString("siglas");
					je=result.getString("jefecarrera");
					matri=result.getString("matricula");
					acre=result.getString("acreditado");
					datos.add(new carrera(nom,si,je,matri,acre));
				
				   
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
        
        colNombre.setCellValueFactory(new PropertyValueFactory<carrera, String>("nombre"));
        colSiglas.setCellValueFactory(new PropertyValueFactory<carrera, String>("siglas"));
        colJefe.setCellValueFactory(new PropertyValueFactory<carrera, String>("jefeCarrera"));
		 colMatricula.setCellValueFactory(new PropertyValueFactory<carrera, String>("matricula"));
		 colAcreditado.setCellValueFactory(new PropertyValueFactory<carrera, String>("acreditado"));
       
		 jtbCarrera.setItems(datos) ;
		 datos=jtbCarrera.getSelectionModel().getSelectedItems();
		 datos.addListener(seleccion);
		 btnGuardar.setDisable(false);
		 btnModificar.setDisable(true);
		 btnEliminar.setDisable(true);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		con.conectar();
		ResultSet result;
		String nom = "";
		String si="";
		String je="";
		String matri="";
		String acre="";

	           datos= FXCollections.observableArrayList(
	            new carrera(nom,si,je,matri,acre)
	            );
		
		try 
		{
			if(con.conectar()==true)
			{
				String query="select nombre,siglas,jefecarrera,matricula,acreditado from carrera where activo='0'";
				PreparedStatement comando = con.getConexion().prepareStatement(query);
				result=comando.executeQuery();
				while(result.next())
				{
					nom=result.getString("nombre");
					si=result.getString("siglas");
					je=result.getString("jefecarrera");
					matri=result.getString("matricula");
					acre=result.getString("acreditado");
					datos.add(new carrera(nom,si,je,matri,acre));
				
				   
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
        
        colNombre.setCellValueFactory(new PropertyValueFactory<carrera, String>("nombre"));
        colSiglas.setCellValueFactory(new PropertyValueFactory<carrera, String>("siglas"));
        colJefe.setCellValueFactory(new PropertyValueFactory<carrera, String>("jefeCarrera"));
		 colMatricula.setCellValueFactory(new PropertyValueFactory<carrera, String>("matricula"));
		 colAcreditado.setCellValueFactory(new PropertyValueFactory<carrera, String>("acreditado"));
       
		 jtbCarrera.setItems(datos) ;
		 datos=jtbCarrera.getSelectionModel().getSelectedItems();
		 datos.addListener(seleccion);
		 btnGuardar.setDisable(false);
		 btnModificar.setDisable(true);
		 btnEliminar.setDisable(true);
		 txtMatricula.setEditable(true);
	}
	
	private final ListChangeListener<carrera> seleccion = new ListChangeListener<carrera>()
			{
				public void onChanged(ListChangeListener.Change<? extends carrera> lista)
				{
                    ponerPersonaSeleccionada();
                    btnGuardar.setDisable(true);
           		 btnModificar.setDisable(false);
           		 btnEliminar.setDisable(false);
           		txtMatricula.setEditable(false);
					}
			};
			
			private void ponerPersonaSeleccionada() {
		        final carrera persona = getTablaPersonasSeleccionada();
		       int posicionPersonaEnTabla = datos.indexOf(persona);

		        if (persona != null) {

		            // Pongo los textFields con los datos correspondientes
		            txtNombre.setText(persona.getNombre());
		            txtSiglas.setText(persona.getSiglas());
		            txtJefe.setText(persona.getJefeCarrera());
		            txtMatricula.setText(persona.getMatricula());
		            cboAcreditado.setValue(persona.getAcreditado());

		            // Pongo los botones en su estado correspondiente
		            

		        }
		    }
			public carrera getTablaPersonasSeleccionada() {
		        if (jtbCarrera != null) {
		            List<carrera> tabla = jtbCarrera.getSelectionModel().getSelectedItems();
		            if (tabla.size() == 1) {
		                final carrera competicionSeleccionada = tabla.get(0);
		                return competicionSeleccionada;
		            }
		        }
		        return null;
		    }
			
			public boolean buscarExistente() throws SQLException
			{
				String mat="";
				if(con.conectar())
				{
					
					String busca="select matricula from carrera where matricula='"+txtMatricula.getText()+"' and activo='0';";
					PreparedStatement consulta = con.getConexion().prepareStatement(busca);
					ResultSet resultado = consulta.executeQuery();
					System.out.println(busca);
					
					while(resultado.next())
					{
						mat=resultado.getString("matricula");
					}
					
				}
				System.out.println(mat);
				if(mat.equals(txtMatricula.getText()))
				{
					return true;
				}
				else
				{
				return false;
				}
			}
			
			@FXML protected void Cancelar()
			{
				btnGuardar.setDisable(false);
				btnEliminar.setDisable(true);
				btnModificar.setDisable(true);
				this.limpiar();
			}
			
}
