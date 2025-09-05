package org.playground.shoppingcart.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.playground.shoppingcart.entities.Address;
import org.playground.shoppingcart.entities.Category;
import org.playground.shoppingcart.entities.Product;
import org.playground.shoppingcart.entities.User;
import org.playground.shoppingcart.repositories.AddressRepository;
import org.playground.shoppingcart.repositories.ProductRepository;
import org.playground.shoppingcart.repositories.ProfileRepository;
import org.playground.shoppingcart.repositories.UserRepository;
import org.playground.shoppingcart.repositories.specifications.ProductSpec;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final EntityManager entityManager;
    private final ProductRepository productRepository;

    @Transactional
    public void showEntityStates() {
        var user = User.builder()
            .name("Kurniawan")
            .email("kurniawan@example.com")
            .password("password")
            .build();

        if (entityManager.contains(user))
            System.out.println("Persistent");
        else
            System.out.println("Transient / Detached");

        userRepository.save(user);

        if (entityManager.contains(user))
            System.out.println("Persistent");
        else
            System.out.println("Transient / Detached");
    }

    @Transactional
    public void showRelatedEntities() {
        var profile = profileRepository.findById(1L).orElse(null);
        if (profile != null) {
            System.out.println(profile);
        }
    }

    @Transactional
    public void fetchAddress() {
        var address = addressRepository.findById(1L).orElse(null);
        if (address != null) {
            System.out.println(address.getUser().getName());
        }
    }

    public void persistRelated() {
        var user = User.builder()
                .name("Kurniawan")
                .email("kurniawan@example.com")
                .password("password")
                .build();

        var address = Address.builder()
                .street("street")
                .city("city")
                .state("state")
                .zip("zip")
                .build();
        user.addAddress(address);
        userRepository.save(user);
    }

    @Transactional
    public void deleteRelated() {
//        userRepository.deleteById(2L);
        var user = userRepository.findById(3L).orElseThrow();
        var address = user.getAddresses().getFirst();
        user.removeAddress(address);
        userRepository.save(user);
    }

    @Transactional
    public void manageProduct() {
        productRepository.deleteById(6L);
    }

    @Transactional
    public void printLoyalProfiles() {
        var profiles = userRepository.findLoyalUsers(2);
        profiles.forEach(p -> System.out.println(p.getId() + ": " + p.getEmail()));
    }

    public void fetchProducts() {
        var product = new Product();
        product.setName("Product");
        var matcher = ExampleMatcher.matching()
                .withIncludeNullValues()
                .withIgnorePaths("id", "description")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        var example = Example.of(product, matcher);
        var products = productRepository.findAll(example);
        products.forEach(System.out::println);
    }

    public void fetchProductsByCriteria() {
        var products = productRepository.findProductByCriteria(null, BigDecimal.valueOf(1), null);
        products.forEach(System.out::println);
    }


    @Transactional
    public void fetchProductsByCategoryCriteria() {
        var products = productRepository.findProductByCategoryCriteria(new Category((byte)1));
        products.forEach(p -> System.out.println(p.getId()));
    }

    public void fetchProductsBySpecification(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Product> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(ProductSpec.hasName(name));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpec.hasPriceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        }

        productRepository.findAll(spec).forEach(System.out::println);
    }

    public void fetchProductsByCategorySpecification(Category category) {
        Specification<Product> spec = Specification.where(null);

        if (category != null) {
            spec = spec.and(ProductSpec.hasCategory(category));
        }

        productRepository.findAll(spec).forEach(p -> System.out.println(p.getName()));
    }

    public void fetchSortedProducts() {
        var sort = Sort.by("name").and(
                Sort.by("price").descending()
        );
        productRepository.findAll(sort).forEach(System.out::println);
    }

    public void fetchPaginatedProducts(int pageNumber, int size) {
        PageRequest pageRequest = PageRequest.of(pageNumber, size);
        Page<Product> products = productRepository.findAll(pageRequest);

        var page = products.getContent();
        page.forEach(System.out::println);

        System.out.println("Total Pages: " + products.getTotalPages());
        System.out.println("Total products: " + products.getTotalElements());
    }
}
