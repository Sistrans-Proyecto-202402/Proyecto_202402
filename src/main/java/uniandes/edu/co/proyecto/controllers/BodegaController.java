package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.services.BodegaService;
import uniandes.edu.co.proyecto.entities.Bodega;
import java.util.List;

@RestController
@RequestMapping("/bodegas")
public class BodegaController 
{
    @Autowired
    private BodegaService bodegaService;

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Bodega>> findAllBodegas() 
    {
        List <Bodega> bodegas = bodegaService.findAllBodegas();
        return ResponseEntity.status(HttpStatus.OK).body(bodegas);
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/{idBodega}/find")
    public ResponseEntity<Bodega> findBodegaById(@PathVariable Long idBodega) 
    {
        Bodega bodega = bodegaService.findBodegaById(idBodega);
        return ResponseEntity.status(HttpStatus.OK).body(bodega);
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/insert")
    public ResponseEntity<String> insertBodega(@RequestBody Bodega bodega) 
    {
        bodegaService.insertBodega(bodega);
        return ResponseEntity.status(HttpStatus.CREATED).body("La bodega '" + bodega.getNombre() + "' ha sido agregada correctamente");
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @PutMapping("/{idBodega}/update")
    public ResponseEntity<String> updateBodega(@PathVariable Long idBodega, @RequestBody Bodega bodega) 
    {
        bodegaService.updateBodega(idBodega, bodega);
        return ResponseEntity.status(HttpStatus.OK).body("La bodega '" + bodega.getNombre() + "' ha sido actualizada correctamente");
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping("/{idBodega}/delete")
    public ResponseEntity<String> deleteBodega(@PathVariable Long idBodega) 
    {
        bodegaService.deleteBodega(idBodega);
        return ResponseEntity.status(HttpStatus.OK).body("La bodega ha sido eliminada correctamente");
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}