package com.app.ecom.controller;

import java.util.List;

import com.app.ecom.dto.UserResponse;
import com.app.ecom.dto.UserResquest;
import com.app.ecom.service.UserService;
import com.app.ecom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController
{
  private final UserService userService;

  @GetMapping("/api/users")
  private ResponseEntity<List<UserResponse>> getAllUsers()
  {
    return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
  }

  @GetMapping("/api/users/{id}")
  private ResponseEntity<UserResponse> getUser(@PathVariable Long id)
  {
    return userService.fetchUser(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("api/user")
  private void addUser(@RequestBody UserResquest userResquest)
  {
    userService.addUser(userResquest);
  }

  @PutMapping("/api/users/{id}")
  private ResponseEntity<String> updateUser(@PathVariable long id,@RequestBody UserResquest userResquest){
    boolean updated = userService.updatedUser(id, userResquest);
    if (updated) {
      return ResponseEntity.ok("User updated successfully");
    }
    return ResponseEntity.notFound().build();
  }
}
