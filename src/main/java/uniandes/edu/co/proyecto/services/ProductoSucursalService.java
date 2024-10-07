package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.entities.ProductoSucursal;
import uniandes.edu.co.proyecto.repositories.BodegaProductoRepository;
import uniandes.edu.co.proyecto.repositories.BodegaRepository;
import uniandes.edu.co.proyecto.repositories.ProductoSucursalRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoSucursalService {

    @Autowired
    private ProductoSucursalRepository productoSucursalRepository;

    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    private BodegaProductoRepository bodegaProductoRepository;

    public List<ProductoSucursal> findAllProductosSucursales() {
        return productoSucursalRepository.findAllProductosSucursales();
    }

    public ProductoSucursal findProductoSucursalById(Long idProducto, Long idSucursal) {
        Optional<ProductoSucursal> productoSucursal = productoSucursalRepository.findProductoSucursalById(idSucursal, idProducto);

        if (productoSucursal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en la sucursal");
        }

        return productoSucursal.get();
    }

    @Transactional
    public void insertProductoSucursal(ProductoSucursal productoSucursal) {

        if (productoSucursal.getCantidadMinima() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad mínima del producto a registrar en la sucursal debe ser mayor a cero");
        }

        List<Long> bodegas = bodegaRepository.findBodegasBySucursal(productoSucursal.getId().getSucursal().getId());

        if (bodegas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La sucursal no se encuentra registrada o no tiene bodegas asociadas");
        }

        try {
            productoSucursalRepository.insertProductoSucursal(productoSucursal.getId().getSucursal().getId(), productoSucursal.getId().getProducto().getId(), productoSucursal.getCantidadMinima());
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar el producto en la sucursal");
        }

        for (Long idBodega : bodegas) {
            try {
                bodegaProductoRepository.insertBodegaProducto(idBodega, productoSucursal.getId().getProducto().getId(), 0, 0.0, 20000);
            } 
            catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto ya se encuentra registrado en la bodega de la sucursal");
            }
        }
    }

    public void updateProductoSucursal(Long idProducto, Long idSucursal, ProductoSucursal productoSucursal) {

        if (productoSucursalRepository.findProductoSucursalById(idSucursal, idProducto).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en la sucursal");
        }

        if (productoSucursal.getCantidadMinima() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad mínima del producto en la sucursal debe ser mayor a cero");
        }

        try {
            productoSucursalRepository.updateProductoSucursal(idSucursal, idProducto, productoSucursal.getCantidadMinima());
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el producto de la sucursal");
        }
    }

    @Transactional
    public void deleteProductoSucursal(Long idProducto, Long idSucursal) {

        if (productoSucursalRepository.findProductoSucursalById(idSucursal, idProducto).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en la sucursal");
        }

        List<Long> bodegas = bodegaRepository.findBodegasBySucursal(idSucursal);

        for (Long idBodega : bodegas) {

            if (bodegaProductoRepository.findBodegaProductoById(idBodega, idProducto).get().getExistencias() > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar el producto de la sucursal porque tiene existencias en la bodega");
            }

            try {
                bodegaProductoRepository.deleteBodegaProducto(idBodega, idProducto);
            } 
            catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el producto de la bodega");
            }
        }

        try {
            productoSucursalRepository.deleteProductoSucursal(idSucursal, idProducto);
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el producto de la sucursal");
        }
    }
}
