package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.entities.Proveedor;
import uniandes.edu.co.proyecto.services.ProveedorService;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedor>> findAllProveedores() {
        List<Proveedor> proveedores = proveedorService.findAllProveedores();
        return ResponseEntity.status(HttpStatus.OK).body(proveedores);
    }

    @GetMapping("/{idProveedor}/find")
    public ResponseEntity<Proveedor> findProveedorById(@PathVariable Long idProveedor) {
        Proveedor proveedor = proveedorService.findProveedorById(idProveedor);
        return ResponseEntity.status(HttpStatus.OK).body(proveedor);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertProveedor(@RequestBody Proveedor proveedor) {
        proveedorService.insertProveedor(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED).body("El proveedor '" + proveedor.getNombre() + "' ha sido agregado correctamente");
    }

    @PutMapping("/{idProveedor}/update")
    public ResponseEntity<String> updateProveedor(@PathVariable Long idProveedor, @RequestBody Proveedor proveedor) {
        proveedorService.updateProveedor(idProveedor, proveedor);
        return ResponseEntity.status(HttpStatus.OK).body("El proveedor '" + proveedor.getNombre() + "' ha sido actualizado correctamente");
    }

    @DeleteMapping("/{idProveedor}/delete")
    public ResponseEntity<String> deleteProveedor(@PathVariable Long idProveedor) {
        proveedorService.deleteProveedor(idProveedor);
        return ResponseEntity.status(HttpStatus.OK).body("El proveedor ha sido eliminado correctamente");
    }
}