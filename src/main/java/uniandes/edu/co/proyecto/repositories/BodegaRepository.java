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
public interface BodegaRepository extends JpaRepository<Bodega, Long>
{
    @Query(value = "SELECT * FROM bodega", nativeQuery = true)
    List<Bodega> findAllBodegas();

    @Query(value = "SELECT * FROM bodega WHERE id = :idBodega", nativeQuery = true)
    Bodega findBodegaById(Long idBodega);

    // @Query(value = "SELECT * FROM bodega WHERE sucursal_id = :idSucursal", nativeQuery = true)
    // List<Bodega> findBodegasBySucursal(Long idSucursal);
//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bodega (nombre, tamano, sucursal_id) VALUES (:nombre, :tamano, :idSucursal)", nativeQuery = true)
    void insertBodega(
        @Param("nombre") String nombre,
        @Param("tamano") Integer tamano,
        @Param("idSucursal") Long idSucursal
    );
//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "UPDATE bodega SET nombre = :nombre, tamano = :tamano, sucursal_id = :idSucursal WHERE id = :idBodega", nativeQuery = true)
    void updateBodega(
        @Param("idBodega") Long idBodega,
        @Param("nombre") String nombre,
        @Param("tamano") Integer tamano,
        @Param("idSucursal") Long idSucursal
    );
//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bodega WHERE id = :idBodega", nativeQuery = true)
    void deleteBodega(@Param("idBodega") Long idBodega);
//--------------------------------------------------------------------------------------------------------------------------------------------
}
