package lat.persistencia;
import lat.modelo.Orden;
import java.sql.*;
import java.util.ArrayList;
public final class MySqlOrdenDao implements OrdenDao {
 private final Conexion conexion;
 public MySqlOrdenDao(Conexion c){conexion=c;}
 private static final String INSERT="INSERT INTO orden_trabajo(linea,piquete,descripcion,tipo,estado) VALUES(?,?,?,?,?)";
 private void insertar(Connection c,Orden o) throws SQLException {
  try(PreparedStatement p=c.prepareStatement(INSERT)){
   p.setString(1,o.getLinea());p.setString(2,o.getPiquete());p.setString(3,o.getDescripcion());
   p.setString(4,o.getTipo());p.setString(5,o.getEstado());p.executeUpdate();
  }
 }
 @Override public void insertar(Orden o)throws SQLException {try(Connection c=conexion.abrir()){insertar(c,o);}}
 @Override public ArrayList<Orden> listar(Integer id)throws SQLException {
  ArrayList<Orden> resultado=new ArrayList<>();
  String sql="SELECT id,linea,piquete,descripcion,tipo,estado FROM orden_trabajo"+(id==null?"":" WHERE id=?")+" ORDER BY id DESC";
  try(Connection c=conexion.abrir();PreparedStatement p=c.prepareStatement(sql)){
   if(id!=null)p.setInt(1,id);
   try(ResultSet rs=p.executeQuery()){
    while(rs.next()) resultado.add(Orden.crear(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
   }
  }return resultado;
 }
 @Override public boolean actualizarEstado(int id,String estado)throws SQLException {
  try(Connection c=conexion.abrir();PreparedStatement p=c.prepareStatement("UPDATE orden_trabajo SET estado=? WHERE id=?")){
   p.setString(1,estado);p.setInt(2,id);return p.executeUpdate()>0;
  }
 }
 /** Importación atómica: si falla una fila, se revierte el lote completo. */
 @Override public void importar(ArrayList<Orden> ordenes)throws SQLException {
  try(Connection c=conexion.abrir()){
   c.setAutoCommit(false);
   try{for(Orden o:ordenes)insertar(c,o);c.commit();}
   catch(SQLException|RuntimeException e){try{c.rollback();}catch(SQLException rb){e.addSuppressed(rb);}throw e;}
  }
 }
}
