package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uniandes.edu.co.proyecto.entities.Producto;
import uniandes.edu.co.proyecto.repositories.ProductoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

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
        try {
            if ((producto.getCategoria().getId() == 1) && (producto.getFechaVencimiento() == null)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los productos de la categoría perecederos deben tener fecha de vencimiento");
            }

            else if ((producto.getCategoria().getId() != 1) && (producto.getFechaVencimiento() != null)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los productos distintos a la categoría perecederos no deben tener fecha de vencimiento");
            }

            productoRepository.insertProducto(producto.getNombre(), producto.getCostoBodega(), producto.getPrecioUnitario(), producto.getPresentacion(), producto.getCantidad(), producto.getUnidadMedida(), producto.getVolumenEmpaque(), producto.getPesoEmpaque(), producto.getFechaVencimiento(), producto.getCodigoBarras(), producto.getCategoria().getId());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar el producto");
        }
    }

    public void updateProducto(Long idProducto, Producto producto) {
        try {
            if ((producto.getCategoria().getId() == 1) && (producto.getFechaVencimiento() == null)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los productos de la categoría perecederos deben tener fecha de vencimiento");
            }

            else if ((producto.getCategoria().getId() != 1) && (producto.getFechaVencimiento() != null)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los productos distintos a la categoría perecederos no deben tener fecha de vencimiento");
            }

            productoRepository.updateProducto(idProducto, producto.getNombre(), producto.getCostoBodega(), producto.getPrecioUnitario(), producto.getPresentacion(), producto.getCantidad(), producto.getUnidadMedida(), producto.getVolumenEmpaque(), producto.getPesoEmpaque(), producto.getCategoria().getId());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el producto");
        }
    }

    public void deleteProducto(Long idProducto) {
        try {
            productoRepository.deleteProducto(idProducto);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el producto");
        }
    }
}
