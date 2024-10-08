package uniandes.edu.co.proyecto.services;

import uniandes.edu.co.proyecto.entities.BodegaProducto;
import uniandes.edu.co.proyecto.entities.OrdenCompra;
import uniandes.edu.co.proyecto.entities.OrdenProducto;
import uniandes.edu.co.proyecto.entities.Producto;
import uniandes.edu.co.proyecto.repositories.OrdenProductoRepository;
import uniandes.edu.co.proyecto.repositories.OrdenCompraRepository;
import uniandes.edu.co.proyecto.repositories.ProveedorProductoRepository;
import uniandes.edu.co.proyecto.repositories.BodegaProductoRepository;
import uniandes.edu.co.proyecto.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class OrdenProductoService {

    @Autowired
    private OrdenProductoRepository ordenProductoRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private ProveedorProductoRepository proveedorProductoRepository;

    @Autowired
    private BodegaProductoRepository bodegaProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<OrdenProducto> findAllOrdenesProductos() {
        return ordenProductoRepository.findAllOrdenesProductos();
    }

    public OrdenProducto findOrdenProductoById(Long idOrden, Long idProducto) {
        Optional<OrdenProducto> ordenProducto = ordenProductoRepository.findOrdenProductoById(idOrden, idProducto);

        if (ordenProducto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en la orden de compra");
        }

        return ordenProducto.get();
    }

    public List<OrdenProducto> findProductosOrdenCompra(Long idOrden) {

        List <OrdenProducto> productosOrden = ordenProductoRepository.findProductosOrdenCompra(idOrden);

        if (productosOrden.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La orden de compra no se encuentra registrada");
        }

        return productosOrden;
    }

    // SE PUEDE INSERTAR UN NUEVO PRODUCTO A UNA ORDEN DE COMPRA YA CREADA???
    public void insertOrdenProducto(Long idOrden, Long idProducto, OrdenProducto ordenProducto) {

        Optional<OrdenCompra> ordenCompra = ordenCompraRepository.findOrdenCompraById(idOrden);

        if (ordenCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden de compra no se encuentra registrada");
        }

        OrdenCompra ordenCompraActual = ordenCompra.get();

        if (ordenCompraActual.getEstado().name().equals("Entregada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede agregar un producto a una orden de compra que ya ha sido entregada");
        }

        if (ordenCompraActual.getEstado().name().equals("Anulada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede agregar un producto a una orden de compra que ha sido anulada");
        }
        
        LocalDate fechaEntrega = ordenCompraActual.getFechaEntrega();
        LocalDate fechaActual = LocalDate.now();

        if (fechaActual.plusDays(3).isAfter(fechaEntrega)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Para agregar un producto a la orden de compra, debe hacerse con al menos 3 días de anticipación a la fecha de entrega");
        }
        
        if (proveedorProductoRepository.findProveedorProductoById(idProducto, ordenCompraActual.getProveedor().getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El proveedor de la orden de compra no suministra el producto");
        }

        Optional<BodegaProducto> bodegaProducto = bodegaProductoRepository.findBodegaProductoById(ordenCompraActual.getBodega().getId(), idProducto);

        if (bodegaProducto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto no se encuentra registrado en la bodega de la orden de compra");
        }

        BodegaProducto bodegaProductoActual = bodegaProducto.get();

        if ((bodegaProductoActual.getExistencias() / bodegaProductoActual.getCapacidad()) > 0.7) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La bodega se encuentra al 70% de su capacidad, no se pueden agregar más productos");
        }

        if ((bodegaProductoActual.getExistencias() + ordenProducto.getCantidad()) > bodegaProductoActual.getCapacidad()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad del producto excede la capacidad de la bodega");
        }

        if (ordenProducto.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad del producto debe ser mayor a cero");
        }

        Producto producto = productoRepository.findProductoById(idProducto).get();

        if (ordenProducto.getPrecio() < producto.getPrecioUnitario()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio del producto debe ser mayor o igual al precio unitario");
        }

        try {
            ordenProductoRepository.insertOrdenProducto(idOrden, idProducto, ordenProducto.getCantidad(), ordenProducto.getPrecio());
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar el producto en la orden de compra");
        }
    }

    // SE PUEDE ACTUALIZAR UN PRODUCTO DE UNA ORDEN DE COMPRA YA CREADA???
    public void updateOrdenProducto(Long idOrden, Long idProducto, OrdenProducto ordenProducto) {
        Optional<OrdenProducto> ordenProductoOptional = ordenProductoRepository.findOrdenProductoById(idOrden, idProducto);

        if (ordenProductoOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en la orden de compra");
        }

        OrdenProducto ordenProductoActual = ordenProductoOptional.get();

        if (ordenProductoActual.getId().getOrdenCompra().getEstado().name().equals("Entregada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede actualizar un producto de una orden de compra que ya ha sido entregada");
        }

        if (ordenProductoActual.getId().getOrdenCompra().getEstado().name().equals("Anulada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede actualizar un producto de una orden de compra que ha sido anulada");
        }
        
        LocalDate fechaEntrega = ordenProductoActual.getId().getOrdenCompra().getFechaEntrega();
        LocalDate fechaActual = LocalDate.now();

        if (fechaActual.plusDays(3).isAfter(fechaEntrega)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Para actualizar un producto de la orden de compra, debe hacerse con al menos 3 días de anticipación a la fecha de entrega");
        }

        if (ordenProducto.getCantidad() <= ordenProductoActual.getCantidad()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad del producto a actualizar debe ser mayor a la cantidad actual");
        }

        if (ordenProducto.getPrecio() < ordenProductoActual.getId().getProducto().getPrecioUnitario()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio del producto a actualizar debe ser mayor o igual al precio unitario del producto");
        }

        if (ordenProducto.getPrecio() <= ordenProductoActual.getPrecio()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio del producto a actualizar debe ser mayor al precio actual");
        }

        try {
            ordenProductoRepository.updateOrdenProducto(idOrden, idProducto, ordenProducto.getCantidad(), ordenProducto.getPrecio());
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el precio del producto en la orden de compra");
        }
    }

    // SE PUEDE ELIMINAR UN PRODUCTO DE UNA ORDEN DE COMPRA YA CREADA???
    public void deleteOrdenProducto(Long idOrden, Long idProducto) {
        Optional<OrdenProducto> ordenProducto = ordenProductoRepository.findOrdenProductoById(idOrden, idProducto);

        if (ordenProducto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en la orden de compra");
        }

        OrdenProducto ordenProductoActual = ordenProducto.get();

        if (ordenProductoActual.getId().getOrdenCompra().getEstado().name().equals("Entregada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar un producto de una orden de compra que ya ha sido entregada");
        }

        if (ordenProductoActual.getId().getOrdenCompra().getEstado().name().equals("Anulada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar un producto de una orden de compra que ha sido anulada");
        }
        
        LocalDate fechaEntrega = ordenProductoActual.getId().getOrdenCompra().getFechaEntrega();
        LocalDate fechaActual = LocalDate.now();

        if (fechaActual.plusDays(3).isAfter(fechaEntrega)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Para eliminar un producto de la orden de compra, debe hacerse con al menos 3 días de anticipación a la fecha de entrega");
        }

        try {
            ordenProductoRepository.deleteOrdenProducto(idOrden, idProducto);
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el producto de la orden de compra");
        }
    }
}
