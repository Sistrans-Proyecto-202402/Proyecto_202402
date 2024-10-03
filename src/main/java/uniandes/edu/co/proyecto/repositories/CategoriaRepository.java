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
public interface CategoriaRepository extends JpaRepository<Categoria, Long>
{
    @Query(value = "SELECT * FROM categoria", nativeQuery = true)
    List<Categoria> findAllCategorias();

    @Query(value = "SELECT * FROM categoria WHERE id = :idCategoria", nativeQuery = true)
    Categoria findCategoriaById(Long idCategoria);

//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO categoria (nombre, descripcion, caracteristicas) VALUES (:nombre, :descripcion, :caracteristicas)", nativeQuery = true)
    void insertCategoria(
        @Param("nombre") String nombre,
        @Param("descripcion") String descripcion,
        @Param("caracteristicas") String caracteristicas
    );
//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "UPDATE categoria SET descripcion = :descripcion, caracteristicas = :caracteristicas WHERE id = :idCategoria", nativeQuery = true)
    void updateCategoria(
        @Param("idCategoria") Long idCategoria,
        @Param("descripcion") String descripcion,
        @Param("caracteristicas") String caracteristicas
    );
//--------------------------------------------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM categoria WHERE id = :idCategoria", nativeQuery = true)
    void deleteCategoria(@Param("idCategoria") Long idCategoria);
//--------------------------------------------------------------------------------------------------------------------------------------------
}
