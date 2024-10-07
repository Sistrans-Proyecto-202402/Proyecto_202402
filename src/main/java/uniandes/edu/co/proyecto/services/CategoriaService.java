package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniandes.edu.co.proyecto.repositories.*;
import uniandes.edu.co.proyecto.entities.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoriaService 
{
    @Autowired
    private CategoriaRepository categoriaRepository;
    
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<Categoria> findAllCategorias() 
    {
        return categoriaRepository.findAllCategorias();
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Categoria findCategoriaById(Long idCategoria) 
    {
        Optional<Categoria> categoria = categoriaRepository.findCategoriaById(idCategoria);
        if (categoria.isEmpty()) 
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoria no se encuentra registrada");
        }
        return categoria.get();
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void insertCategoria(Categoria categoria) 
    {
        try 
        {
            categoriaRepository.insertCategoria(categoria.getNombre(), categoria.getDescripcion(), categoria.getCaracteristicas());
        } 
        catch (Exception e) 
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar la categoria");
        }
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void updateCategoria(Long idCategoria, Categoria categoria) 
    {
        if (categoriaRepository.findCategoriaById(idCategoria).isEmpty()) 
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoria no se encuentra registrada");
        }
        try 
        {
            categoriaRepository.updateCategoria(idCategoria, categoria.getDescripcion(), categoria.getCaracteristicas());
        } 
        catch (Exception e) 
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la categoria");
        }
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void deleteCategoria(Long idCategoria) 
    {
        if (categoriaRepository.findCategoriaById(idCategoria).isEmpty()) 
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoria no se encuentra registrada");
        }
        try 
        {
            categoriaRepository.deleteCategoria(idCategoria);
        } 
        catch (Exception e) 
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la categoria");
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
}