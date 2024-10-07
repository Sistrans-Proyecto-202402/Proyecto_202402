package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.entities.Producto;
import uniandes.edu.co.proyecto.services.ProductoService;
import uniandes.edu.co.proyecto.dtos.CaracteristicasRequest;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> findAllProductos() {
        List<Producto> productos = productoService.findAllProductos();
        return ResponseEntity.status(HttpStatus.OK).body(productos);
    }

    @GetMapping("/{idProducto}/find")
    public ResponseEntity<Producto> findProductoById(@PathVariable Long idProducto) {
        Producto producto = productoService.findProductoById(idProducto);
        return ResponseEntity.status(HttpStatus.OK).body(producto); 
    }

    @GetMapping("/caracteristicas/find")
    public ResponseEntity<List<Producto>> findProductosByCaracteristicas(@RequestBody CaracteristicasRequest caracteristicasRequest) {
        List<Producto> productos = productoService.findProductosByCaracteristicas(caracteristicasRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productos);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertProducto(@RequestBody Producto producto) {
        productoService.insertProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body("El producto '" + producto.getNombre() + "' ha sido agregado correctamente");
    }

    @PutMapping("/{idProducto}/update")
    public ResponseEntity<String> updateProducto(@PathVariable Long idProducto, @RequestBody Producto producto) {
        productoService.updateProducto(idProducto, producto);
        return ResponseEntity.status(HttpStatus.OK).body("El producto '" + producto.getNombre() + "' ha sido actualizado correctamente");
    }

    @DeleteMapping("/{idProducto}/delete")
    public ResponseEntity<String> deleteProducto(@PathVariable Long idProducto) {
        productoService.deleteProducto(idProducto);
        return ResponseEntity.status(HttpStatus.OK).body("El producto ha sido eliminado correctamente");
    }
}
