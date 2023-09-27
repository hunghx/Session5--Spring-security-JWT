package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.ProductRequest;
import ra.dto.response.ProductResponse;
import ra.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(){
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable String id){
        return new ResponseEntity<>(productService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProductResponse> doAdd(@ModelAttribute ProductRequest productRequest){
        return new ResponseEntity<>(productService.save(productRequest),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> doUpdate(@ModelAttribute ProductRequest productRequest,@PathVariable String id){
        productRequest.setId(id);
        return new ResponseEntity<>(productService.save(productRequest),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> delete(@PathVariable String id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
