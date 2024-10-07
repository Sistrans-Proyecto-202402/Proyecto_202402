
package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uniandes.edu.co.proyecto.entities.ProveedorSucursal;
import uniandes.edu.co.proyecto.repositories.ProveedorSucursalRepository;
import uniandes.edu.co.proyecto.repositories.ProveedorRepository; // Si es necesario
import uniandes.edu.co.proyecto.repositories.SucursalRepository; // Si es necesario

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorSucursalService {

    @Autowired
    private ProveedorSucursalRepository proveedorSucursalRepository;

    @Autowired
    private ProveedorRepository proveedorRepository; // Si es necesario

    @Autowired
    private SucursalRepository sucursalRepository; // Si es necesario

    public List<ProveedorSucursal> findAllProveedorSucursales() {
        return proveedorSucursalRepository.findAllProveedorSucursales();
    }

    public ProveedorSucursal findProveedorSucursalById(Long sucursalId, Long proveedorId) {
        Optional<ProveedorSucursal> proveedorSucursal = proveedorSucursalRepository.findProveedorSucursalById(sucursalId, proveedorId);

        if (proveedorSucursal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La relación proveedor-sucursal no se encuentra registrada");
        }

        return proveedorSucursal.get();
    }

    public void insertProveedorSucursal(Long sucursalId, Long proveedorId) {
        if (!sucursalRepository.existsById(sucursalId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La sucursal no existe");
        }

        if (!proveedorRepository.existsById(proveedorId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El proveedor no existe");
        }

        try {
            proveedorSucursalRepository.insertProveedorSucursal(sucursalId, proveedorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la relación proveedor-sucursal");
        }
    }

    public void deleteProveedorSucursal(Long sucursalId, Long proveedorId) {
        Optional<ProveedorSucursal> proveedorSucursal = proveedorSucursalRepository.findProveedorSucursalById(sucursalId, proveedorId);

        if (proveedorSucursal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La relación proveedor-sucursal no se encuentra registrada");
        }

        try {
            proveedorSucursalRepository.deleteProveedorSucursal(sucursalId, proveedorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la relación proveedor-sucursal");
        }
    }
}
