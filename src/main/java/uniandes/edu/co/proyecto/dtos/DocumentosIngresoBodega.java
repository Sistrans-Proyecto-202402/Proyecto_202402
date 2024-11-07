package uniandes.edu.co.proyecto.dtos;

import java.util.List;

import lombok.Data;

@Data
public class DocumentosIngresoBodega {

    String sucursal;

    String bodega;

    List<DocumentoIngresoDTO> documentosIngreso;
}
