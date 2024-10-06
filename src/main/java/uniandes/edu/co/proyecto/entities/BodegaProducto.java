package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "bodegaproducto")
public class BodegaProducto {

    @EmbeddedId
    private BodegaProductoPK id;

    @Column(name = "existencias", nullable = false)
    private Integer existencias;

    @Column(name = "preciopromedio", nullable = false)
    private double precioPromedio;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;
}

