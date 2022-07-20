package br.com.henrique.rest.controller;

import br.com.henrique.domain.entity.Cliente;
import br.com.henrique.domain.repository.ClientesRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/clientes")
@Api("Api Clientes")
public class ClienteControllerRestfull {

    @Autowired
    private ClientesRepository clientesRepository;

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")})
    public Cliente getClienteById(@PathVariable(name = "id") @ApiParam("Id do Cliente") Integer id){
        return this.clientesRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")})
    public Cliente save(@RequestBody @Valid Cliente cliente){
        return clientesRepository.save(cliente);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Integer id){
      this.clientesRepository.findById(id)
              .map(cliente -> {
                  clientesRepository.delete(cliente);
                  return cliente;
              })
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") Integer id, @RequestBody @Valid Cliente cliente){
         clientesRepository.findById(id).map(clienteExistente ->{
            cliente.setId(clienteExistente.getId());
            clientesRepository.save(cliente);
            return clienteExistente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @GetMapping
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro,matcher);
        List<Cliente> lista = clientesRepository.findAll(example);
        return clientesRepository.findAll();
    }
}
