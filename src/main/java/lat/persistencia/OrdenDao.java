package lat.persistencia;
import lat.modelo.Orden;
import java.sql.SQLException;
import java.util.ArrayList;
/** DAO: contrato de acceso a datos; permite sustituir la implementación. */
public interface OrdenDao {
 void insertar(Orden o) throws SQLException;
 ArrayList<Orden> listar(Integer id) throws SQLException;
 boolean actualizarEstado(int id,String estado) throws SQLException;
 void importar(ArrayList<Orden> ordenes) throws SQLException;
}
