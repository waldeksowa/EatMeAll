package pl.wizard.software.diet.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExcludedProductDao extends JpaRepository<ExcludedProductEntity, Long> {

    @Query(value = "select p.id from products p " +
            "left join excluded_products e on e.product_id = p.id " +
            "where e.member_id = ?1", nativeQuery = true)
    List<Long> findByMember(Long memberId);
}
