package lat.vista;
import lat.modelo.Orden;
import lat.servicio.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.logging.*;
public final class VentanaPrincipal extends JFrame {
 private final OrdenServicio servicio;private final ArchivoOrdenes archivos=new ArchivoOrdenes();
 private final String[] columnas={"ID","Línea","Piquete","Descripción","Tipo","Estado"};
 private final DefaultTableModel modelo=new DefaultTableModel(columnas,0){@Override public boolean isCellEditable(int r,int c){return false;}};
 private final JTable tabla=new JTable(modelo);private final JTextField filtro=new JTextField(9);
 private final JLabel estado=new JLabel("Listo");private boolean ocupado=false;
 private final JPanel acciones=new JPanel();private final JMenu menu=new JMenu("Opciones");
 public VentanaPrincipal(OrdenServicio servicio){
  super("Órdenes de trabajo LAT 132 kV | AP4");this.servicio=servicio;
  setDefaultCloseOperation(DISPOSE_ON_CLOSE);setSize(1100,600);setLocationRelativeTo(null);
  JMenuBar barra=new JMenuBar();barra.add(menu);setJMenuBar(barra);
  boton("Nueva orden",this::crear);acciones.add(new JLabel("ID (vacío = todas)"));acciones.add(filtro);
  boton("Consultar",this::consultar);boton("Cambiar estado",this::cambiar);
  item("Guardar archivo",this::guardar);item("Recuperar archivo",this::recuperar);menu.addSeparator();item("Salir",this::dispose);
  add(acciones,BorderLayout.NORTH);add(new JScrollPane(tabla),BorderLayout.CENTER);add(estado,BorderLayout.SOUTH);
  tabla.setRowHeight(28);tabla.getColumnModel().getColumn(3).setPreferredWidth(350);
 }
 private void item(String nombre,Runnable accion){JMenuItem i=new JMenuItem(nombre);i.addActionListener(e->{if(!ocupado)accion.run();});menu.add(i);}
 private void boton(String nombre,Runnable accion){JButton b=new JButton(nombre);b.addActionListener(e->{if(!ocupado)accion.run();});acciones.add(b);item(nombre,accion);}
 private void mostrar(ArrayList<Orden> lista){modelo.setRowCount(0);for(Orden o:lista)modelo.addRow(o.fila());estado.setText(lista.size()+" órdenes consultadas en MySQL");}
 private <T> void ejecutar(Callable<T> tarea,java.util.function.Consumer<T> alTerminar){
  ocupado=true;menu.setEnabled(false);for(Component c:acciones.getComponents())c.setEnabled(false);estado.setText("Procesando...");
  new SwingWorker<T,Void>(){
   protected T doInBackground()throws Exception{return tarea.call();}
   protected void done(){try{alTerminar.accept(get());}
    catch(InterruptedException e){Thread.currentThread().interrupt();aviso(e);}
    catch(ExecutionException e){aviso(e.getCause());}
    finally{ocupado=false;menu.setEnabled(true);for(Component c:acciones.getComponents())c.setEnabled(true);}
   }
  }.execute();
 }
 private void aviso(Throwable e){
  Logger.getLogger("lat").log(Level.WARNING,"Operación fallida",e);estado.setText("Operación no completada");
  String mensaje=e instanceof IllegalArgumentException?e.getMessage():e instanceof java.io.IOException?"No se pudo leer o guardar el archivo. Revise formato y permisos.":"No se pudo completar la operación en MySQL. Revise el servicio, el controlador y las credenciales; consulte el registro técnico.";
  JOptionPane.showMessageDialog(this,mensaje,"Aviso",JOptionPane.WARNING_MESSAGE);
 }
 private void consultar(){String id=filtro.getText();ejecutar(()->servicio.buscar(id),this::mostrar);}
 private void crear(){
  JTextField l=new JTextField(),p=new JTextField(),d=new JTextField();String[] tipos={"PREVENTIVA","CORRECTIVA"};JComboBox<String> t=new JComboBox<>(tipos);
  JPanel form=new JPanel(new GridLayout(0,2,8,8));form.add(new JLabel("Línea"));form.add(l);form.add(new JLabel("Piquete"));form.add(p);form.add(new JLabel("Descripción"));form.add(d);form.add(new JLabel("Tipo"));form.add(t);
  if(JOptionPane.showConfirmDialog(this,form,"Nueva orden",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION)return;
  String linea=l.getText(),piquete=p.getText(),desc=d.getText(),tipo=(String)t.getSelectedItem();
  ejecutar(()->{servicio.crear(linea,piquete,desc,tipo);return true;},ok->{estado.setText("Orden guardada en MySQL. Consulte para actualizar el listado.");JOptionPane.showMessageDialog(this,"Orden registrada correctamente");});
 }
 private void cambiar(){
  int fila=tabla.getSelectedRow();if(fila<0){aviso(new IllegalArgumentException("Seleccione una orden"));return;}
  int id=(Integer)modelo.getValueAt(tabla.convertRowIndexToModel(fila),0);
  String nuevo=(String)JOptionPane.showInputDialog(this,"Seleccione estado","Orden "+id,JOptionPane.QUESTION_MESSAGE,null,Orden.ESTADOS,Orden.ESTADOS[0]);
  if(nuevo!=null)ejecutar(()->{servicio.cambiar(id,nuevo);return true;},ok->estado.setText("Estado actualizado en MySQL. Consulte para actualizar el listado."));
 }
 private void guardar(){
  JFileChooser c=new JFileChooser();c.setSelectedFile(new java.io.File("ordenes.lat"));
  if(c.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)return;
  Path ruta=c.getSelectedFile().toPath();
  if(java.nio.file.Files.exists(ruta)&&JOptionPane.showConfirmDialog(this,"¿Reemplazar archivo existente?","Confirmar",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
  ejecutar(()->{ArrayList<Orden> lista=servicio.buscar("");archivos.guardar(ruta,lista);return lista.size();},n->estado.setText(n+" órdenes guardadas en archivo"));
 }
 private void recuperar(){
  JFileChooser c=new JFileChooser();if(c.showOpenDialog(this)!=JFileChooser.APPROVE_OPTION)return;Path ruta=c.getSelectedFile().toPath();
  ejecutar(()->archivos.leer(ruta),lista->{
   if(JOptionPane.showConfirmDialog(this,"Se agregarán "+lista.size()+" órdenes con ID nuevos. Repetir la recuperación genera duplicados. ¿Continuar?","Recuperar archivo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
    // Iniciar después de que finalice el worker de lectura y se libere el estado ocupado.
    SwingUtilities.invokeLater(()->ejecutar(()->{servicio.importar(lista);return lista.size();},n->estado.setText(n+" órdenes recuperadas en MySQL con ID nuevos")));
   }
  });
 }
}
