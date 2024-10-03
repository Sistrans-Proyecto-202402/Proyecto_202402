package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.entities.ProductoSucursal;
import uniandes.edu.co.proyecto.entities.ProductoSucursalPK;
import java.util.List;

@Repository
public interface ProductoSucursalRepository extends JpaRepository<ProductoSucursal, ProductoSucursalPK> {

    @Query(value = "SELECT * FROM productosucursal", nativeQuery = true)
    List<ProductoSucursal> findAllProductosSucursal();

    @Query(value = "SELECT * FROM productosucursal WHERE sucursal_id = :idSucursal AND producto_id = :idProducto", nativeQuery = true)
    ProductoSucursal findProductoSucursalById(@Param("idSucursal") Long idSucursal,
                                              @Param("idProducto") Long idProducto);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO productosucursal (sucursal_id, producto_id, cantidad) VALUES (:idSucursal, :idProducto, :cantidad)", nativeQuery = true)
    void insertProductoSucursal(@Param("idSucursal") Long idSucursal,
                                @Param("idProducto") Long idProducto,
                                @Param("cantidad") Integer cantidad);

    @Modifying
    @Transactional
    @Query(value = "UPDATE productosucursal SET cantidad = :cantidad WHERE sucursal_id = :idSucursal AND producto_id = :idProducto", nativeQuery = true)
    void updateProductoSucursal(@Param("idSucursal") Long idSucursal,
                                @Param("idProducto") Long idProducto,
                                @Param("cantidad") Integer cantidad);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM productosucursal WHERE sucursal_id = :idSucursal AND producto_id = :idProducto", nativeQuery = true)
    void deleteProductoSucursal(@Param("idSucursal") Long idSucursal,
                                @Param("idProducto") Long idProducto); 
}
