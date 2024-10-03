package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.entities.Producto;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query(value = "SELECT * FROM producto", nativeQuery = true)
    List<Producto> findAllProductos();

    @Query(value = "SELECT * FROM producto WHERE id = :idProducto", nativeQuery = true)  
    Producto findProductoById(@Param("idProducto") Long idProducto);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO producto (nombre, costobodega, preciounitario, presentacion, cantidad, unidadmedida, volumenempaque, pesoempaque, fechaexpiracion, codigobarras, categoria_id) VALUES (:nombre, :costoBodega, :precioUnitario, :presentacion, :cantidad, :unidadMedida, :volumenEmpaque, :pesoEmpaque, :fechaVencimiento, :codigoBarras, :idCategoria)", nativeQuery = true)
    void insertProducto(@Param("nombre") String nombre,
                        @Param("costoBodega") double costoBodega,
                        @Param("precioUnitario") double precioUnitario,
                        @Param("presentacion") String presentacion,
                        @Param("cantidad") Integer cantidad,
                        @Param("unidadMedida") String unidadMedida,
                        @Param("volumenEmpaque") double volumenEmpaque,
                        @Param("pesoEmpaque") double pesoEmpaque,
                        @Param("fechaVencimiento") LocalDate fechaVencimiento,
                        @Param("codigoBarras") String codigoBarras,
                        @Param("idCategoria") Long idCategoria);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE producto SET nombre = :nombre, costobodega = :costoBodega, preciounitario = :precioUnitario, presentacion = :presentacion, cantidad = :cantidad, unidadmedida = :unidadMedida, volumenempaque = :volumenEmpaque, pesoempaque = :pesoEmpaque, categoria_id = :idCategoria WHERE id = :idProducto", nativeQuery = true)
    void updateProducto(@Param("idProducto") Long idProducto,
                        @Param("nombre") String nombre,
                        @Param("costoBodega") double costoBodega,
                        @Param("precioUnitario") double precioUnitario,
                        @Param("presentacion") String presentacion,
                        @Param("cantidad") Integer cantidad,
                        @Param("unidadMedida") String unidadMedida,
                        @Param("volumenEmpaque") double volumenEmpaque,
                        @Param("pesoEmpaque") double pesoEmpaque,
                        @Param("idCategoria") Long idCategoria);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM producto WHERE id = :idProducto", nativeQuery = true)
    void deleteProducto(@Param("idProducto") Long idProducto);
}
