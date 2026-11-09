package lat.servicio;
import lat.modelo.Orden;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
/** Archivo binario versionado; no usa deserialización de objetos Java. */
public final class ArchivoOrdenes {
 public void guardar(Path destino,ArrayList<Orden> ordenes)throws IOException {
  if(ordenes.size()>10000)throw new IOException("El archivo admite hasta 10000 órdenes");
  Path absoluta=destino.toAbsolutePath();Path temporal=Files.createTempFile(absoluta.getParent(),"lat-",".tmp");
  try {
   try(DataOutputStream d=new DataOutputStream(Files.newOutputStream(temporal))){
    d.writeUTF("LAT-OT-1");d.writeInt(ordenes.size());
    for(Orden o:ordenes){d.writeUTF(o.getLinea());d.writeUTF(o.getPiquete());d.writeUTF(o.getDescripcion());d.writeUTF(o.getTipo());d.writeUTF(o.getEstado());}
   }
   Files.move(temporal,absoluta,StandardCopyOption.REPLACE_EXISTING);
  }finally{Files.deleteIfExists(temporal);}
 }
 public ArrayList<Orden> leer(Path archivo)throws IOException {
  if(Files.size(archivo)>32_000_000)throw new IOException("Archivo demasiado grande");
  ArrayList<Orden> resultado=new ArrayList<>();
  try(DataInputStream d=new DataInputStream(Files.newInputStream(archivo))){
   if(!"LAT-OT-1".equals(d.readUTF()))throw new IOException("Formato de archivo incompatible");
   int n=d.readInt();if(n<0||n>10000)throw new IOException("Cantidad de registros inválida");
   for(int i=0;i<n;i++) resultado.add(Orden.crear(0,d.readUTF(),d.readUTF(),d.readUTF(),d.readUTF(),d.readUTF()));
   if(d.read()!=-1)throw new IOException("Contenido adicional inválido");
  }catch(IllegalArgumentException e){throw new IOException("Archivo con datos inválidos",e);}
  return resultado;
 }
}
