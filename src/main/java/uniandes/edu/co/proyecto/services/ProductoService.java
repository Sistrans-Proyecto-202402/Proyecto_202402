package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uniandes.edu.co.proyecto.entities.Producto;
import uniandes.edu.co.proyecto.repositories.ProductoRepository;
import uniandes.edu.co.proyecto.repositories.ProductoSucursalRepository;
import uniandes.edu.co.proyecto.repositories.ProveedorProductoRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoSucursalRepository productoSucursalRepository;

    @Autowired
    private ProveedorProductoRepository proveedorProductoRepository;

    public List<Producto> findAllProductos() {
        return productoRepository.findAllProductos();
    }

    public Producto findProductoById(Long idProducto) {
        Optional<Producto> producto = productoRepository.findProductoById(idProducto);

        if (producto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado");
        }

        return producto.get();
    }

    public void insertProducto(Producto producto) {

        if (producto.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del producto no puede estar vacío");
        }

        if (producto.getCostoBodega() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El costo de bodega del producto debe ser mayor a cero");
        }

        if (producto.getPrecioUnitario() < producto.getCostoBodega()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio unitario del producto debe ser mayor al costo de bodega");
        }

        if (producto.getPresentacion().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La presentación del producto no puede estar vacía");
        }

        if (producto.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad de la presentacion del producto debe ser mayor a cero");
        }

        if (producto.getVolumenEmpaque() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El volumen de empaque del producto debe ser mayor a cero");
        }

        if (producto.getPesoEmpaque() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El peso de empaque del producto debe ser mayor a cero");
        }

        if ((producto.getCategoria().getId() == 1) && (producto.getFechaVencimiento() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los productos de la categoría perecederos deben tener fecha de vencimiento");
        }

        if ((producto.getCategoria().getId() != 1) && (producto.getFechaVencimiento() != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los productos distintos a la categoría perecederos no deben tener fecha de vencimiento");
        }

        if ((producto.getCategoria().getId() == 1) && (producto.getFechaVencimiento().isBefore(LocalDate.now()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de vencimiento del producto no puede ser anterior a la fecha actual");
        }

        if (producto.getCodigoBarras().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El código de barras del producto no puede estar vacío");
        }

        try {
            productoRepository.insertProducto(producto.getNombre(), producto.getCostoBodega(), producto.getPrecioUnitario(), producto.getPresentacion(), producto.getCantidad(), producto.getUnidadMedida().name(), producto.getVolumenEmpaque(), producto.getPesoEmpaque(), producto.getFechaVencimiento(), producto.getCodigoBarras(), producto.getCategoria().getId());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar el producto");
        }
    }

    public void updateProducto(Long idProducto, Producto producto) {

        if (productoRepository.findProductoById(idProducto).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado");
        } 

        if (producto.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del producto no puede estar vacío");
        }

        if (producto.getCostoBodega() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El costo de bodega del producto debe ser mayor a cero");
        }

        if (producto.getPrecioUnitario() <= producto.getCostoBodega()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio unitario del producto debe ser mayor al costo de bodega");
        }

        if (producto.getPresentacion().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La presentación del producto no puede estar vacía");
        }

        if (producto.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad de la presentacion del producto debe ser mayor a cero");
        }

        if (producto.getVolumenEmpaque() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El volumen de empaque del producto debe ser mayor a cero");
        }

        if (producto.getPesoEmpaque() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El peso de empaque del producto debe ser mayor a cero");
        }

        if ((producto.getCategoria().getId() == 1) && (producto.getFechaVencimiento() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los productos de la categoría perecederos deben tener fecha de vencimiento");
        }

        if ((producto.getCategoria().getId() != 1) && (producto.getFechaVencimiento() != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los productos distintos a la categoría perecederos no deben tener fecha de vencimiento");
        }

        if ((producto.getCategoria().getId() == 1) && (producto.getFechaVencimiento().isBefore(LocalDate.now()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de vencimiento del producto no puede ser anterior a la fecha actual");
        }

        try {
            productoRepository.updateProducto(idProducto, producto.getNombre(), producto.getCostoBodega(), producto.getPrecioUnitario(), producto.getPresentacion(), producto.getCantidad(), producto.getUnidadMedida().name(), producto.getVolumenEmpaque(), producto.getPesoEmpaque(), producto.getFechaVencimiento(), producto.getCategoria().getId());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el producto");
        }
    }

    public void deleteProducto(Long idProducto) {

        if (productoRepository.findProductoById(idProducto).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado");
        }

        if (!productoSucursalRepository.findSucursalesByProducto(idProducto).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto se encuentra registrado en al menos una sucursal");
        }

        if (!proveedorProductoRepository.findProveedoresByProducto(idProducto).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto se encuentra registrado en al menos un proveedor");
        }
        
        try {
            productoRepository.deleteProducto(idProducto);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el producto");
        }
    }
}
