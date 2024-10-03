package uniandes.edu.co.proyecto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long>{
    @Query(value = "SELECT * FROM sucursal", nativeQuery = true)
    List<Sucursal> findAllSucursales();

    @Query(value = "SELECT * FROM sucursal WHERE id = :idSucursal",
     nativeQuery = true)
    Sucursal findSucursalById(@Param("idSucursal") Long idSucursal);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO sucursal (nombre, direccion, telefono, tamano, ciudad_id) VALUES (:nombre, :direccion, :telefono, :tamano, :idCiudad)",
     nativeQuery = true)
    void insertSucursal(@Param("nombre") String nombre,
                        @Param("direccion") String direccion,
                        @Param("telefono") Long telefono,
                        @Param("tamano") String tamano,
                        @Param("idCiudad") Long idCiudad);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sucursal SET nombre = :nombre, direccion = :direccion, telefono = :telefono, tamano = :tamano, ciudad_id = :idCiudad WHERE id = :idSucursal",
     nativeQuery = true)
    void updateSucursal(@Param("idSucursal") Long idSucursal,
                        @Param("nombre") String nombre,
                        @Param("direccion") String direccion,
                        @Param("telefono") Long telefono,
                        @Param("tamano") String tamano,
                        @Param("idCiudad") Long idCiudad);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sucursal WHERE id = :idSucursal", nativeQuery = true)
    void deleteSucursal(@Param("idSucursal") Long idSucursal);

    
}
