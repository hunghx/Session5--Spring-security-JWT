package ra.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private boolean status;
}
