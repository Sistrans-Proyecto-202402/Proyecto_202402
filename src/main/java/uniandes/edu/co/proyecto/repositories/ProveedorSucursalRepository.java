package uniandes.edu.co.proyecto.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.ProveedorSucursal;
import uniandes.edu.co.proyecto.entities.ProveedorSucursalPK;

@Repository
public interface ProveedorSucursalRepository extends JpaRepository<ProveedorSucursal, ProveedorSucursalPK> {
    
    @Query(value = "SELECT * FROM proveedorsucursal ORDER BY proveedorsucursal.sucursal_id", nativeQuery = true)
    List<ProveedorSucursal> findAllProveedorSucursales();

    @Query(value = "SELECT * FROM proveedorsucursal WHERE sucursal_id = :sucursalId AND proveedor_id = :proveedorId", nativeQuery = true)
    Optional<ProveedorSucursal> findProveedorSucursalById(@Param("sucursalId") Long sucursalId, @Param("proveedorId") Long proveedorId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO proveedorsucursal (sucursal_id, proveedor_id) VALUES (:sucursalId, :proveedorId)", nativeQuery = true)
    void insertProveedorSucursal(@Param("sucursalId") Long sucursalId, @Param("proveedorId") Long proveedorId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM proveedorsucursal WHERE sucursal_id = :sucursalId AND proveedor_id = :proveedorId", nativeQuery = true)
    void deleteProveedorSucursal(@Param("sucursalId") Long sucursalId, @Param("proveedorId") Long proveedorId);
}
