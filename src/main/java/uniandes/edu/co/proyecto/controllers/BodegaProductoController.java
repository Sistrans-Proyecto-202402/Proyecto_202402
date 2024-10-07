package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.entities.BodegaProducto;
import uniandes.edu.co.proyecto.services.BodegaProductoService;
import java.util.List;

@RestController
@RequestMapping("/bodegasproductos")
public class BodegaProductoController {

    @Autowired
    private BodegaProductoService bodegaProductoService;

    @GetMapping
    public ResponseEntity<List<BodegaProducto>> findAllBodegasProductos() {
        List<BodegaProducto> bodegasProductos = bodegaProductoService.findAllBodegasProductos();
        return ResponseEntity.status(HttpStatus.OK).body(bodegasProductos);
    }

    @GetMapping("/{idBodega}/{idProducto}/find")
    public ResponseEntity<BodegaProducto> findBodegaProductoById(@PathVariable Long idBodega, @PathVariable Long idProducto) {
        BodegaProducto bodegaProducto = bodegaProductoService.findBodegaProductoById(idBodega, idProducto);
        return ResponseEntity.status(HttpStatus.OK).body(bodegaProducto); 
    }

    @PutMapping("/{idBodega}/{idProducto}/update/capacidad")
    public ResponseEntity<String> updateCapacidadBodegaProducto(@PathVariable Long idBodega, @PathVariable Long idProducto, @RequestBody BodegaProducto bodegaProducto) {
        bodegaProductoService.updateCapacidadBodegaProducto(idBodega, idProducto, bodegaProducto);
        return ResponseEntity.status(HttpStatus.OK).body("La capacidad de la bodega ha sido actualizada correctamente");
    }

    @DeleteMapping("/{idBodega}/{idProducto}/delete")
    public ResponseEntity<String> deleteBodegaProducto(@PathVariable Long idBodega, @PathVariable Long idProducto) {
        bodegaProductoService.deleteBodegaProducto(idBodega, idProducto);
        return ResponseEntity.status(HttpStatus.OK).body("El producto de la bodega ha sido eliminado correctamente");
    }
}
