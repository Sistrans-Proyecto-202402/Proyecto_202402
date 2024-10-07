package uniandes.edu.co.proyecto.dtos;

import uniandes.edu.co.proyecto.entities.Bodega;
import uniandes.edu.co.proyecto.entities.Producto;
import lombok.Data;

@Data
public class IndiceOcupacionDTO {

    private Bodega bodega;

    private Producto producto;

    private double indiceOcupacion;
}
