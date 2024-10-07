package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.entities.OrdenProducto;
import uniandes.edu.co.proyecto.services.OrdenProductoService;
import java.util.List;

@RestController
@RequestMapping("/ordenesproductos")
public class OrdenProductoController {

    @Autowired
    private OrdenProductoService ordenProductoService;

    @GetMapping
    public ResponseEntity<List<OrdenProducto>> findAllOrdenesProducto() {
        List<OrdenProducto> ordenesProducto = ordenProductoService.findAllOrdenesProductos();
        return ResponseEntity.status(HttpStatus.OK).body(ordenesProducto);
    }

    @GetMapping("/{idOrden}/{idProducto}/find")
    public ResponseEntity<OrdenProducto> findOrdenProductoById(@PathVariable Long idOrden, @PathVariable Long idProducto) {
        OrdenProducto ordenProducto = ordenProductoService.findOrdenProductoById(idOrden, idProducto);
        return ResponseEntity.status(HttpStatus.OK).body(ordenProducto); 
    }

    @GetMapping("/{idOrden}/find/productos")
    public ResponseEntity<List<OrdenProducto>> findProductosOrdenCompra(@PathVariable Long idOrden) {
        List<OrdenProducto> productosOrden = ordenProductoService.findProductosOrdenCompra(idOrden);
        return ResponseEntity.status(HttpStatus.OK).body(productosOrden);
    }
}
