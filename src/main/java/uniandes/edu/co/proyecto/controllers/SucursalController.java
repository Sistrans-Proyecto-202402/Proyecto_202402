package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.entities.Sucursal;
import uniandes.edu.co.proyecto.services.SucursalService;

import java.util.List;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<Sucursal>> findAllSucursales() {
        List<Sucursal> sucursales = sucursalService.findAllSucursales();
        return ResponseEntity.status(HttpStatus.OK).body(sucursales);
    }

    @GetMapping("/{idSucursal}/find")
    public ResponseEntity<Sucursal> findSucursalById(@PathVariable Long idSucursal) {
        Sucursal sucursal = sucursalService.findSucursalById(idSucursal);
        return ResponseEntity.status(HttpStatus.OK).body(sucursal);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertSucursal(@RequestBody Sucursal sucursal) {
        sucursalService.insertSucursal(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body("La sucursal '" + sucursal.getNombre() + "' ha sido agregada correctamente");
    }

    @PutMapping("/{idSucursal}/update")
    public ResponseEntity<String> updateSucursal(@PathVariable Long idSucursal, @RequestBody Sucursal sucursal) {
        sucursalService.updateSucursal(idSucursal, sucursal);
        return ResponseEntity.status(HttpStatus.OK).body("La sucursal '" + sucursal.getNombre() + "' ha sido actualizada correctamente");
    }

    @DeleteMapping("/{idSucursal}/delete")
    public ResponseEntity<String> deleteSucursal(@PathVariable Long idSucursal) {
        sucursalService.deleteSucursal(idSucursal);
        return ResponseEntity.status(HttpStatus.OK).body("La sucursal ha sido eliminada correctamente");
    }
}
