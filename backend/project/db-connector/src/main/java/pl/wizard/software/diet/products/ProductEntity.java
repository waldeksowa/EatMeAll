package pl.wizard.software.diet.products;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table(name = "PRODUCTS")
@Data
public class ProductEntity extends AbstractBaseEntity {
    private String name;
    private Double calorific;
    private Double protein;
    private Double fat;
    private Double carbohydrates;
    private Double roughage;
    @Enumerated(EnumType.ORDINAL)
    private ProductTypeEnum productType;

    public enum ProductTypeEnum {
        FAKE("NUMBER_0"), CEREALS("zboża"), DAIRY("przetwory mleczne"), HAMS("wędliny"), RAW_MEAT("mięso"), POULTRY("drób"), EGGS("jajka"), FATS("tłuszcze"), MUSHROMS("grzyby"), NUTS("orzechy"),
        FRUITS("owoce"), SPICES("przyprawy"), FISH("ryby"), VEGETABLES("warzywa"), ALCOHOLS("alkohole"), DRINKS("napoje"), SWEETS("słodycze"), OTHERS("inne");

        private String productType;

        ProductTypeEnum(String aType) {
            productType = aType;
        }

        public String getProductType() {
            return productType;
        }
    }
}
