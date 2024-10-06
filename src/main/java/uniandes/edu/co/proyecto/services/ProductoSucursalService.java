package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto de la sucursal no se encuentra registrado");
        }

        return productoSucursal.get();
    }

    public void insertProductoSucursal(ProductoSucursal productoSucursal) {

        if (productoSucursal.getCantidadMinima() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad mínima del producto a registrar en la sucursal debe ser mayor a cero");
        }

        List<Long> bodegas = bodegaRepository.findBodegasBySucursal(productoSucursal.getId().getSucursal().getId());

        if (bodegas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La sucursal no tiene bodegas registradas");
        }

        for (Long idBodega : bodegas) {
            try {
                bodegaProductoRepository.insertBodegaProducto(idBodega, productoSucursal.getId().getProducto().getId(), 0, 0.0, 20000);
            } 
            catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto ya se encuentra registrado en la bodega de la sucursal");
            }
        }

        try {
            productoSucursalRepository.insertProductoSucursal(productoSucursal.getId().getProducto().getId(), productoSucursal.getId().getSucursal().getId(), productoSucursal.getCantidadMinima());
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto ya se encuentra registrado en la sucursal");
        }
    }

    public void updateProductoSucursal(Long idProducto, Long idSucursal, ProductoSucursal productoSucursal) {

        if (productoSucursal.getCantidadMinima() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad mínima del producto en la sucursal debe ser mayor a cero");
        }

        try {
            productoSucursalRepository.updateProductoSucursal(idSucursal, idProducto, productoSucursal.getCantidadMinima());
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto de la sucursal no se encuentra registrado");
        }
    }

    public void deleteProductoSucursal(Long idProducto, Long idSucursal) {
        try {
            productoSucursalRepository.deleteProductoSucursal(idSucursal, idProducto);
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto de la sucursal no se encuentra registrado");
        }
    }
}
