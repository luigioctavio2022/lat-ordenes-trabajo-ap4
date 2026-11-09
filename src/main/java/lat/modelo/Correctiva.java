package lat.modelo;
public final class Correctiva extends Orden {
 public Correctiva(int id,String l,String p,String d,String e){super(id,l,p,d,e);}
 @Override public String getTipo(){return "CORRECTIVA";}
}
