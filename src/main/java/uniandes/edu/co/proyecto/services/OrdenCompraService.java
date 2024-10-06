package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.entities.OrdenCompra;
import uniandes.edu.co.proyecto.repositories.OrdenCompraRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

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
    public void insertOrdenCompra(OrdenCompra ordenCompra) {

        if (ordenCompra.getFechaEntrega().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de entrega de la orden de compra no puede ser anterior a la fecha actual");
        }

        try {
            ordenCompraRepository.insertOrdenCompra(LocalDate.now(), ordenCompra.getFechaEntrega(), ordenCompra.getEstado().name(), ordenCompra.getProveedor().getId(), ordenCompra.getSucursal().getId(), ordenCompra.getBodega().getId());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar la orden de compra");
        }
    }

    @Transactional
    public void updateOrdenCompra(Long idOrdenCompra, OrdenCompra ordenCompra) {
        
        if (ordenCompraRepository.findOrdenCompraById(idOrdenCompra).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La orden de compra no se encuentra registrada");
        }

        if (ordenCompra.getFechaEntrega().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de entrega de la orden de compra no puede ser anterior a la fecha actual");
        }

        ordenCompraRepository.updateOrdenCompra(idOrdenCompra, ordenCompra.getFechaEntrega(), ordenCompra.getProveedor().getId(), ordenCompra.getSucursal().getId(), ordenCompra.getBodega().getId());
    }

    public void deleteOrdenCompra(Long idOrdenCompra) {
        
        Optional<OrdenCompra> ordenCompra = ordenCompraRepository.findOrdenCompraById(idOrdenCompra);

        if (ordenCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La orden de compra no se encuentra registrada");
        }

        if (ordenCompra.get().getEstado().name().equals("Entregada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden de compra no puede ser eliminada porque ya ha sido entregada");
        }

        ordenCompraRepository.deleteOrdenCompra(idOrdenCompra);
    }
}
