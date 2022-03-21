package com.learn.sample.shopping.cart;

import com.learn.sample.shopping.cart.entity.Customer;
import com.learn.sample.shopping.cart.entity.Product;
import com.learn.sample.shopping.cart.entity.ShoppingCart;
import com.learn.sample.shopping.cart.entity.Stock;
import com.learn.sample.shopping.cart.entity.repositories.CustomerRepository;
import com.learn.sample.shopping.cart.entity.repositories.ProductRepository;
import com.learn.sample.shopping.cart.entity.repositories.ShoppingCartRepository;
import com.learn.sample.shopping.cart.entity.repositories.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RepositoryTests {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCustomerCreate() {
        Customer saro = customerRepository.save(Customer.builder()
                .firstName("Sarawanan")
                .lastName("Mahalingam")
                .address("test address")
                .build());
        assertEquals("Sarawanan", saro.getFirstName());
    }

    @Test
    public void testProductCreate() {
        Product rice = productRepository.save(Product.builder()
                .name("White Rice")
                .description("White Rice 1KG")
                .price(20f)
                .build());
        assertEquals("White Rice", rice.getName());
    }

    @Test
    public void testStockCreate() {
        Product rice = entityManager.persist(Product.builder()
                .name("White Rice")
                .description("White Rice 1KG")
                .price(20f)
                .build());
        Stock stock = stockRepository.save(Stock.builder()
                .product(rice)
                .quantity(100)
                .build());
        assertEquals(100, stock.getQuantity());
    }

    @Test
    public void testShoppingCartCreate() {
        Product rice = entityManager.persist(Product.builder()
                .name("White Rice")
                .description("White Rice 1KG")
                .price(20f)
                .build());
        Customer customer = entityManager.persist(Customer.builder()
                .firstName("Sarawanan")
                .lastName("Mahalingam")
                .address("test address")
                .build());
        entityManager.flush();

        Stock stock = entityManager.persistFlushFind(Stock.builder()
                .product(rice)
                .quantity(100)
                .build());
        ShoppingCart shoppingCart = shoppingCartRepository.save(
                ShoppingCart.builder()
                        .customer(customer)
                        .product(rice)
                        .quantity(2)
                        .build()
        );
        System.out.println(shoppingCart);
        assertEquals(2, shoppingCart.getQuantity());
        assertEquals(rice, shoppingCart.getProduct());
        stock.setQuantity(stock.getQuantity() - shoppingCart.getQuantity());
        System.out.println(stock);
        assertEquals(98, stock.getQuantity());
    }
}
