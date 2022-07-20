package br.com.henrique.rest.controller;

import br.com.henrique.domain.entity.Produto;
import br.com.henrique.domain.repository.ProdutosRepository;
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
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutosRepository produtosRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody @Valid Produto produto){
        return this.produtosRepository.save(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Produto update(@PathVariable(name = "id") Integer id, @RequestBody @Valid Produto produto){
        this.produtosRepository.findById(id).map(produtoEncontrado ->{
            produto.setId(produtoEncontrado.getId());
            this.produtosRepository.save(produto);
            return produto;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer id){
        this.produtosRepository.findById(id).map(produtoEncontrado -> {
            this.produtosRepository.delete(produtoEncontrado);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Produto> find(Produto produto){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(produto, exampleMatcher);
        List<Produto> list = this.produtosRepository.findAll(example);
        return list;
    }

    @GetMapping("/{id}")
    public Produto findById(@PathVariable Integer id){
        return produtosRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

}
