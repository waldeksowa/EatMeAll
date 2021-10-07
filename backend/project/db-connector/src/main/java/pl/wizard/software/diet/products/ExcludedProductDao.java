package pl.wizard.software.diet.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExcludedProductDao extends JpaRepository<ExcludedProductEntity, Long> {

    @Query("select p from ExcludedProductEntity e " +
            "left join e.product p " +
            "where e.memberId = :memberId")
    List<ProductEntity> findByMember(@Param("memberId") Long memberId);
}
