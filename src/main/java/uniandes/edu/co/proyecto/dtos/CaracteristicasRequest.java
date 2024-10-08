package uniandes.edu.co.proyecto.dtos;

import lombok.Data;
import uniandes.edu.co.proyecto.entities.Categoria;
import uniandes.edu.co.proyecto.entities.Sucursal;
import java.time.LocalDate;

@Data
public class CaracteristicasRequest {

    private double precioMinimo;

    private double precioMaximo;

    private LocalDate fechaMinima;

    private LocalDate fechaMaxima;

    private Sucursal sucursal;

    private Categoria categoria;
}
