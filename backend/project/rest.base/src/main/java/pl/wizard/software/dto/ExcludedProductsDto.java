package pl.wizard.software.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcludedProductsDto {
    private Long memberId;
    private List<Long> products;
}
