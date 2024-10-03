package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
@Table(name = "ordencompra")
public class OrdenCompra extends BaseEntity {

    @Column(name = "fechacreacion", nullable = false, updatable = false)
    private LocalDate fechaCreacion;

    @Column(name = "fechaentrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(name = "fecharecepcion")
    private LocalDate fechaRecepcion;

    @Column(name = "estado", nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = false)
    private Bodega bodega;
}
