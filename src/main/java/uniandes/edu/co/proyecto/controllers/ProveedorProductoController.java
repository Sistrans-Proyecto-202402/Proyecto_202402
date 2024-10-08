package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.entities.ProveedorProducto;
import uniandes.edu.co.proyecto.services.ProveedorProductoService;

import java.util.List;

@RestController
@RequestMapping("/proveedorproducto")
public class ProveedorProductoController {

    @Autowired
    private ProveedorProductoService proveedorProductoService;

    @GetMapping
    public ResponseEntity<List<ProveedorProducto>> findAllProveedorProductos() {
        List<ProveedorProducto> proveedorProductos = proveedorProductoService.findAllProveedorProductos();
        return ResponseEntity.status(HttpStatus.OK).body(proveedorProductos);
    }

    @GetMapping("/{productoId}/{proveedorId}")
    public ResponseEntity<ProveedorProducto> findProveedorProductoById(@PathVariable Long productoId, @PathVariable Long proveedorId) {
        ProveedorProducto proveedorProducto = proveedorProductoService.findProveedorProductoById(productoId, proveedorId);
        return ResponseEntity.status(HttpStatus.OK).body(proveedorProducto);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertProveedorProducto(@RequestBody ProveedorProducto proveedorProducto) {
        proveedorProductoService.insertProveedorProducto(proveedorProducto.getId().getProducto().getId(), proveedorProducto.getId().getProveedor().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Relación Proveedor-Producto creada correctamente");
    }

    @DeleteMapping("/{productoId}/{proveedorId}/delete")
    public ResponseEntity<String> deleteProveedorProducto(@PathVariable Long productoId, @PathVariable Long proveedorId) {
        proveedorProductoService.deleteProveedorProducto(productoId, proveedorId);
        return ResponseEntity.status(HttpStatus.OK).body("Relación Proveedor-Producto eliminada correctamente");
    }
}