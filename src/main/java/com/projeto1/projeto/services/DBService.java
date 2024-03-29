package com.projeto1.projeto.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto1.projeto.domain.Categoria;
import com.projeto1.projeto.domain.Cidade;
import com.projeto1.projeto.domain.Cliente;
import com.projeto1.projeto.domain.Endereco;
import com.projeto1.projeto.domain.Estado;
import com.projeto1.projeto.domain.ItemPedido;
import com.projeto1.projeto.domain.Pagamento;
import com.projeto1.projeto.domain.PagamentoComBoleto;
import com.projeto1.projeto.domain.PagamentoComCartao;
import com.projeto1.projeto.domain.Pedido;
import com.projeto1.projeto.domain.Produto;
import com.projeto1.projeto.domain.enums.EstadoPagamento;
import com.projeto1.projeto.domain.enums.Perfil;
import com.projeto1.projeto.domain.enums.TipoCliente;
import com.projeto1.projeto.repositories.CategoriaRepository;
import com.projeto1.projeto.repositories.CidadeRepository;
import com.projeto1.projeto.repositories.ClienteRepository;
import com.projeto1.projeto.repositories.EnderecoRepository;
import com.projeto1.projeto.repositories.EstadoRepository;
import com.projeto1.projeto.repositories.ItemPedidoRepository;
import com.projeto1.projeto.repositories.PagamentoRepository;
import com.projeto1.projeto.repositories.PedidoRepository;
import com.projeto1.projeto.repositories.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;
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
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public void InstantiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Computadores");
		Categoria cat4 = new Categoria(null, "Mouses");
		Categoria cat5 = new Categoria(null, "Teclados");
		Categoria cat6 = new Categoria(null, "Gamer");
		Categoria cat7 = new Categoria(null, "Outros");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 250.00);
		Produto p5 = new Produto(null, "Teclado", 120.00);
		Produto p6 = new Produto(null, "Tela", 350.00);
		Produto p7 = new Produto(null, "Gabinete gamer", 300.00);
		Produto p8 = new Produto(null, "Estabilizador", 90.00);
		Produto p9 = new Produto(null, "Cabo fonte", 20.00);
		Produto p10 = new Produto(null, "Cabo HDMI", 40.00);
		Produto p11 = new Produto(null, "Mesa digitalizadora", 500.00);
		Produto p12 = new Produto(null, "Mouse Gamer RGB", 120.00);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		Cliente cli1 = new Cliente(null, "Luiz Antonio", "bonberatokisu@gmail.com", "06794157811", TipoCliente.PESSOAFISICA, pe.encode("123") );
		Cliente cli2 = new Cliente(null, "Admin", "bomberatox@gmail.com", "06794187593", TipoCliente.PESSOAFISICA, pe.encode("123") );
		cli2.addPerfil(Perfil.ADMIN);
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apt 203", "Jardim", "3845289842", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "3848726485", cli1, c2);
		Endereco e3 = new Endereco(null, "Rua Paz", "155", null, "Planalto", "3848775183", cli2, c2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2019 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2019 19:35"), cli1, e2);
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2019 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		cli1.getTelefones().addAll(Arrays.asList("991082466", "99710069"));
		cli2.getTelefones().addAll(Arrays.asList("991087843", "99711576"));
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3,p6));
		cat2.getProdutos().addAll(Arrays.asList(p2,p8,p4));
		cat3.getProdutos().addAll(Arrays.asList(p7));
		cat4.getProdutos().addAll(Arrays.asList(p3));
		cat5.getProdutos().addAll(Arrays.asList(p5));
		cat6.getProdutos().addAll(Arrays.asList(p7,p12));
		cat7.getProdutos().addAll(Arrays.asList(p11,p10,p9));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1,cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat5));
		p6.getCategorias().addAll(Arrays.asList(cat1));
		p7.getCategorias().addAll(Arrays.asList(cat3,cat6));
		p8.getCategorias().addAll(Arrays.asList(cat2));
		p9.getCategorias().addAll(Arrays.asList(cat7));
		p10.getCategorias().addAll(Arrays.asList(cat7));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		p12.getCategorias().addAll(Arrays.asList(cat6));
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est1.getCidades().addAll(Arrays.asList(c2,c3));
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12));
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1,e2, e3));
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}
	
}
