package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.entities.OrdenCompra;
import uniandes.edu.co.proyecto.services.OrdenCompraService;
import java.util.List;
import uniandes.edu.co.proyecto.dtos.DocumentosIngresoBodega;
import uniandes.edu.co.proyecto.dtos.OrdenCompraRequest;
import uniandes.edu.co.proyecto.dtos.ProductosRequierenOrdenDTO;

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

    @GetMapping("/find/productos/orden/requerida")
    public ResponseEntity<List<ProductosRequierenOrdenDTO>> findProductosRequierenOrdenCompra() {
        List<ProductosRequierenOrdenDTO> productosRequierenOrden = ordenCompraService.findProductosRequierenOrdenCompra();
        return ResponseEntity.status(HttpStatus.OK).body(productosRequierenOrden);
    }

    @GetMapping("/{idSucursal}/{idBodega}/find/documentos/ingreso/productos/bodega/serializable")
    public ResponseEntity<DocumentosIngresoBodega> findDocumentosIngresoProductosByBodega(@PathVariable Long idSucursal, @PathVariable Long idBodega) {
        DocumentosIngresoBodega documentosIngreso = ordenCompraService.findDocumentosIngresoProductosByBodegaSerializable(idSucursal, idBodega);
        return ResponseEntity.status(HttpStatus.OK).body(documentosIngreso);
    }

    @GetMapping("/{idSucursal}/{idBodega}/find/documentos/ingreso/productos/bodega/read/committed")
    public ResponseEntity<DocumentosIngresoBodega> findDocumentosIngresoProductosByBodegaReadCommitted(@PathVariable Long idSucursal, @PathVariable Long idBodega) {
        DocumentosIngresoBodega documentosIngreso = ordenCompraService.findDocumentosIngresoProductosByBodegaReadCommitted(idSucursal, idBodega);
        return ResponseEntity.status(HttpStatus.OK).body(documentosIngreso);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertOrdenCompra(@RequestBody OrdenCompraRequest ordenCompraRequest) {
        ordenCompraService.insertOrdenCompra(ordenCompraRequest.getOrdenCompra(), ordenCompraRequest.getProductosOrden());
        return ResponseEntity.status(HttpStatus.CREATED).body("La orden de compra ha sido agregada correctamente");
    }

    @PutMapping("/{idOrdenCompra}/update")
    public ResponseEntity<String> updateOrdenCompra(@PathVariable Long idOrdenCompra, @RequestBody OrdenCompra ordenCompra) {
        ordenCompraService.updateOrdenCompra(idOrdenCompra, ordenCompra);
        return ResponseEntity.status(HttpStatus.OK).body("La orden de compra ha sido actualizada correctamente");
    }

    @PutMapping("/{idOrdenCompra}/update/estado")
    public ResponseEntity<String> updateEstadoOrdenCompra(@PathVariable Long idOrdenCompra, @RequestBody OrdenCompra ordenCompra) {
        ordenCompraService.updateEstadoOrdenCompra(idOrdenCompra, ordenCompra);
        return ResponseEntity.status(HttpStatus.OK).body("El estado de la orden de compra ha sido actualizado a '" + ordenCompra.getEstado() + "' correctamente");
    }

    @DeleteMapping("/{idOrdenCompra}/delete")
    public ResponseEntity<String> deleteOrdenCompra(@PathVariable Long idOrdenCompra) {
        ordenCompraService.deleteOrdenCompra(idOrdenCompra);
        return ResponseEntity.status(HttpStatus.OK).body("La orden de compra ha sido eliminada correctamente");
    }
}
