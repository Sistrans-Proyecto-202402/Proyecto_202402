package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.entities.OrdenProducto;
import uniandes.edu.co.proyecto.entities.OrdenProductoPK;
import java.util.List;

@Repository
public interface OrdenProductoRepository extends JpaRepository<OrdenProducto, OrdenProductoPK> {

    @Query(value = "SELECT * FROM ordenproducto", nativeQuery = true)
    List<OrdenProducto> findAllOrdenesProducto();

    @Query(value = "SELECT * FROM ordenproducto WHERE ordencompra_id = :idOrdenCompra AND producto_id = :idProducto", nativeQuery = true)
    OrdenProducto findOrdenProductoById(@Param("idOrdenCompra") Long idOrdenCompra,
                                        @Param("idProducto") Long idProducto);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ordenproducto (ordencompra_id, producto_id, cantidad, precio) VALUES (:idOrdenCompra, :idProducto, :cantidad, :precio)", nativeQuery = true)
    void insertOrdenProducto(@Param("idOrdenCompra") Long idOrdenCompra,
                             @Param("idProducto") Long idProducto,
                             @Param("cantidad") Integer cantidad,
                             @Param("precio") Double precio);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ordenproducto SET cantidad = :cantidad, precio = :precio WHERE ordencompra_id = :idOrdenCompra AND producto_id = :idProducto", nativeQuery = true)
    void updateOrdenProducto(@Param("idOrdenCompra") Long idOrdenCompra,
                             @Param("idProducto") Long idProducto,
                             @Param("cantidad") Integer cantidad,
                             @Param("precio") Double precio);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ordenproducto WHERE ordencompra_id = :idOrdenCompra AND producto_id = :idProducto", nativeQuery = true)
    void deleteOrdenProducto(@Param("idOrdenCompra") Long idOrdenCompra,
                             @Param("idProducto") Long idProducto);                        
}
