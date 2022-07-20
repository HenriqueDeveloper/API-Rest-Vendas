package br.com.henrique;

import br.com.henrique.domain.entity.Cliente;
import br.com.henrique.domain.repository.ClientesEntityManager;
import br.com.henrique.domain.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}

