package br.com.henrique.domain.repository;

import br.com.henrique.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {
}
