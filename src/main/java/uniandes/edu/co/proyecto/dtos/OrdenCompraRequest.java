package uniandes.edu.co.proyecto.dtos;

import lombok.Data;
import uniandes.edu.co.proyecto.entities.OrdenCompra;
import java.util.List;

@Data
public class OrdenCompraRequest {

    private OrdenCompra ordenCompra;

    private List<ProductosOrdenDTO> productosOrden;
}
