package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.products.HomeProductDao;
import pl.wizard.software.diet.products.HomeProductEntity;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeProductService {
    private final HomeProductDao homeProductDao;

    Collection<HomeProductWithTypeDto> findAll() {
        List<HomeProductEntity.HomeProductTypeEnum> homeProductTypes = Arrays.stream(HomeProductEntity.HomeProductTypeEnum.values()).collect(Collectors.toList());
        HashMap<HomeProductEntity.HomeProductTypeEnum, HomeProductWithTypeDto> hashMap = new HashMap<>();
        homeProductTypes.forEach(pt -> hashMap.put(pt, new HomeProductWithTypeDto(pt.getHomeProductType())));
        homeProductDao.findAll().forEach(p -> {
            HomeProductWithTypeDto homeProductType = hashMap.get(p.getHomeProductType());
            homeProductType.addProd(p);
        });
        return hashMap.values();
    }

    public Optional<HomeProductEntity> findById(Long id) {
        return homeProductDao.findById(id);
    }

    public HomeProductEntity save(HomeProductEntity stock) {
        return homeProductDao.save(stock);
    }

    public void deleteById(Long id) {
        homeProductDao.deleteById(id);
    }
}