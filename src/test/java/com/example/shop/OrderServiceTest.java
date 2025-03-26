package com.example.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.shop.model.Product;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ProductRepository;

@SpringBootTest
class OrderServiceTest {

	@Autowired
	OrderService orderService;

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	ProductRepository productRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setup() {
		jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0;");
		jdbcTemplate.execute("TRUNCATE TABLE order_tb;");
		jdbcTemplate.execute("TRUNCATE TABLE product_tb;");
		jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=1;");

		final var product = Product.builder()
				.name("지드래곤 콘서트")
				.price(100000)
				.quantity(100)
				.build();
		productRepo.save(product);
	}

	@Test
	void order_test() throws InterruptedException {
		// given
		final int requestCount = 1000;
		final ExecutorService executorService = Executors.newCachedThreadPool();
		final CountDownLatch countDownLatch = new CountDownLatch(requestCount);

		// when
		for (int i = 0; i < requestCount; i++) {
			final long userId = i;
			executorService.submit(() -> {
				try {
					orderService.orderProduct(1L, (long) userId);
				} catch (Exception e) {
					System.out.println(e.getMessage() + " : " + userId);
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();

		System.out.println("orderRepo.count() : " + orderRepo.count());

		// then
		assertEquals(orderRepo.count(), 100L);
	}

}
