package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uniandes.edu.co.proyecto.entities.Proveedor;
import uniandes.edu.co.proyecto.repositories.ProveedorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAllProveedores() {
        return proveedorRepository.findAllProveedores();
    }

    public Proveedor findProveedorById(Long idProveedor) {
        Optional<Proveedor> proveedor = proveedorRepository.findProveedorById(idProveedor);

        if (proveedor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El proveedor no se encuentra registrado");
        }

        return proveedor.get();
    }

    public void insertProveedor(Proveedor proveedor) {
        try {
            proveedorRepository.insertProveedor(proveedor.getNit(), proveedor.getNombre(), proveedor.getDireccion(), proveedor.getNombreContacto(), proveedor.getTelefonoContacto());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar el proveedor");
        }
    }

    public void updateProveedor(Long idProveedor, Proveedor proveedor) {
        if (proveedorRepository.findProveedorById(idProveedor).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El proveedor no se encuentra registrado");
        }

        try {
            proveedorRepository.updateProveedor(idProveedor, proveedor.getNombre(), proveedor.getDireccion(), proveedor.getNombreContacto(), proveedor.getTelefonoContacto());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el proveedor");
        }
    }

    public void deleteProveedor(Long idProveedor) {
        if (proveedorRepository.findProveedorById(idProveedor).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El proveedor no se encuentra registrado");
        }

        try {
            proveedorRepository.deleteProveedor(idProveedor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el proveedor");
        }
    }
}
