package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.entities.OrdenCompra;
import uniandes.edu.co.proyecto.services.OrdenCompraService;
import java.util.List;

@RestController
@RequestMapping("/ordenescompra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @GetMapping
    public ResponseEntity<List<OrdenCompra>> findAllOrdenesCompra() {
        List<OrdenCompra> ordenesCompra = ordenCompraService.findAllOrdenesCompra();
        return ResponseEntity.status(HttpStatus.OK).body(ordenesCompra);
    }

    @GetMapping("/{idOrdenCompra}/find")
    public ResponseEntity<OrdenCompra> findOrdenCompraById(@PathVariable Long idOrdenCompra) {
        OrdenCompra ordenCompra = ordenCompraService.findOrdenCompraById(idOrdenCompra);
        return ResponseEntity.status(HttpStatus.OK).body(ordenCompra); 
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertOrdenCompra(@RequestBody OrdenCompra ordenCompra) {
        ordenCompraService.insertOrdenCompra(ordenCompra);
        return ResponseEntity.status(HttpStatus.CREATED).body("La orden de compra ha sido agregada correctamente");
    }

    @PutMapping("/{idOrdenCompra}/update")
    public ResponseEntity<String> updateOrdenCompra(@PathVariable Long idOrdenCompra, @RequestBody OrdenCompra ordenCompra) {
        ordenCompraService.updateOrdenCompra(idOrdenCompra, ordenCompra);
        return ResponseEntity.status(HttpStatus.OK).body("La orden de compra ha sido actualizada correctamente");
    }

    @DeleteMapping("/{idOrdenCompra}/delete")
    public ResponseEntity<String> deleteOrdenCompra(@PathVariable Long idOrdenCompra) {
        ordenCompraService.deleteOrdenCompra(idOrdenCompra);
        return ResponseEntity.status(HttpStatus.OK).body("La orden de compra ha sido eliminada correctamente");
    }
}
