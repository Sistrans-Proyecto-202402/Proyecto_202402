package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Data
@Table(name = "categoria")
public class Categoria extends BaseEntity {

    @Column(name = "nombre", nullable = false, unique = true, updatable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "caracteristicas", nullable = false)
    private String caracteristicas;
}
