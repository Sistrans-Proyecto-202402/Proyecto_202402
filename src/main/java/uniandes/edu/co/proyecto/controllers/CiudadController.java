package uniandes.edu.co.proyecto.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uniandes.edu.co.proyecto.entities.*;
import uniandes.edu.co.proyecto.services.*;
import java.util.*;

@RestController
@RequestMapping("/ciudades")
public class CiudadController 
{
    @Autowired
    private CiudadService ciudadService;

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Ciudad>> findAllCiudades() 
    {
        List<Ciudad> ciudades = ciudadService.findAllCiudades();
        return ResponseEntity.status(HttpStatus.OK).body(ciudades);
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/{idCiudad}/find")
    public ResponseEntity<Ciudad> findCiudadById(@PathVariable Long idCiudad) 
    {
        Ciudad ciudad = ciudadService.findCiudadById(idCiudad);
        return ResponseEntity.status(HttpStatus.OK).body(ciudad); 
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/insert")
    public ResponseEntity<String> insertCiudad(@RequestBody Ciudad ciudad) 
    {
        ciudadService.insertCiudad(ciudad);
        return ResponseEntity.status(HttpStatus.CREATED).body("La ciudad '" + ciudad.getNombre() + "' ha sido agregada correctamente");
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping("/{idCiudad}/delete")
    public ResponseEntity<String> deleteCiudad(@PathVariable Long idCiudad) 
    {
        ciudadService.deleteCiudad(idCiudad);
        return ResponseEntity.status(HttpStatus.OK).body("La ciudad ha sido eliminada correctamente");
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}