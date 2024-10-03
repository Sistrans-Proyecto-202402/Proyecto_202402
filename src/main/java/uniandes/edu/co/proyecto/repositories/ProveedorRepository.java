package uniandes.edu.co.proyecto.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query(value = "SELECT * FROM proveedor", nativeQuery = true)
    List<Proveedor> findAllProveedores();

    @Query(value = "SELECT * FROM proveedor WHERE id = :idProveedor", nativeQuery = true)
    Proveedor findProveedorById(@Param("idProveedor") Long idProveedor);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO proveedor (nit, nombre, direccion, nombrecontacto, telefonocontacto) VALUES (:nit, :nombre, :direccion, :nombreContacto, :telefonoContacto)", nativeQuery = true)
    void insertProveedor(@Param("nit") Long nit,
                         @Param("nombre") String nombre,
                         @Param("direccion") String direccion,
                         @Param("nombreContacto") String nombreContacto,
                         @Param("telefonoContacto") Long telefonoContacto);

    @Modifying
    @Transactional
    @Query(value = "UPDATE proveedor SET nombre = :nombre, direccion = :direccion, nombrecontacto = :nombreContacto, telefonocontacto = :telefonoContacto WHERE id = :idProveedor", nativeQuery = true)
    void updateProveedor(@Param("idProveedor") Long idProveedor,
                         @Param("nombre") String nombre,
                         @Param("direccion") String direccion,
                         @Param("nombreContacto") String nombreContacto,
                         @Param("telefonoContacto") Long telefonoContacto);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM proveedor WHERE id = :idProveedor", nativeQuery = true)
    void deleteProveedor(@Param("idProveedor") Long idProveedor);
}


    

