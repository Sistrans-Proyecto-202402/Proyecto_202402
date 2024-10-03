package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Data
@Table(name = "proveedor")
public class Proveedor extends BaseEntity {

    @Column(name = "nit", nullable = false, unique = true, updatable = false)
    private Long nit;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "nombrecontacto", nullable = false)
    private String nombreContacto;

    @Column(name = "telefonocontacto", nullable = false, unique = true)
    private Long telefonoContacto;
}

