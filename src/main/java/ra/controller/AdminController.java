package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.service.IUserService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private IUserService userService;
    @GetMapping("/user")
    public ResponseEntity<?> getAllUser(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }
}
