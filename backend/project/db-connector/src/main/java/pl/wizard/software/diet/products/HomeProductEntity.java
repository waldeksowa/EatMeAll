package pl.wizard.software.diet.products;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "HOME_PRODUCTS")
@Data
public class HomeProductEntity extends AbstractBaseEntity {
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private HomeProductTypeEnum HomeProductType;

    public enum HomeProductTypeEnum {
        FAKE("NUMBER_0"), ELECTRONICS("elektronika"), HOUSEHOLD_CHEMICALS ("chemia gospodarcza"), CLOTHES("ubrania"), ANXIETIES ("leki"), OTHERS("inne");

        private String HomeProductType;

        HomeProductTypeEnum(String aType) {
            HomeProductType = aType;
        }

        public String getHomeProductType() {
            return HomeProductType;
        }
    }
}