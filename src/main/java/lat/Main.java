package lat;
import javax.swing.*;
import lat.persistencia.*;
import lat.servicio.OrdenServicio;
import lat.vista.VentanaPrincipal;
public final class Main {
 public static void main(String[] args){SwingUtilities.invokeLater(()->new VentanaPrincipal(new OrdenServicio(new MySqlOrdenDao(new Conexion()))).setVisible(true));}
}
