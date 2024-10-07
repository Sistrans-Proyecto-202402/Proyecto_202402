package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniandes.edu.co.proyecto.entities.*;
import uniandes.edu.co.proyecto.repositories.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CiudadService 
{
    @Autowired
    private CiudadRepository ciudadRepository;

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<Ciudad> findAllCiudades() 
    {
        return ciudadRepository.findAllCiudades();
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Ciudad findCiudadById(Long idCiudad) 
    {
        Optional<Ciudad> ciudad = ciudadRepository.findCiudadById(idCiudad);
        if (ciudad.isEmpty()) 
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La ciudad no se encuentra registrada");
        }
        return ciudad.get();
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void insertCiudad(Ciudad ciudad) 
    {
        try 
        {
            ciudadRepository.insertCiudad(ciudad.getNombre());
        } 
        catch (Exception e) 
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar la ciudad");
        }
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void deleteCiudad(Long idCiudad) 
    {
        if (ciudadRepository.findCiudadById(idCiudad).isEmpty()) 
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La ciudad no se encuentra registrada");
        }
        try 
        {
            ciudadRepository.deleteCiudad(idCiudad);
        } 
        catch (Exception e) 
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la ciudad");
        }
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}