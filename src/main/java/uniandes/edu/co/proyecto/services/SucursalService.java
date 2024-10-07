package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uniandes.edu.co.proyecto.entities.Sucursal;
import uniandes.edu.co.proyecto.repositories.SucursalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAllSucursales() {
        return sucursalRepository.findAllSucursales();
    }

    public Sucursal findSucursalById(Long idSucursal) {
        Optional<Sucursal> sucursal = sucursalRepository.findSucursalById(idSucursal);

        if (sucursal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La sucursal no se encuentra registrada");
        }

        return sucursal.get();
    }

    public void insertSucursal(Sucursal sucursal) {
        try {
            sucursalRepository.insertSucursal(sucursal.getNombre(), sucursal.getDireccion(),
                sucursal.getTelefono(), sucursal.getTamano(), sucursal.getCiudad().getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar la sucursal");
        }
    }

    public void updateSucursal(Long idSucursal, Sucursal sucursal) {
        if (sucursalRepository.findSucursalById(idSucursal).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La sucursal no se encuentra registrada");
        }

        try {
            sucursalRepository.updateSucursal(idSucursal, sucursal.getNombre(), sucursal.getDireccion(),
                sucursal.getTelefono(), sucursal.getTamano(), sucursal.getCiudad().getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la sucursal");
        }
    }

    public void deleteSucursal(Long idSucursal) {
        if (sucursalRepository.findSucursalById(idSucursal).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La sucursal no se encuentra registrada");
        }

        try {
            sucursalRepository.deleteSucursal(idSucursal);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la sucursal");
        }
    }
}