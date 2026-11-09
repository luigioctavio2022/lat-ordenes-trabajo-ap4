package lat.modelo;
/** Entidad abstracta: invariantes comunes y tipo polimórfico. */
public abstract class Orden {
 private final int id;
 private final String linea, piquete, descripcion, estado;
 public static final String[] ESTADOS = {"PENDIENTE", "EN_PROCESO", "FINALIZADA"};
 protected Orden(int id, String linea, String piquete, String descripcion, String estado) {
  if (id < 0) throw new IllegalArgumentException("ID inválido");
  this.id=id; this.linea=texto(linea,100,"Línea");
  this.piquete=texto(piquete,30,"Piquete"); this.descripcion=texto(descripcion,500,"Descripción");
  boolean valido=false; for(String valor:ESTADOS) if(valor.equals(estado)) valido=true;
  if(!valido) throw new IllegalArgumentException("Estado inválido"); this.estado=estado;
 }
 private static String texto(String s,int max,String campo) {
  if(s==null || s.isBlank() || s.trim().codePointCount(0,s.trim().length())>max)
   throw new IllegalArgumentException(campo+": complete entre 1 y "+max+" caracteres");
  return s.trim();
 }
 public int getId(){return id;} public String getLinea(){return linea;}
 public String getPiquete(){return piquete;} public String getDescripcion(){return descripcion;}
 public String getEstado(){return estado;} public abstract String getTipo();
 public Object[] fila(){return new Object[]{id,linea,piquete,descripcion,getTipo(),estado};}
 public static Orden crear(int id,String l,String p,String d,String tipo,String estado){
  if("PREVENTIVA".equals(tipo)) return new Preventiva(id,l,p,d,estado);
  if("CORRECTIVA".equals(tipo)) return new Correctiva(id,l,p,d,estado);
  throw new IllegalArgumentException("Tipo inválido");
 }
}
