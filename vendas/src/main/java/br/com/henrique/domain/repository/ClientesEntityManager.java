package br.com.henrique.domain.repository;

import br.com.henrique.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
@Repository
public class ClientesEntityManager {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente){
        this.entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente){
        this.entityManager.merge(cliente);
        return cliente;
    }
    @Transactional
    public void deletar(int id){
       Cliente cliente = this.entityManager.find(Cliente.class, id);
       this.entityManager.remove(cliente);
    }
    @Transactional
    public void deletar(Cliente cliente){
        if(!entityManager.contains(cliente)){
            cliente = entityManager.merge(cliente);
        }
        this.entityManager.remove(cliente);
    }
    @Transactional
    public List<Cliente> obterTodos(){
        return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
    }
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome){
       String jpql = "select c from Cliente c where c.nome like :nome";
       TypedQuery<Cliente> query = this.entityManager.createQuery(jpql, Cliente.class);
       query.setParameter("nome", "%" + nome + "%");
       return query.getResultList();
    }



}
