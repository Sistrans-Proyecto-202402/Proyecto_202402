package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.dtos.ProductoDisponibleSucursalDTO;
import uniandes.edu.co.proyecto.entities.ProductoSucursal;
import uniandes.edu.co.proyecto.entities.ProductoSucursalPK;
import java.util.*;

@Repository
public interface ProductoSucursalRepository extends JpaRepository<ProductoSucursal, ProductoSucursalPK> {

    @Query(value = "SELECT * FROM productosucursal ORDER BY productosucursal.sucursal_id", nativeQuery = true)
    List<ProductoSucursal> findAllProductosSucursales();

    @Query(value = "SELECT * FROM productosucursal WHERE sucursal_id = :idSucursal AND producto_id = :idProducto", nativeQuery = true)
    Optional<ProductoSucursal> findProductoSucursalById(@Param("idSucursal") Long idSucursal, @Param("idProducto") Long idProducto);

    @Query(value = "SELECT ps.sucursal_id FROM productosucursal ps WHERE ps.producto_id = :idProducto", nativeQuery = true)
    List<Long> findSucursalesByProducto(@Param("idProducto") Long idProducto);

    @Query(value = "SELECT DISTINCT s.id AS idSucursal, s.nombre AS nombreSucursal, p.id AS idProducto, p.nombre AS nombreProducto FROM sucursal s INNER JOIN bodega b ON s.id = b.sucursal_id INNER JOIN bodegaproducto bp ON b.id = bp.bodega_id INNER JOIN producto p ON bp.producto_id = p.id WHERE p.id = :idProducto AND bp.existencias > 0 ORDER BY idSucursal", nativeQuery = true)
    List<ProductoDisponibleSucursalDTO> findSucursalesByProductoDisponible(@Param("idProducto") Long idProducto);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO productosucursal (sucursal_id, producto_id, cantidadminima) VALUES (:idSucursal, :idProducto, :cantidadMinima)", nativeQuery = true)
    void insertProductoSucursal(@Param("idSucursal") Long idSucursal,
                                @Param("idProducto") Long idProducto,
                                @Param("cantidadMinima") Integer cantidadMinima);

    @Modifying
    @Transactional
    @Query(value = "UPDATE productosucursal SET cantidadminima = :cantidadMinima WHERE sucursal_id = :idSucursal AND producto_id = :idProducto", nativeQuery = true)
    void updateProductoSucursal(@Param("idSucursal") Long idSucursal,
                                @Param("idProducto") Long idProducto,
                                @Param("cantidadMinima") Integer cantidadMinima);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM productosucursal WHERE sucursal_id = :idSucursal AND producto_id = :idProducto", nativeQuery = true)
    void deleteProductoSucursal(@Param("idSucursal") Long idSucursal, @Param("idProducto") Long idProducto); 
}
