package br.com.henrique.domain.repository;

import br.com.henrique.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static  String INSERT = "insert into cliente (nome) values (?)";
    private static String SELECT_ALL = "select * from cliente";
    private static  String UPDATE ="update cliente set nome = ? where id = ?";
    private static String DELETE = "delete from cliente where id = ?";

    public Cliente salvar(Cliente cliente){
        this.jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }

    public List<Cliente> obterTodos(){
        return this.jdbcTemplate.query(SELECT_ALL, new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"));
            }
        });
    }

    public List<Cliente> buscarPorNome(String nome){
        return this.jdbcTemplate.query(SELECT_ALL.concat(" where nome like ?"), new Object[]{"%" + nome + "%"}, new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Cliente(rs.getInt("id"),rs.getString("nome"));
            }
        });
    }

    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        return cliente;
    }
    public void deletar(int id){
        this.jdbcTemplate.update(DELETE, new Object[]{id});
    }

    public void deletar(Cliente cliente){
        deletar(cliente.getId());
    }
}
