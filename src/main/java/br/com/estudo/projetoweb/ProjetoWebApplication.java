package br.com.estudo.projetoweb;

import br.com.estudo.projetoweb.domain.Categoria;
import br.com.estudo.projetoweb.domain.Produto;
import br.com.estudo.projetoweb.repositories.CategoriaRepository;
import br.com.estudo.projetoweb.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoWebApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjetoWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria categoria1 = new Categoria("Informática", new ArrayList<>());
        Categoria categoria2 = new Categoria("Escritorio", new ArrayList<>());

        Produto produto1 = new Produto("Computador", BigDecimal.valueOf(2000.00), new ArrayList<>());
        Produto produto2 = new Produto("Cadeira Gamer", BigDecimal.valueOf(800.00), new ArrayList<>());

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2));
        categoria2.getProdutos().addAll(Arrays.asList(produto2));

        produto1.getCategorias().addAll(Arrays.asList(categoria1));
        produto1.getCategorias().addAll(Arrays.asList(categoria1, categoria2));

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));

        produtoRepository.saveAll(Arrays.asList(produto1, produto2));
    }
}
