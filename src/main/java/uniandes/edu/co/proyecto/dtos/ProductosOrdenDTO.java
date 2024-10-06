package uniandes.edu.co.proyecto.dtos;

import lombok.Data;

@Data
public class ProductosOrdenDTO {

    private Long idProducto;

    private Integer cantidad;

    private Double precio;
}
