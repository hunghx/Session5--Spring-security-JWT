package ra.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;


@Setter
@Getter
@Builder
public class ProductRequest {
    private Long id;
    @Size(min = 6,message = "Tổi thiếu 6 kí tự")
    private String name;
    @Min(value = 0 , message = "Giá tổi thiểu băng 0")
    private double price;
//    @NotNull // không được null
//    @NotBlank // không được null và ko được là khoảng trắng (cắt bớt khoảng trắng)
//    @NotEmpty // ko châp nhận null, chuỗi rỗng ""
    // qui định kích thước chuỗi
//    @Size(min = 10,max=100)
//    @Pattern(regexp = "^S\\w{15}$")
    @NotBlank(message = "Không được để trống")
    private String description;
    @NotNull(message = "Bạn phải chọn file")
    private MultipartFile file;
    private boolean status;
//    @Min(20) // giả trị nhỏ nhất
//    @Max(100) // giá trị lớn nhất
//    private int stock;
}
