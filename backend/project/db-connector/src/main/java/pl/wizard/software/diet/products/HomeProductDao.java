package pl.wizard.software.diet.products;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeProductDao extends JpaRepository<HomeProductEntity, Long> {
}

