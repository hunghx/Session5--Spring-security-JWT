package ra.service;

import com.amazonaws.services.kms.model.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dto.request.ProductRequest;
import ra.dto.response.ProductResponse;
import ra.entity.Product;
import ra.repository.IProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired UploadService uploadService;
    public List<ProductResponse> findAll(){
        return productRepository.findAll().stream().map(p->ProductResponse.builder()
                .id(p.getId())
                .price(p.getPrice())
                .description(p.getDescription())
                .name(p.getName())
                .status(p.isStatus())
                .imageUrl(uploadService.getUrlFromFilename(p.getImageUrl()))
                .build()).collect(Collectors.toList());
    }
    public ProductResponse findById(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(product -> ProductResponse.builder()
                .id(product.getId())
                .price(product.getPrice())
                .description(product.getDescription())
                .name(product.getName())
                .status(product.isStatus())
                .imageUrl(uploadService.getUrlFromFilename(product.getImageUrl()))
                .build()).orElse(null);
    }
    public ProductResponse save(ProductRequest productRequest){
        // xử lí upload file : common upload file
        Product product = null;
        if(productRequest.getId() == null){
            // thêm mới

            String fileName = null;
            if (!productRequest.getFile().isEmpty()){
                // up load file
                fileName = uploadService.uploadFile(productRequest.getFile());
            }
             product= Product.builder()
                     .name(productRequest.getName())
                     .price(productRequest.getPrice())
                     .status(productRequest.isStatus())
                     .description(productRequest.getDescription())
                     .imageUrl(fileName)
                     .build();
        }else {
            // lấy thông tin cũ về
            Product oldProduct = productRepository.findById(productRequest.getId()).orElse(null);
            // cập nhật
            if (oldProduct==null){
                throw  new NotFoundException("product ko ton tai");
            }
            String fileName = oldProduct.getImageUrl();
            if (!productRequest.getFile().isEmpty()){
                // up load file
                fileName = uploadService.uploadFile(productRequest.getFile());
                // xóa file cũ
//                uploadService.deleteFile(oldProduct.getImageUrl());

            }
            product = Product.builder()
                    .id(productRequest.getId())
                    .name(productRequest.getName())
                    .price(productRequest.getPrice())
                    .description(productRequest.getDescription())
                    .status(productRequest.isStatus())
                    .imageUrl(fileName).build();
        }

        Product p = productRepository.save(product);
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .description(p.getDescription())
                .status(p.isStatus())
                .imageUrl(uploadService.getUrlFromFilename(p.getImageUrl())).build();
    }
    public void delete(Long id){
        // xóa ảnh
//        uploadService.deleteFile(findById(id).getImageUrl());
        productRepository.deleteById(id);
    }
    public boolean existByProductName(String name){
       return productRepository.existsByName(name);
    }
}
