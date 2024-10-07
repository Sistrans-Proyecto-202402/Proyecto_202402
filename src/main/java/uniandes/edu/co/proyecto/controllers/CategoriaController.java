package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.services.CategoriaService;
import uniandes.edu.co.proyecto.entities.Categoria;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController 
{
    @Autowired
    private CategoriaService categoriaService;

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Categoria>> findAllCategorias() 
    {
        List <Categoria> categorias = categoriaService.findAllCategorias();
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/{idCategoria}/find")
    public ResponseEntity<Categoria> findCategoriaById(@PathVariable Long idCategoria) 
    {
        Categoria categoria = categoriaService.findCategoriaById(idCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/insert")
    public ResponseEntity<String> insertCategoria(@RequestBody Categoria categoria) 
    {
        categoriaService.insertCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body("La categoria '" + categoria.getNombre() + "' ha sido agregada correctamente");
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @PutMapping("/{idCategoria}/update")
    public ResponseEntity<String> updateCategoria(@PathVariable Long idCategoria, @RequestBody Categoria categoria) 
    {
        categoriaService.updateCategoria(idCategoria, categoria);
        return ResponseEntity.status(HttpStatus.OK).body("La categoria '" + categoria.getNombre() + "' ha sido actualizada correctamente");
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping("/{idCategoria}/delete")
    public ResponseEntity<String> deleteCategoria(@PathVariable Long idCategoria) 
    {
        categoriaService.deleteCategoria(idCategoria);
        return ResponseEntity.status(HttpStatus.OK).body("La categoria ha sido eliminada correctamente");
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}