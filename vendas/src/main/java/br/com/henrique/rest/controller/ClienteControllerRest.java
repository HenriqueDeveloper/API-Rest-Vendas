/*package br.com.henrique.domain.rest.controller;

import br.com.henrique.domain.entity.Cliente;
import br.com.henrique.domain.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteControllerRest {

    @Autowired
    private ClientesRepository clientesRepository;

    @GetMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable(name = "id") Integer id){
        Optional<Cliente> cliente = this.clientesRepository.findById(id);
        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/clientes")
    @ResponseBody
    public ResponseEntity save(@RequestBody Cliente cliente){
        Cliente clienteSalvo = clientesRepository.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }
    @DeleteMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable(name = "id") Integer id){
        Optional<Cliente> cliente = this.clientesRepository.findById(id);

        if(cliente.isPresent()){
            this.clientesRepository.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable(name = "id") Integer id, @RequestBody Cliente cliente){
        return clientesRepository.findById(id).map(clienteExistente ->{
            cliente.setId(clienteExistente.getId());
            clientesRepository.save(cliente);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/api/clientes")
    public ResponseEntity find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro,matcher);
        List<Cliente> lista = clientesRepository.findAll(example);
        return ResponseEntity.ok(lista);
    }
}
*/