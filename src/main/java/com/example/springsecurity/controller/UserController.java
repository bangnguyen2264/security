package com.example.springsecurity.controller;

import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.model.form.ChangePasswordForm;
import com.example.springsecurity.model.form.UpdateForm;
import com.example.springsecurity.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(){
         return ResponseEntity.ok(userService.getAll());
     }
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam(name = "query") String query) {
        return ResponseEntity.ok(userService.searchUser(query));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getById(Long id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordForm request, Principal connectedUser) {
        return ResponseEntity.ok(userService.changePassword(request, connectedUser));
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> update(@PathVariable Long userId, @RequestBody UpdateForm form){
        return ResponseEntity.ok(userService.update(userId,form));
    }
    @DeleteMapping("/delete")
    public  ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(userService.delete(id));
    }
}
