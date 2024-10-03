package uniandes.edu.co.proyecto.repositories;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.ProveedorProducto;
import uniandes.edu.co.proyecto.entities.ProveedorProductoPK;

@Repository
public interface ProveedorProductoRepository extends JpaRepository<ProveedorProducto, ProveedorProductoPK> {
    @Query(value = "SELECT * FROM proveedorproducto", nativeQuery = true)
    List<ProveedorProducto> findAllProveedorProductos();

    @Query(value = "SELECT * FROM proveedorproducto WHERE producto_id = :productoId AND proveedor_id = :proveedorId", nativeQuery = true)
    ProveedorProducto findProveedorProductoById(@Param("productoId") Long productoId, @Param("proveedorId") Long proveedorId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO proveedorproducto (producto_id, proveedor_id) VALUES (:productoId, :proveedorId)", nativeQuery = true)
    void insertProveedorProducto(@Param("productoId") Long productoId,
                                 @Param("proveedorId") Long proveedorId);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM proveedorproducto WHERE producto_id = :productoId AND proveedor_id = :proveedorId", nativeQuery = true)
    void deleteProveedorProducto(@Param("productoId") Long productoId, @Param("proveedorId") Long proveedorId);
}
    
