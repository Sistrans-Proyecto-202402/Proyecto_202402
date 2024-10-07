package uniandes.edu.co.proyecto.dtos;

import uniandes.edu.co.proyecto.entities.Producto;
import java.util.List;
import lombok.Data;

@Data
public class OcupacionProductosRequest {

    private List<Producto> productos;
}
