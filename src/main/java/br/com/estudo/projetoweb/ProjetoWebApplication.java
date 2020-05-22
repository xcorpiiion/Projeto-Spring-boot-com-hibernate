package br.com.estudo.projetoweb;

import br.com.estudo.projetoweb.domain.*;
import br.com.estudo.projetoweb.domain.enums.EnumEstadoPagamento;
import br.com.estudo.projetoweb.domain.enums.EnumTipoCliente;
import br.com.estudo.projetoweb.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoWebApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjetoWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria categoria1 = new Categoria("Informática", new ArrayList<>());
        Categoria categoria2 = new Categoria("Escritorio", new ArrayList<>());

        Produto produto1 = new Produto("Computador", BigDecimal.valueOf(2000.00), new ArrayList<>());
        Produto produto2 = new Produto("Cadeira Gamer", BigDecimal.valueOf(800.00), new ArrayList<>());

        Estado estado1 = new Estado("São Paulo", new ArrayList<>());
        Estado estado2 = new Estado("Rio de Janeiro", new ArrayList<>());

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2));
        categoria2.getProdutos().addAll(Arrays.asList(produto2));

        produto1.getCategorias().addAll(Arrays.asList(categoria1));
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));

        Cidade cidade1 = new Cidade("São Paulo", estado1);
        Cidade cidade2 = new Cidade("Niteroi", estado2);

        estado1.getCidades().addAll(Arrays.asList(cidade1, cidade2));
        estado2.getCidades().addAll(Arrays.asList(cidade2));

        Cliente cliente1 = new Cliente("Laxus", "laxus@gmail.com", "45071341883", EnumTipoCliente.PESSOAFISICA, new ArrayList<>(), new HashSet<>(), new ArrayList<>());
        Cliente cliente2 = new Cliente("Natsu", "natsu@gmail.com", "45076541003", EnumTipoCliente.PESSOAJURIDICA, new ArrayList<>(), new HashSet<>(), new ArrayList<>());

        cliente1.getTelefones().addAll(Arrays.asList("1139852913"));
        cliente2.getTelefones().addAll(Arrays.asList("11958004508"));

        Endereco endereco = new Endereco("Rua flores", "300", "apto 300", "jardim", "02676020", cliente1, cidade1);


        Pedido pedido1 = new Pedido(new Date(), cliente1, endereco);
        Pagamento pagamento1 = new PagamentoCartao(EnumEstadoPagamento.PEDENTE, pedido1, 5);
        Pagamento pagamento2 = new PagamentoBoleto(EnumEstadoPagamento.CANCELADO, pedido1, new Date(), new Date());

        pedido1.setPagamento(pagamento1);

        pagamento1.setPedido(pedido1);
        pagamento2.setPedido(pedido1);

        cliente1.setPedidos(Arrays.asList(pedido1));
        cliente2.setPedidos(Arrays.asList(pedido1));

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2));
        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2));
        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
        enderecoRepository.saveAll(Arrays.asList(endereco));
        pedidoRepository.saveAll(Arrays.asList(pedido1));
    }
}
