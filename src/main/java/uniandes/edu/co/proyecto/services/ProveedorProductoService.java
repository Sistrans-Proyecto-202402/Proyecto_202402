package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import uniandes.edu.co.proyecto.entities.ProveedorProducto;

import uniandes.edu.co.proyecto.repositories.ProveedorProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorProductoService {

   @Autowired
    private ProveedorProductoRepository proveedorProductoRepository;

    public List<ProveedorProducto> findAllProveedorProductos() {
        return proveedorProductoRepository.findAllProveedorProductos();
    }

    public ProveedorProducto findProveedorProductoById(Long productoId, Long proveedorId) {
        Optional<ProveedorProducto> proveedorProducto = proveedorProductoRepository.findProveedorProductoById(productoId, proveedorId);

        if (proveedorProducto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La relación Proveedor-Producto no se encuentra registrada");
        }

        return proveedorProducto.get();
    }

    public void insertProveedorProducto(Long productoId, Long proveedorId) {
        try {
            proveedorProductoRepository.insertProveedorProducto(productoId, proveedorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la relación Proveedor-Producto");
        }
    }

    public void deleteProveedorProducto(Long productoId, Long proveedorId) {
        Optional<ProveedorProducto> proveedorProducto = proveedorProductoRepository.findProveedorProductoById(productoId, proveedorId);

        if (proveedorProducto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La relación Proveedor-Producto no se encuentra registrada");
        }

        try {
            proveedorProductoRepository.deleteProveedorProducto(productoId, proveedorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la relación Proveedor-Producto");
        }
    }

}