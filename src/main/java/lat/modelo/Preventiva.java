package lat.modelo;
public final class Preventiva extends Orden {
 public Preventiva(int id,String l,String p,String d,String e){super(id,l,p,d,e);}
 @Override public String getTipo(){return "PREVENTIVA";}
}
