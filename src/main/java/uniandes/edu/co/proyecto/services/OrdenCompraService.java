package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.entities.OrdenCompra;
import uniandes.edu.co.proyecto.entities.OrdenProducto;
import uniandes.edu.co.proyecto.repositories.OrdenCompraRepository;
import uniandes.edu.co.proyecto.entities.BodegaProducto;
import uniandes.edu.co.proyecto.repositories.BodegaProductoRepository;
import uniandes.edu.co.proyecto.repositories.BodegaRepository;
import uniandes.edu.co.proyecto.repositories.OrdenProductoRepository;
import uniandes.edu.co.proyecto.dtos.ProductosOrdenDTO;
import uniandes.edu.co.proyecto.repositories.ProveedorProductoRepository;
import uniandes.edu.co.proyecto.repositories.ProveedorSucursalRepository;
import uniandes.edu.co.proyecto.entities.Producto;
import uniandes.edu.co.proyecto.repositories.ProductoRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private BodegaProductoRepository bodegaProductoRepository;

    @Autowired
    private OrdenProductoRepository ordenProductoRepository;

    @Autowired
    private ProveedorSucursalRepository proveedorSucursalRepository;

    @Autowired
    private ProveedorProductoRepository proveedorProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private BodegaRepository bodegaRepository;

    public List<OrdenCompra> findAllOrdenesCompra() {
        return ordenCompraRepository.findAllOrdenesCompra();
    }

    public OrdenCompra findOrdenCompraById(Long idOrdenCompra) {
        Optional<OrdenCompra> ordenCompra = ordenCompraRepository.findOrdenCompraById(idOrdenCompra);

        if (ordenCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La orden de compra no se encuentra registrada");
        }

        return ordenCompra.get();
    }

    @Transactional
    public void insertOrdenCompra(OrdenCompra ordenCompra, List<ProductosOrdenDTO> productosOrden) {

        if (ordenCompra.getFechaEntrega().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de entrega de la orden de compra no puede ser anterior a la fecha actual");
        }

        if (ordenCompra.getSucursal().getId() != bodegaRepository.findBodegaById(ordenCompra.getBodega().getId()).get().getSucursal().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La bodega de la orden de compra debe pertenecer a la misma sucursal");
        }

        if ((proveedorSucursalRepository.findProveedorSucursalById(ordenCompra.getSucursal().getId(), ordenCompra.getProveedor().getId())).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El proveedor no suministra productos a la sucursal");
        }

        if (productosOrden.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden de compra debe contener al menos un producto");
        }

        for (ProductosOrdenDTO productoOrden : productosOrden) {

            if (proveedorProductoRepository.findProveedorProductoById(productoOrden.getIdProducto(), ordenCompra.getProveedor().getId()).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El proveedor no suministra el producto");
            }

            Optional<BodegaProducto> bodegaProducto = bodegaProductoRepository.findBodegaProductoById(ordenCompra.getBodega().getId(), productoOrden.getIdProducto());

            if (bodegaProducto.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto no se encuentra registrado en la bodega");
            }

            BodegaProducto bodegaProductoActual = bodegaProducto.get();

            if ((bodegaProductoActual.getExistencias() / bodegaProductoActual.getCapacidad()) > 0.7) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La bodega se encuentra al 70% de su capacidad, no se pueden agregar más productos");
            }

            if ((bodegaProductoActual.getExistencias() + productoOrden.getCantidad()) > bodegaProductoActual.getCapacidad()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad del producto excede la capacidad de la bodega");
            }

            if (productoOrden.getCantidad() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad del producto debe ser mayor a cero");
            }

            Producto producto = productoRepository.findProductoById(productoOrden.getIdProducto()).get();

            if (productoOrden.getPrecio() < producto.getPrecioUnitario()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio del producto debe ser mayor o igual al precio unitario");
            }
        }

        try {
            ordenCompraRepository.insertOrdenCompra(LocalDate.now(), ordenCompra.getFechaEntrega(), "Vigente", ordenCompra.getProveedor().getId(), ordenCompra.getSucursal().getId(), ordenCompra.getBodega().getId());
            Long idOrden = ordenCompraRepository.findOrdenCompraByAttributes(LocalDate.now(), ordenCompra.getFechaEntrega(), "Vigente", ordenCompra.getProveedor().getId(), ordenCompra.getSucursal().getId(), ordenCompra.getBodega().getId()).get();

            for (ProductosOrdenDTO productoOrden : productosOrden) {
                ordenProductoRepository.insertOrdenProducto(idOrden, productoOrden.getIdProducto(), productoOrden.getCantidad(), productoOrden.getPrecio());
            }
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar la orden de compra");
        }
    }

    @Transactional
    public void updateOrdenCompra(Long idOrdenCompra, OrdenCompra ordenCompra) {
        
        Optional<OrdenCompra> ordenCompraOptional = ordenCompraRepository.findOrdenCompraById(idOrdenCompra);

        if (ordenCompraOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La orden de compra no se encuentra registrada");
        }

        OrdenCompra ordenCompraActual = ordenCompraOptional.get();

        if (ordenCompraActual.getEstado().name().equals("Entregada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden de compra no puede ser alterada porque ya ha sido entregada");
        }

        if (ordenCompraActual.getEstado().name().equals("Anulada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden de compra no puede ser alterada porque ya ha sido anulada");
        }

        if (ordenCompra.getFechaEntrega().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de entrega de la orden de compra no puede ser anterior a la fecha actual");
        }

        try {
            ordenCompraRepository.updateOrdenCompra(idOrdenCompra, ordenCompra.getFechaEntrega());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la orden de compra");
        }
    }

    @Transactional
    public void updateEstadoOrdenCompra(Long idOrdenCompra, OrdenCompra ordenCompra) {
        
        Optional<OrdenCompra> ordenCompraOptional = ordenCompraRepository.findOrdenCompraById(idOrdenCompra);

        if (ordenCompraOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La orden de compra no se encuentra registrada");
        }

        if (ordenCompra.getEstado().name().equals("Vigente")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado de la orden de compra ya es vigente");
        };

        OrdenCompra ordenCompraActual = ordenCompraOptional.get();

        if (ordenCompraActual.getEstado().name().equals("Entregada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden de compra no puede ser alterada porque ya ha sido entregada");
        }

        if (ordenCompraActual.getEstado().name().equals("Anulada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden de compra no puede ser alterada porque ya ha sido anulada");
        }

        if (ordenCompra.getEstado().name().equals("Entregada")) {

            List<OrdenProducto> productosOrden = ordenProductoRepository.findProductosOrdenCompra(idOrdenCompra);

            for (OrdenProducto productoOrden : productosOrden) {
                Optional<BodegaProducto> bodegaProducto = bodegaProductoRepository.findBodegaProductoById(ordenCompraActual.getBodega().getId(), productoOrden.getId().getProducto().getId());

                if (bodegaProducto.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en la bodega");
                }

                BodegaProducto bodegaProductoActual = bodegaProducto.get();

                double NuevoPrecioPromedio = ((bodegaProductoActual.getPrecioPromedio() * bodegaProductoActual.getExistencias()) + (productoOrden.getPrecio() * productoOrden.getCantidad())) / (bodegaProductoActual.getExistencias() + productoOrden.getCantidad());
                Integer NuevasExistencias = bodegaProductoActual.getExistencias() + productoOrden.getCantidad();

                try {
                    bodegaProductoRepository.updateBodegaProducto(ordenCompraActual.getBodega().getId(), productoOrden.getId().getProducto().getId(), NuevasExistencias, NuevoPrecioPromedio);
                }
                catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el producto en la bodega");
                }
            }
            
            try {
                ordenCompraRepository.updateEstadoOrdenCompra(idOrdenCompra, ordenCompra.getEstado().name(), LocalDate.now());
            }
            catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el estado de la orden de compra");
            }
        }

        if (ordenCompra.getEstado().name().equals("Anulada")) {

            try {
                ordenCompraRepository.updateEstadoOrdenCompra(idOrdenCompra, ordenCompra.getEstado().name(), null);
            }
            catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el estado de la orden de compra");
            }
        }
    }

    public void deleteOrdenCompra(Long idOrdenCompra) {
        
        Optional<OrdenCompra> ordenCompra = ordenCompraRepository.findOrdenCompraById(idOrdenCompra);

        if (ordenCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La orden de compra no se encuentra registrada");
        }

        if (ordenCompra.get().getEstado().name().equals("Entregada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden de compra no puede ser eliminada porque ya ha sido entregada");
        }

        if (ordenCompra.get().getEstado().name().equals("Vigente")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden de compra no puede ser eliminada porque aún sigue vigente");
        }

        try {
            ordenCompraRepository.deleteOrdenCompra(idOrdenCompra);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la orden de compra");
        }
    }
}
