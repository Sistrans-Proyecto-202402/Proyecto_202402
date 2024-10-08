package uniandes.edu.co.proyecto.repositories;

import java.util.*;

import uniandes.edu.co.proyecto.dtos.BodegaProductoDTO;
import uniandes.edu.co.proyecto.dtos.IndiceOcupacionDTO;
import uniandes.edu.co.proyecto.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BodegaProductoRepository extends JpaRepository<BodegaProducto, BodegaProductoPK> {
    
    @Query(value = "SELECT * FROM bodegaproducto ORDER BY bodegaproducto.bodega_id", nativeQuery = true)
    List<BodegaProducto> findAllBodegasProductos();

    @Query(value = "SELECT * FROM bodegaproducto WHERE bodega_id = :idBodega AND producto_id = :idProducto", nativeQuery = true)
    Optional<BodegaProducto> findBodegaProductoById(@Param("idBodega") Long idBodega, @Param("idProducto") Long idProducto);

    @Query(value = "SELECT b.id AS idBodega, b.nombre AS nombreBodega, p.id AS idProducto, p.nombre AS nombreProducto, (bp.existencias / bp.capacidad) * 100 AS indiceOcupacion FROM bodegaproducto bp INNER JOIN bodega b ON b.id = bp.bodega_id INNER JOIN producto p ON p.id = bp.producto_id WHERE bp.producto_id = :idProducto", nativeQuery = true)
    List<IndiceOcupacionDTO> findOcupacionBodegasByProducto(@Param("idProducto") Long idProducto);


    @Query(value = "SELECT bp.bodega_id AS idBodega, b.nombre AS nombreBodega, bp.producto_id AS idProducto, p.nombre AS nombreProducto, bp.existencias, bp.preciopromedio, bp.capacidad, ps.cantidadminima FROM bodegaproducto bp INNER JOIN bodega b ON b.id = bp.bodega_id INNER JOIN productosucursal ps ON ps.producto_id = bp.producto_id AND ps.sucursal_id = b.sucursal_id INNER JOIN producto p ON p.id = bp.producto_id WHERE bp.bodega_id = :idBodega AND b.sucursal_id = :idSucursal ORDER BY p.id", nativeQuery = true)
    List<BodegaProductoDTO> findProductosByBodega(@Param("idBodega") Long idBodega, @Param("idSucursal") Long idSucursal);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bodegaproducto (bodega_id, producto_id, existencias, preciopromedio, capacidad) VALUES (:idBodega, :idProducto, :existencias, :precioPromedio, :capacidad)", nativeQuery = true)
    void insertBodegaProducto(@Param("idBodega") Long idBodega,
                              @Param("idProducto") Long idProducto,
                              @Param("existencias") Integer existencias,
                              @Param("precioPromedio") Double precioPromedio,
                              @Param("capacidad") Integer capacidad);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bodegaproducto SET existencias = :existencias, preciopromedio = :precioPromedio WHERE bodega_id = :idBodega AND producto_id = :idProducto", nativeQuery = true)
    void updateBodegaProducto(@Param("idBodega") Long idBodega,
                              @Param("idProducto") Long idProducto,
                              @Param("existencias") Integer existencias,
                              @Param("precioPromedio") Double precioPromedio);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bodegaproducto SET capacidad = :capacidad WHERE bodega_id = :idBodega AND producto_id = :idProducto", nativeQuery = true)
    void updateCapacidadBodegaProducto(@Param("idBodega") Long idBodega,
                                       @Param("idProducto") Long idProducto,
                                       @Param("capacidad") Integer capacidad);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bodegaproducto WHERE bodega_id = :idBodega AND producto_id = :idProducto", nativeQuery = true)
    void deleteBodegaProducto(@Param("idBodega") Long idBodega, @Param("idProducto") Long idProducto);
}
