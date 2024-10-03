package uniandes.edu.co.proyecto.repositories;

import uniandes.edu.co.proyecto.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public interface BodegaProductoRepository extends JpaRepository<BodegaProducto, BodegaProductoPK>
{
    @Query(value = "SELECT * FROM bodegaproducto", nativeQuery = true)
    List<BodegaProducto> findAllBodegaProductos();

    @Query(value = "SELECT * FROM bodegaproducto WHERE bodega_id = :idBodega AND producto_id = :idProducto", nativeQuery = true)
    BodegaProducto findBodegaProductoById(
        @Param("idBodega") Long idBodega,
        @Param("idProducto") Long idProducto
    );

//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bodegaproducto (bodega_id, producto_id, existencias, preciopromedio, capacidad) VALUES (:idBodega, :idProducto, :existencias, :precioPromedio, :capacidad)", nativeQuery = true)
    void insertBodegaProducto(
        @Param("idBodega") Long idBodega,
        @Param("idProducto") Long idProducto,
        @Param("existencias") Integer existencias,
        @Param("precioPromedio") Double precioPromedio,
        @Param("capacidad") Integer capacidad
    );
//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "UPDATE bodegaproducto SET existencias = :existencias, preciopromedio = :precioPromedio, capacidad = :capacidad WHERE bodega_id = :idBodega AND producto_id = :idProducto", nativeQuery = true)
    void updateBodegaProducto(
        @Param("idBodega") Long idBodega,
        @Param("idProducto") Long idProducto,
        @Param("existencias") Integer existencias,
        @Param("precioPromedio") Double precioPromedio,
        @Param("capacidad") Integer capacidad
    );
//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bodegaproducto WHERE bodega_id = :idBodega AND producto_id = :idProducto", nativeQuery = true)
    void deleteBodegaProducto(
        @Param("idBodega") Long idBodega,
        @Param("idProducto") Long idProducto
    );
//--------------------------------------------------------------------------------------------------------------------------------------------

}
