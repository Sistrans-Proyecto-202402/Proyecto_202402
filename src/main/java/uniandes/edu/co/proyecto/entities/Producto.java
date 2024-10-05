package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import lombok.Data;
import uniandes.edu.co.proyecto.enumerations.UnidadMedida;


@Entity
@Data
@Table(name = "producto")
public class Producto extends BaseEntity {

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "costobodega", nullable = false)
    private double costoBodega;

    @Column(name = "preciounitario", nullable = false)
    private double precioUnitario;

    @Column(name = "presentacion", nullable = false)
    private String presentacion; 

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Enumerated(EnumType.STRING) 
    @Column(name = "unidadmedida", nullable = false)
    private UnidadMedida unidadMedida;

    @Column(name = "volumenempaque", nullable = false)
    private double volumenEmpaque;

    @Column(name = "pesoempaque", nullable = false)
    private double pesoEmpaque;

    @Column(name = "fechaexpiracion", updatable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "codigobarras", nullable = false, unique = true, updatable = false)
    private String codigoBarras;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}


