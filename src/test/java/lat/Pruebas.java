package lat;
import lat.modelo.*;import lat.persistencia.*;import lat.servicio.*;
import java.nio.file.*;import java.sql.*;import java.util.*;
public final class Pruebas {
 static int pasadas=0;
 static void ok(boolean b,String nombre){if(!b)throw new AssertionError(nombre);System.out.println("APROBADO | "+nombre);pasadas++;}
 interface Accion{void ejecutar()throws Exception;}
 static void falla(Class<? extends Throwable> tipo,Accion a,String n)throws Exception{try{a.ejecutar();}catch(Throwable t){if(tipo.isInstance(t)){ok(true,n);return;}throw t;}throw new AssertionError(n);}
 public static void main(String[] args)throws Exception{
  Orden o=Orden.crear(0,"PNG","P16","Inspección de aisladores","PREVENTIVA","PENDIENTE");
  ok(o instanceof Preventiva&&o.getTipo().equals("PREVENTIVA"),"Clase abstracta y polimorfismo preventiva");
  ok(Orden.crear(0,"PNG","P1","Reparación","CORRECTIVA","PENDIENTE") instanceof Correctiva,"Polimorfismo correctiva");
  falla(IllegalArgumentException.class,()->Orden.crear(0," ","P1","x","PREVENTIVA","PENDIENTE"),"Campo vacío rechazado");
  falla(IllegalArgumentException.class,()->Orden.crear(0,"a".repeat(101),"P1","x","PREVENTIVA","PENDIENTE"),"Límite 101 caracteres rechazado");
  ok(Orden.crear(0,"a".repeat(100),"P1","x","PREVENTIVA","PENDIENTE").getLinea().length()==100,"Límite 100 caracteres aceptado");
  falla(IllegalArgumentException.class,()->Orden.crear(0,"PNG","P1","x","OTRO","PENDIENTE"),"Tipo inválido rechazado");
  ok(o.fila().length==6&&Orden.ESTADOS.length==3,"Arreglos de fila y estados");
  ArchivoOrdenes archivos=new ArchivoOrdenes();Path temporal=Files.createTempFile("lat-test-",".lat");
  ArrayList<Orden> lista=new ArrayList<>();lista.add(o);lista.add(Orden.crear(0,"LAT de prueba","P2","Acentos, ñ y comillas: ' ;","CORRECTIVA","EN_PROCESO"));
  archivos.guardar(temporal,lista);ArrayList<Orden> recuperada=archivos.leer(temporal);
  ok(recuperada.size()==2&&recuperada.get(1).getDescripcion().equals(lista.get(1).getDescripcion()),"Archivo guarda y recupera datos Unicode");
  Files.write(temporal,new byte[]{1,2,3});falla(java.io.IOException.class,()->archivos.leer(temporal),"Archivo truncado rechazado");Files.delete(temporal);
  Conexion c=new Conexion();OrdenDao dao=new MySqlOrdenDao(c);OrdenServicio s=new OrdenServicio(dao);
  falla(IllegalArgumentException.class,()->s.buscar("abc"),"Búsqueda no numérica rechazada");
  falla(IllegalArgumentException.class,()->s.buscar("999999999999"),"Desbordamiento de ID rechazado");
  int inicial=s.buscar("").size();s.crear("PNG","P16","DATOS DE PRUEBA - inspección","PREVENTIVA");
  ArrayList<Orden> filas=s.buscar("");int id=filas.get(0).getId();
  ok(filas.size()==inicial+1,"MySQL inserta y lista orden");
  ok(s.buscar(""+id).size()==1,"MySQL búsqueda por ID");
  s.cambiar(id,"EN_PROCESO");ok(s.buscar(""+id).get(0).getEstado().equals("EN_PROCESO"),"MySQL actualiza estado");
  ok(new OrdenServicio(new MySqlOrdenDao(new Conexion())).buscar(""+id).get(0).getEstado().equals("EN_PROCESO"),"Nueva conexión conserva persistencia");
  ok(s.buscar("2147483647").isEmpty(),"ID inexistente devuelve lista vacía");
  falla(IllegalArgumentException.class,()->s.cambiar(2147483647,"FINALIZADA"),"Actualización de ID inexistente controlada");
  falla(IllegalArgumentException.class,()->s.cambiar(id,"OTRO"),"Estado inválido rechazado");
  s.crear("Prueba ' ; --","P1","Texto tratado como dato","CORRECTIVA");ok(s.buscar("").get(0).getLinea().equals("Prueba ' ; --"),"PreparedStatement conserva caracteres SQL como datos");
  Path respaldo=Files.createTempFile("lat-db-",".lat");archivos.guardar(respaldo,lista);int antes=s.buscar("").size();s.importar(archivos.leer(respaldo));ok(s.buscar("").size()==antes+2,"Archivo recuperado en MySQL mediante lote");Files.delete(respaldo);
  ArrayList<Orden> lote=new ArrayList<>();lote.add(o);lote.add(new Orden(0,"PNG","P2","x","PENDIENTE") {public String getTipo(){return "INVALIDO";}});
  int cantidad=s.buscar("").size();falla(SQLException.class,()->s.importar(lote),"Error SQL en lote propagado");ok(s.buscar("").size()==cantidad,"Rollback revierte primer INSERT del lote");
  OrdenDao sinConexion=new MySqlOrdenDao(new Conexion("jdbc:mysql://127.0.0.1:1/lat_ot_ap4?connectTimeout=1000","lat_app",""));
  falla(SQLException.class,()->sinConexion.listar(null),"Conexión inaccesible produce SQLException controlable");
  System.out.println("RESULTADO: "+pasadas+" aprobadas, 0 fallidas");
 }
}
