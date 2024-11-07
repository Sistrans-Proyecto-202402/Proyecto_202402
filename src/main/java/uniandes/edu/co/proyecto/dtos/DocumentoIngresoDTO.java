package uniandes.edu.co.proyecto.dtos;

import java.time.LocalDate;

public interface DocumentoIngresoDTO {

    Long getId();

    LocalDate getFechaRecepcion();

    String getNombreProveedor();
}
