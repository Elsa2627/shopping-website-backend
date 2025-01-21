package com.shoppingwebsite.shoppingbackend;

import com.shoppingwebsite.shoppingbackend.model.AppUser;
import com.shoppingwebsite.shoppingbackend.model.CustomerOrder;
import com.shoppingwebsite.shoppingbackend.repository.OrderRepository;
import com.shoppingwebsite.shoppingbackend.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shoppingwebsite.shoppingbackend.model.Product;
import com.shoppingwebsite.shoppingbackend.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class ShoppingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository) {
        return args -> {
            productRepository.save(new Product("Apple iPhone 13", "Smartphone by Apple", 799.0, 0, "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/iphone-13-pink-select-2021?wid=470&hei=556&fmt=png-alpha&.v=1645572315937"));
            productRepository.save(new Product("Bose QuietComfort 35 earphone", "Noise-cancelling headphones", 299.0, 8, "https://media.ouest-france.fr/v1/pictures/MjAyMDExMWJjZWIwYTg2NjFlOWIyOTk4MTM5NDMzMmI5YTg5MWY?width=1260&height=708&focuspoint=50%2C25&cropresize=1&client_id=bpeditorial&sign=984646411fb9c062121a3039ac5d921f5cc675409c6224e359068b2a11513629"));
            productRepository.save(new Product("Samsung Galaxy Watch 4", "Smartwatch by Samsung", 249.0, 12, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1N8rGi2go0--omBh5C-IbfGCx6MFgU_qdeA&s"));
            productRepository.save(new Product("Rose Gold Apple Macbook Air", "Lightweight laptop by Apple", 1100.0, 30, "https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/refurb-macbook-air-gold-m1-202010_AV1?wid=1144&hei=1144&fmt=jpeg&qlt=90&.v=1634148580000"));
            productRepository.save(new Product("Iphone 16 Apple", "Latest iPhone model", 1199.0, 20, "https://store.storeimages.cdn-apple.com/1/as-images.apple.com/is/iphone-16-pro-finish-select-202409-6-9inch-deserttitanium?wid=5120&hei=2880&fmt=p-jpg&qlt=80&.v=eUdsd0dIb3VUOXdtWkY0VFUwVE8vbEdkZHNlSjBQRklnaFB2d3I5MW94NW9lRVVkRmJ5ZE03VysydEdnMXpSNEY3eHJKR1hDaEJCS2hmc2czazlldHlSTUMybCtXNXZpclhWeFpYZUcvRk5uNDBzcjA0aG5jQUJ1UTI2VzJJR1lVbWVlSEpnVXRHaTFWbTR2eGk4MUpR&traceId=1"));
            productRepository.save(new Product("Ipad Air 13-inch", "Tablet by Apple", 1300.0, 40, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhUU8gfx7q3CHsczmvXf6Jfh5LvoFA5iTqcg&s"));
            productRepository.save(new Product("Airpods Pro", "Wireless earbuds by Apple", 299.0, 50, "https://www.mymac.dz/wp-content/uploads/2021/02/ecouteurs-airpods-pro-avec-boitier-de-charge-4.jpg"));
            productRepository.save(new Product("Apple Airpods Max", "Over-ear headphones by Apple", 999.0, 57, "https://hatec.fr/98667-large_default/casque-apple-airpods-max-a-reduction-de-bruit-active-argent.jpg"));
            productRepository.save(new Product("Fast charger USB C cable", "High-speed charging cable", 20.0, 200, "https://www.chargeursenligne.com/images/Apple-9V-2A-A1720.jpg"));
            productRepository.save(new Product("Apple Watch", "Smartwatch by Apple", 599.0, 150, "https://www.apple.com/newsroom/images/product/watch/standard/Apple_watch-experience-for-entire-family-hero_09152020_big.jpg.large.jpg"));
            productRepository.save(new Product("Wireless Charge", "Wireless charging pad", 35.0, 300, "https://ma.jumia.is/unsafe/fit-in/500x500/filters:fill(white)/product/51/475936/1.jpg?6345"));
            productRepository.save(new Product("Apple iMac", "Desktop computer by Apple", 1499.0, 250, "https://cdn.myshoptet.com/usr/www.istuff.cz/user/shop/big/3576-1_imac-24-pink-s.jpg?660d0346"));





            AppUser user = new AppUser();
            user.setEmail("test@example.com");
            user.setPassword("password123");
            user.setFirstName("John");
            user.setLastName("Doe");
            userRepository.save(user);


            CustomerOrder order1 = new CustomerOrder();
            order1.setDate(LocalDate.now());
            order1.setStatus(CustomerOrder.OrderStatus.PENDING);
            order1.setTotal(100.0);
            order1.setUser(user);
            orderRepository.save(order1);

            CustomerOrder order2 = new CustomerOrder();
            order2.setDate(LocalDate.now());
            order2.setStatus(CustomerOrder.OrderStatus.SHIPPED);
            order2.setTotal(200.0);
            order2.setUser(user);
            orderRepository.save(order2);
        };
    }






}
