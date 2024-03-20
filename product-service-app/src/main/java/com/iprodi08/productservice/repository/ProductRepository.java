package com.iprodi08.productservice.repository;

import com.iprodi08.productservice.entity.Price;
import com.iprodi08.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("""
            SELECT p FROM Product p
             JOIN FETCH p.discounts d
            """)
    List<Product> getAllProductsWithDiscounts();

    @Query("""
            SELECT p FROM Product p
            JOIN FETCH p.discounts d
             WHERE d.id = :discount_id
            """)
    List<Product> getAllProductsWithDiscountsById(
            @Param("discount_id") Long discountId
    );

    @Query("""
            SELECT p FROM Product p
            JOIN FETCH p.discounts d
            WHERE p.id=:product_id AND d.id = :discount_id
            """)
    Optional<Product> getProductByIdWithDiscounts(
            @Param("product_id") Long productId,
            @Param("discount_id") Long discountId
    );

    @Modifying
    @Transactional
    @Query("""
            UPDATE Product p
            SET p.price = :price_value
            WHERE p.id = :id
            """)
    Product updatePriceForProductById(
            @Param("id") Long productId,
            @Param("price_value") Price price
    );

    @Modifying
    @Transactional
    @Query("""
            UPDATE Product p
            SET p.active = :active
            WHERE p.id = :id
            """)
    Product updateActiveOfProductById(
            @Param("id") Long productId,
            @Param("active") Boolean active
    );


}
