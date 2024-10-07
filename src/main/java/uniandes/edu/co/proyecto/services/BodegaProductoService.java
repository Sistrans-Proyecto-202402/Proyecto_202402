package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import uniandes.edu.co.proyecto.entities.BodegaProducto;
import uniandes.edu.co.proyecto.repositories.BodegaProductoRepository;
import uniandes.edu.co.proyecto.repositories.ProductoSucursalRepository;
import uniandes.edu.co.proyecto.dtos.IndiceOcupacionDTO;
import uniandes.edu.co.proyecto.dtos.OcupacionProductosRequest;
import uniandes.edu.co.proyecto.entities.Producto;
import java.util.ArrayList;

@Service
public class BodegaProductoService {

    @Autowired
    private BodegaProductoRepository bodegaProductoRepository;

    @Autowired
    private ProductoSucursalRepository productoSucursalRepository;

    public List<BodegaProducto> findAllBodegasProductos() {
        return bodegaProductoRepository.findAllBodegasProductos();
    }

    public BodegaProducto findBodegaProductoById(Long idBodega, Long idProducto) {
        Optional<BodegaProducto> bodegaProducto = bodegaProductoRepository.findBodegaProductoById(idBodega, idProducto);

        if (bodegaProducto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en la bodega");
        }

        return bodegaProducto.get();
    }

    public List<IndiceOcupacionDTO> findOcupacionBodegasByProductos(OcupacionProductosRequest ocupacionProductosRequest) {

        List<IndiceOcupacionDTO> indicesOcupacion = new ArrayList<>();
        List<Producto> productos = ocupacionProductosRequest.getProductos();

        if (productos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La lista de productos a consultar no puede estar vacía");
        }

        for (Producto producto : productos) {
            
            List<BodegaProducto> bodegasProducto = bodegaProductoRepository.findBodegasByProducto(producto.getId());

            if (bodegasProducto.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en ninguna bodega");
            }

            for (BodegaProducto bodegaProducto : bodegasProducto) {

                double indiceOcupacion = (double) bodegaProducto.getExistencias() / bodegaProducto.getCapacidad();

                IndiceOcupacionDTO indiceOcupacionDTO = new IndiceOcupacionDTO();
                indiceOcupacionDTO.setBodega(bodegaProducto.getId().getBodega());
                indiceOcupacionDTO.setProducto(bodegaProducto.getId().getProducto());
                indiceOcupacionDTO.setIndiceOcupacion(indiceOcupacion * 100);

                indicesOcupacion.add(indiceOcupacionDTO);
            }
        }

        return indicesOcupacion;
    }

    public void updateCapacidadBodegaProducto(Long idBodega, Long idProducto, BodegaProducto bodegaProducto) {

        Optional<BodegaProducto> bodegaProductoOptional = bodegaProductoRepository.findBodegaProductoById(idBodega, idProducto);

        if (bodegaProductoOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en la bodega");
        }

        BodegaProducto bodegaProductoActual = bodegaProductoOptional.get();

        if (bodegaProducto.getCapacidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La capacidad de la bodega debe ser mayor a cero");
        }

        if (bodegaProducto.getCapacidad() < bodegaProductoActual.getExistencias()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La capacidad de la bodega no puede ser menor a las existencias actuales");
        }

        try {
            bodegaProductoRepository.updateCapacidadBodegaProducto(idBodega, idProducto, bodegaProducto.getCapacidad());
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la capacidad de la bodega");
        }
    }

    public void deleteBodegaProducto(Long idBodega, Long idProducto) {
        Optional<BodegaProducto> bodegaProducto = bodegaProductoRepository.findBodegaProductoById(idBodega, idProducto);

        if (bodegaProducto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se encuentra registrado en la bodega");
        }

        BodegaProducto bodegaProductoActual = bodegaProducto.get();

        if (bodegaProductoActual.getExistencias() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar el producto de la bodega si tiene existencias");
        }

        if (!productoSucursalRepository.findProductoSucursalById(bodegaProductoActual.getId().getBodega().getSucursal().getId(), idProducto).isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se puede eliminar el producto de la bodega si no está registrado en la sucursal");
        }
        
        try {
            bodegaProductoRepository.deleteBodegaProducto(idBodega, idProducto);
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el producto de la bodega");
        }
    }
}
