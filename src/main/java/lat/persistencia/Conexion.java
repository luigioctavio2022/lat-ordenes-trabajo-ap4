package lat.persistencia;
import java.sql.*;
public final class Conexion {
 private final String url,usuario,clave;
 public Conexion(){this(System.getenv().getOrDefault("LAT_DB_URL","jdbc:mysql://localhost:3306/lat_ot_ap4?connectTimeout=4000&socketTimeout=8000"),System.getenv().getOrDefault("LAT_DB_USER","lat_app"),System.getenv().getOrDefault("LAT_DB_PASSWORD",""));}
 public Conexion(String url,String usuario,String clave){this.url=url;this.usuario=usuario;this.clave=clave;}
 public Connection abrir() throws SQLException{return DriverManager.getConnection(url,usuario,clave);}
}
