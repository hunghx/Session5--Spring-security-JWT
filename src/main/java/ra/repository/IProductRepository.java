package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName(String name);
    @Query(nativeQuery = true,value = "select avg(price) from product")
    double getAvgProductPrice();
}
