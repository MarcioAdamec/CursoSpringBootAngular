package com.adamec.clientes.rest;

import com.adamec.clientes.model.entity.Cliente;
import com.adamec.clientes.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    @Autowired
    public ClienteController(ClienteRepository repository){
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@RequestBody @Valid Cliente cliente){

        return repository.save(cliente);

    }

    @GetMapping("{id}")
    public Cliente buscarPorId( @PathVariable Integer id ){

        return repository.findById(id).orElseThrow( () -> new ResponseStatusException(( HttpStatus.NOT_FOUND) ) );

    }

    @GetMapping("/all")
    public List buscarTodos(){

        return repository.findAll();

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id){

        repository.findById(id)
                .map( cliente -> {
                    repository.delete(cliente);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado){

        repository.findById(id).map( cliente -> { clienteAtualizado.setId(cliente.getId());
                        return repository.save(clienteAtualizado); })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );

    }

}
