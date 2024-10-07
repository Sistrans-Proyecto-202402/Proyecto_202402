package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uniandes.edu.co.proyecto.repositories.*;
import uniandes.edu.co.proyecto.entities.*;
import java.util.*;


@Service
public class BodegaService 
{
 
    @Autowired
    private BodegaRepository bodegaRepository;
//------------------------------------------------------------------------------------------------------------------------
    public List<Bodega> findAllBodegas() 
    {
        return bodegaRepository.findAllBodegas();
    }
//------------------------------------------------------------------------------------------------------------------------
    public Bodega findBodegaById(Long idBodega) 
    {
        Optional<Bodega> bodega = bodegaRepository.findBodegaById(idBodega);
        if (bodega.isEmpty()) 
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La bodega no se encuentra registrada");
        }
        return bodega.get();
    }
//------------------------------------------------------------------------------------------------------------------------
    public void insertBodega(Bodega bodega) 
    {
        try 
        {
            bodegaRepository.insertBodega(bodega.getNombre(), bodega.getTamano(), bodega.getSucursal().getId());
        } 
        catch (Exception e) 
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar la bodega");
        }
    }
//------------------------------------------------------------------------------------------------------------------------
    public void updateBodega(Long idBodega, Bodega bodega) 
    {
        if (bodegaRepository.findBodegaById(idBodega).isEmpty()) 
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La bodega no se encuentra registrada");
        }
        try 
        {
            bodegaRepository.updateBodega(idBodega, bodega.getNombre(), bodega.getTamano(), bodega.getSucursal().getId());
        } 
        catch (Exception e) 
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la bodega");
        }
    }
//------------------------------------------------------------------------------------------------------------------------
    public void deleteBodega(Long idBodega) 
    {
        if (bodegaRepository.findBodegaById(idBodega).isEmpty()) 
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La bodega no se encuentra registrada");
        }
        try 
        {
            bodegaRepository.deleteBodega(idBodega);
        } 
        catch (Exception e) 
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la bodega");
        }
    }
//------------------------------------------------------------------------------------------------------------------------
}