package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.dtos.DocumentoIngresoDTO;
import uniandes.edu.co.proyecto.dtos.ProductosRequierenOrdenDTO;
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

    @Query(value = "SELECT p.id AS idProducto, p.nombre AS nombreProducto, b.id AS idBodega, b.nombre AS nombreBodega, s.id AS idSucursal, s.nombre AS nombreSucursal, prov.id AS idProveedor, prov.nombre AS nombreProveedor, bp.existencias, ps.cantidadminima FROM producto p INNER JOIN bodegaproducto bp ON p.id = bp.producto_id INNER JOIN bodega b ON bp.bodega_id = b.id INNER JOIN sucursal s ON b.sucursal_id = s.id INNER JOIN productosucursal ps ON ps.producto_id = p.id AND ps.sucursal_id = s.id INNER JOIN proveedorproducto pp ON p.id = pp.producto_id INNER JOIN proveedor prov ON pp.proveedor_id = prov.id WHERE bp.existencias < ps.cantidadminima ORDER BY p.id, b.id, s.id", nativeQuery = true)
    List<ProductosRequierenOrdenDTO> findProductosRequierenOrdenCompra();

    @Query(value = "SELECT op.id, op.fecharecepcion AS fechaRecepcion, p.nombre AS nombreProveedor FROM ordencompra op INNER JOIN proveedor p ON p.id = op.proveedor_id WHERE sucursal_id = :idSucursal AND bodega_id = :idBodega AND fecharecepcion >= :fechaLimite ORDER BY op.id", nativeQuery = true)
    List<DocumentoIngresoDTO> findDocumentosIngresoProductosByBodega(@Param("idSucursal") Long idSucursal,
                                                    @Param("idBodega") Long idBodega,
                                                    @Param("fechaLimite") LocalDate fechaLimite);

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
