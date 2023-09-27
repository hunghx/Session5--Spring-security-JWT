package ra.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Setter
@Getter
@Builder
public class ProductRequest {
    private String id;
    private String name;
    private double price;
    private String description;
    private MultipartFile file;
    private boolean status;
}
