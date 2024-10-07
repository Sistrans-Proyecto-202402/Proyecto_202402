package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.entities.ProveedorSucursal;
import uniandes.edu.co.proyecto.services.ProveedorSucursalService;

import java.util.List;

@RestController
@RequestMapping("/proveedsucursal")
public class ProveedorSucursalController {

    @Autowired
    private ProveedorSucursalService proveedorSucursalService;

    @GetMapping
    public ResponseEntity<List<ProveedorSucursal>> findAllProveedorSucursales() {
        List<ProveedorSucursal> proveedorSucursales = proveedorSucursalService.findAllProveedorSucursales();
        return ResponseEntity.status(HttpStatus.OK).body(proveedorSucursales);
    }

    @GetMapping("/{sucursalId}/{proveedorId}")
    public ResponseEntity<ProveedorSucursal> findProveedorSucursalById(@PathVariable Long sucursalId, @PathVariable Long proveedorId) {
        ProveedorSucursal proveedorSucursal = proveedorSucursalService.findProveedorSucursalById(sucursalId, proveedorId);
        return ResponseEntity.status(HttpStatus.OK).body(proveedorSucursal);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertProveedorSucursal(@RequestParam Long sucursalId, @RequestParam Long proveedorId) {
        proveedorSucursalService.insertProveedorSucursal(sucursalId, proveedorId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Relación Proveedor-Sucursal creada correctamente");
    }

    @DeleteMapping("/{sucursalId}/{proveedorId}/delete")
    public ResponseEntity<String> deleteProveedorSucursal(@PathVariable Long sucursalId, @PathVariable Long proveedorId) {
        proveedorSucursalService.deleteProveedorSucursal(sucursalId, proveedorId);
        return ResponseEntity.status(HttpStatus.OK).body("Relación Proveedor-Sucursal eliminada correctamente");
    }
}