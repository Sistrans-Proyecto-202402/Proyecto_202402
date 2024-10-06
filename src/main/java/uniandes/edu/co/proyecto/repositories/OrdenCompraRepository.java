package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.entities.OrdenCompra;
import java.time.LocalDate;
import java.util.*;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {

    @Query(value = "SELECT * FROM ordencompra ORDER BY ordencompra.id", nativeQuery = true)
    List<OrdenCompra> findAllOrdenesCompra();

    @Query(value = "SELECT * FROM ordencompra WHERE id = :idOrdenCompra", nativeQuery = true)
    Optional<OrdenCompra> findOrdenCompraById(@Param("idOrdenCompra") Long idOrdenCompra);

    @Query(value = "SELECT id FROM ordencompra WHERE fechaCreacion = :fechaCreacion AND fechaEntrega = :fechaEntrega AND estado = :estado AND proveedor_id = :idProveedor AND sucursal_id = :idSucursal AND bodega_id = :idBodega", nativeQuery = true)
    Optional<Long> findOrdenCompraByAttributes(@Param("fechaCreacion") LocalDate fechaCreacion,
                                               @Param("fechaEntrega") LocalDate fechaEntrega,
                                               @Param("estado") String estado,
                                               @Param("idProveedor") Long idProveedor,
                                               @Param("idSucursal") Long idSucursal,
                                               @Param("idBodega") Long idBodega);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ordencompra (fechacreacion, fechaentrega, estado, proveedor_id, sucursal_id, bodega_id) VALUES (:fechaCreacion, :fechaEntrega, :estado, :idProveedor, :idSucursal, :idBodega)", nativeQuery = true)
    void insertOrdenCompra(@Param("fechaCreacion") LocalDate fechaCreacion,
                           @Param("fechaEntrega") LocalDate fechaEntrega,
                           @Param("estado") String estado,
                           @Param("idProveedor") Long idProveedor,
                           @Param("idSucursal") Long idSucursal,
                           @Param("idBodega") Long idBodega);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ordencompra SET fechaentrega = :fechaEntrega WHERE id = :idOrdenCompra", nativeQuery = true)
    void updateOrdenCompra(@Param("idOrdenCompra") Long idOrdenCompra,
                           @Param("fechaEntrega") LocalDate fechaEntrega);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ordencompra SET estado = :estado, fecharecepcion = :fechaRecepcion WHERE id = :idOrdenCompra", nativeQuery = true)
    void updateEstadoOrdenCompra(@Param("idOrdenCompra") Long idOrdenCompra,
                                 @Param("estado") String estado,
                                 @Param("fechaRecepcion") LocalDate fechaRecepcion);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ordencompra WHERE id = :idOrdenCompra", nativeQuery = true)
    void deleteOrdenCompra(@Param("idOrdenCompra") Long idOrdenCompra);
}
