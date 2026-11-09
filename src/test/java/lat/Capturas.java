package lat;
import lat.vista.*;import lat.servicio.*;import lat.persistencia.*;
import javax.swing.*;import java.awt.*;import java.nio.file.*;import javax.imageio.ImageIO;
/** Recorrido automatizado del prototipo propio, con capturas reales en pantalla virtual. */
public final class Capturas {
 static VentanaPrincipal v;
 static void pausa()throws Exception{Thread.sleep(1000);}
 static void foto(String n)throws Exception{pausa();ImageIO.write(new Robot().createScreenCapture(new Rectangle(0,0,1280,800)),"png",Path.of("AP4/evidencias/"+n+".png").toFile());}
 static JButton boton(Container c,String texto){for(Component x:c.getComponents()){if(x instanceof JButton&&texto.equals(((JButton)x).getText()))return (JButton)x;if(x instanceof Container){JButton b=boton((Container)x,texto);if(b!=null)return b;}}return null;}
 static java.util.List<JTextField> campos(Container c){java.util.List<JTextField> r=new java.util.ArrayList<>();for(Component x:c.getComponents()){if(x instanceof JTextField)r.add((JTextField)x);else if(x instanceof Container)r.addAll(campos((Container)x));}return r;}
 static JTable tabla(Container c){for(Component x:c.getComponents()){if(x instanceof JTable)return (JTable)x;if(x instanceof Container){JTable t=tabla((Container)x);if(t!=null)return t;}}return null;}
 static void cerrarDialogos(){for(Window w:Window.getWindows())if(w instanceof JDialog)w.dispose();}
 public static void main(String[] args)throws Exception{
  SwingUtilities.invokeAndWait(()->{v=new VentanaPrincipal(new OrdenServicio(new MySqlOrdenDao(new Conexion())));v.setVisible(true);boton(v,"Consultar").doClick();});foto("01-consulta-mysql");
  SwingUtilities.invokeAndWait(()->v.getJMenuBar().getMenu(0).setPopupMenuVisible(true));foto("02-menu");
  SwingUtilities.invokeAndWait(()->v.getJMenuBar().getMenu(0).setPopupMenuVisible(false));
  SwingUtilities.invokeLater(()->boton(v,"Nueva orden").doClick());pausa();
  SwingUtilities.invokeAndWait(()->{for(Window w:Window.getWindows())if(w instanceof JDialog&&w.isVisible()){var cs=campos((Container)w);if(cs.size()==3){cs.get(0).setText("PNG");cs.get(1).setText("P16");cs.get(2).setText("DATOS DE PRUEBA - control de aisladores");}}});foto("03-formulario");
  SwingUtilities.invokeLater(()->{for(Window w:Window.getWindows())if(w instanceof JDialog&&w.isVisible()){JButton b=boton((Container)w,"OK");if(b!=null)b.doClick();}});foto("04-alta-confirmada");
  SwingUtilities.invokeAndWait(()->cerrarDialogos());pausa();SwingUtilities.invokeAndWait(()->boton(v,"Consultar").doClick());foto("05-alta-listada");
  SwingUtilities.invokeAndWait(()->{JTable t=tabla(v);t.setRowSelectionInterval(0,0);});SwingUtilities.invokeLater(()->boton(v,"Cambiar estado").doClick());pausa();
  SwingUtilities.invokeAndWait(()->{for(Window w:Window.getWindows())if(w instanceof JDialog&&w.isVisible()){buscarCombo((Container)w);JButton b=boton((Container)w,"OK");if(b!=null)b.doClick();}});pausa();
  SwingUtilities.invokeAndWait(()->boton(v,"Consultar").doClick());foto("06-estado-actualizado");
  SwingUtilities.invokeAndWait(()->{campos(v).get(0).setText("abc");boton(v,"Consultar").doClick();});foto("07-validacion");
  SwingUtilities.invokeAndWait(()->cerrarDialogos());pausa();
  SwingUtilities.invokeAndWait(()->{JTable t=tabla(v);campos(v).get(0).setText(t.getValueAt(0,0).toString());boton(v,"Consultar").doClick();});foto("09-busqueda-id");
  SwingUtilities.invokeAndWait(()->v.dispose());
  SwingUtilities.invokeAndWait(()->{v=new VentanaPrincipal(new OrdenServicio(new MySqlOrdenDao(new Conexion("jdbc:mysql://127.0.0.1:1/lat_ot_ap4?connectTimeout=1000","lat_app",""))));v.setVisible(true);boton(v,"Consultar").doClick();});foto("08-error-conexion");
  SwingUtilities.invokeAndWait(()->{cerrarDialogos();v.dispose();});System.out.println("Recorrido Swing y nueve capturas terminados");System.exit(0);
 }
 static void buscarCombo(Container c){for(Component x:c.getComponents()){if(x instanceof JComboBox)((JComboBox<?>)x).setSelectedItem("FINALIZADA");else if(x instanceof Container)buscarCombo((Container)x);}}
}
