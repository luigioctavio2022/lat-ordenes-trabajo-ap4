package lat.servicio;
import lat.modelo.Orden;
import lat.persistencia.OrdenDao;
import java.sql.SQLException;
import java.util.ArrayList;
public final class OrdenServicio {
 private final OrdenDao dao;
 public OrdenServicio(OrdenDao dao){this.dao=dao;}
 public void crear(String l,String p,String d,String tipo)throws SQLException{dao.insertar(Orden.crear(0,l,p,d,tipo,"PENDIENTE"));}
 public ArrayList<Orden> buscar(String filtro)throws SQLException {
  Integer id=null;
  if(!filtro.isBlank())try{id=Integer.valueOf(filtro.trim());if(id<=0)throw new NumberFormatException();}
  catch(NumberFormatException e){throw new IllegalArgumentException("Ingrese un ID entero positivo de hasta 2147483647");}
  return dao.listar(id);
 }
 public void cambiar(int id,String estado)throws SQLException {
  boolean valido=false;for(String e:Orden.ESTADOS)if(e.equals(estado))valido=true;
  if(id<=0||!valido)throw new IllegalArgumentException("ID o estado inválido");
  if(!dao.actualizarEstado(id,estado))throw new IllegalArgumentException("La orden ya no existe");
 }
 public void importar(ArrayList<Orden> ordenes)throws SQLException{dao.importar(ordenes);}
}
