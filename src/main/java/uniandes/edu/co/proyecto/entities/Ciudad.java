package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Data
@Table(name = "ciudad")
public class Ciudad extends BaseEntity {

    @Column(name = "nombre", nullable = false, unique = true, updatable = false)
    private String nombre;
}

