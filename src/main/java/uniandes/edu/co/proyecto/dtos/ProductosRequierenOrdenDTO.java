package uniandes.edu.co.proyecto.dtos;

public interface ProductosRequierenOrdenDTO {

    Long getIdProducto();

    String getNombreProducto();

    Long getIdBodega();

    String getNombreBodega();

    Long getIdSucursal();

    String getNombreSucursal();

    Long getIdProveedor();

    String getNombreProveedor();

    Integer getExistencias();

    Integer getCantidadMinima();
}
