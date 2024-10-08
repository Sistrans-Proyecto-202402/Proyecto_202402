package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dtos.ProductoDisponibleSucursalDTO;
import uniandes.edu.co.proyecto.entities.ProductoSucursal;
import uniandes.edu.co.proyecto.services.ProductoSucursalService;
import java.util.List;

@RestController
@RequestMapping("/productossucursales")
public class ProductoSucursalController {

    @Autowired
    private ProductoSucursalService productoSucursalService;

    @GetMapping
    public ResponseEntity<List<ProductoSucursal>> findAllProductosSucursales() {
        List<ProductoSucursal> productosSucursal = productoSucursalService.findAllProductosSucursales();
        return ResponseEntity.status(HttpStatus.OK).body(productosSucursal);
    }

    @GetMapping("/{idProducto}/{idSucursal}/find")
    public ResponseEntity<ProductoSucursal> findProductoSucursalById(@PathVariable Long idProducto, @PathVariable Long idSucursal) {
        ProductoSucursal productoSucursal = productoSucursalService.findProductoSucursalById(idProducto, idSucursal);
        return ResponseEntity.status(HttpStatus.OK).body(productoSucursal); 
    }

    @GetMapping("/{idProducto}/find/sucursales/disponible")
    public ResponseEntity<List<ProductoDisponibleSucursalDTO>> findSucursalesByProductoDisponible(@PathVariable Long idProducto) {
        List<ProductoDisponibleSucursalDTO> sucursalesProducto = productoSucursalService.findSucursalesByProductoDisponible(idProducto);
        return ResponseEntity.status(HttpStatus.OK).body(sucursalesProducto);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertProductoSucursal(@RequestBody ProductoSucursal productoSucursal) {
        productoSucursalService.insertProductoSucursal(productoSucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body("El producto ha sido registrado en la sucursal correctamente");
    }

    @PutMapping("/{idProducto}/{idSucursal}/update")
    public ResponseEntity<String> updateProductoSucursal(@PathVariable Long idProducto, @PathVariable Long idSucursal, @RequestBody ProductoSucursal productoSucursal) {
        productoSucursalService.updateProductoSucursal(idProducto, idSucursal, productoSucursal);
        return ResponseEntity.status(HttpStatus.OK).body("El producto ha sido actualizado en la sucursal correctamente");
    }

    @DeleteMapping("/{idProducto}/{idSucursal}/delete")
    public ResponseEntity<String> deleteProductoSucursal(@PathVariable Long idProducto, @PathVariable Long idSucursal) {
        productoSucursalService.deleteProductoSucursal(idProducto, idSucursal);
        return ResponseEntity.status(HttpStatus.OK).body("El producto de la sucursal ha sido eliminado correctamente");
    }
}
