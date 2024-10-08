package uniandes.edu.co.proyecto.dtos;

public interface BodegaProductoDTO {

    Long getIdBodega();

    String getNombreBodega();
    
    Long getIdProducto();

    String getNombreProducto();

    Integer getExistencias();

    double getPrecioPromedio();

    Integer getCapacidad();

    Integer getCantidadMinima();
}
