package com.myprojects.course.config;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.myprojects.course.entities.*;
import com.myprojects.course.entities.dto.OrderDTO;
import com.myprojects.course.entities.dto.ProductDTO;
import com.myprojects.course.entities.dto.ReviewDTO;
import com.myprojects.course.entities.dto.UserDTO;
import com.myprojects.course.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myprojects.course.entities.enums.OrderStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
//ComandLineRunner to execute when the app starts
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public void run(String... args) throws Exception {

		Category c1 = new Category(null, "Electronics");
		Category c2 = new Category(null, "Books");
		Category c3 = new Category(null, "Computers");
		
		Product p1 = new Product(new ProductDTO("The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", "test.url.com", 90.5, Collections.singletonMap("numberOfPages", "500")));
		Product p2 = new Product(new ProductDTO("Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", "test.url.com", 2190.0, Collections.singletonMap("inchesSize", "60")));
		Product p3 = new Product(new ProductDTO("Macbook Pro", "Nam eleifend maximus tortor, at mollis.", "https://http2.mlstatic.com/D_NQ_NP_2X_896426-MLA96891614890_112025-F.webp", 1250.0, Collections.singletonMap("ramMemory", "16 GB")));
		Product p4 = new Product(new ProductDTO("PC Gamer", "Donec aliquet odio ac rhoncus cursus.", "https://images.tcdn.com.br/img/img_prod/740836/pc_gamer_top_concordia_ryzen_5_5600x_16gb_ssd_1tb_placa_3060_fonte_600w_16159_1_cf3cdbf4047d49e563292d2000f3bb4b.png", 1200.0, Collections.singletonMap("ramMemory", "16 GB")));
		Product p5 = new Product(new ProductDTO("Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", "test.url.com", 100.99, Collections.singletonMap("numberOfPages", "400")));

		Review r1 = new Review(new ReviewDTO(5, "Amazing product!", p1));
		Review r2 = new Review(new ReviewDTO(4, "Good product!", p1));
		Review r3 = new Review(new ReviewDTO(2, "Bad product", p2));
		Review r4 = new Review(new ReviewDTO(5, "Great product!", p4));
		Review r5 = new Review(new ReviewDTO(2, "Bad product", p4));

		Address a1 = new Address(null, "Rua A", "ap 33", "Santos", "SP", "Brasil", "99999999");
		Address a2 = new Address(null, "Rua B", "ap 71", "Rio de Janeiro", "RJ", "Brasil", "00000000");

		categoryRepository.saveAll(Arrays.asList(c1, c2, c3));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		reviewRepository.saveAll(Arrays.asList(r1, r2, r3, r4, r5));
		addressRepository.saveAll(Arrays.asList(a1, a2));

		p1.getCategories().add(c2);
		p2.getCategories().add(c1);
		p2.getCategories().add(c3);
		p3.getCategories().add(c3);
		p4.getCategories().add(c3);
		p5.getCategories().add(c2);
		
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User u1 = new User(new UserDTO("Gabriel", "gabriel@gmail.com", "999999999", encoder.encode("12345"), true, List.of("USER_ROLE")));
		User u2 = new User(new UserDTO("Maria", "maria@gmail.com", "988888888", encoder.encode("12345"), true, List.of("USER_ROLE")));

		Order o1 = new Order(new OrderDTO(Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1, Collections.emptySet(), a1));
		Order o2 = new Order(new OrderDTO(Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2, Collections.emptySet(), a2));
		Order o3 = new Order(new OrderDTO(Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1, Collections.emptySet(), a1));
				
		userRepository.saveAll(Arrays.asList(u1, u2));
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));

		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());

		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));
		
		Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
		o1.setPayment(pay1);
		
		orderRepository.save(o1);
	}
}
