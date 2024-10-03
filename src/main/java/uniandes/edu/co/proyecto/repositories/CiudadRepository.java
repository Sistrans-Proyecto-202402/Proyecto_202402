package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.*;
import java.util.*;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long>
{
    @Query(value = "SELECT * FROM ciudad", nativeQuery = true)
    List<Ciudad> findAllCiudades();

    @Query(value = "SELECT * FROM ciudad WHERE id = :idCiudad", nativeQuery = true)
    Ciudad findCiudadById(Long idCiudad);
//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ciudad (nombre) VALUES (:nombre)", nativeQuery = true)
    void insertCiudad(
        @Param ("nombre") String nombre
    );

//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ciudad WHERE id = :idCiudad", nativeQuery = true)
    void deleteCiudad(@Param("idCiudad") Long idCiudad);
//--------------------------------------------------------------------------------------------------------------------------------------------
}

