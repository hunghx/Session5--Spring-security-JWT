package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.ProductRequest;
import ra.dto.response.ProductResponse;
import ra.exception.ProductExceptionValidate;
import ra.service.impl.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(){
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }
//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id){
        return new ResponseEntity<>(productService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProductResponse> doAdd(@Valid @ModelAttribute ProductRequest productRequest) throws ProductExceptionValidate {
        if(productService.existByProductName(productRequest.getName())){
            throw new ProductExceptionValidate("Tên sản phẩm đã tồn tại");
        }
        return new ResponseEntity<>(productService.save(productRequest),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> doUpdate(@Valid @ModelAttribute ProductRequest productRequest,@PathVariable String id) throws ProductExceptionValidate {
        if(productService.existByProductName(productRequest.getName())){
            throw new ProductExceptionValidate("Tên sản phẩm đã tồn tại");
        }
        productRequest.setId(Long.valueOf(id));
        return new ResponseEntity<>(productService.save(productRequest),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> delete(@PathVariable Long id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
