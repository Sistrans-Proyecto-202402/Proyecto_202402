package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
@Embeddable
public class OrdenProductoPK {

    @ManyToOne
    @JoinColumn(name = "ordencompra_id", referencedColumnName = "id", nullable = false)
    private OrdenCompra ordenCompra;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id", nullable = false)
    private Producto producto;
}
