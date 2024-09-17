package com.dmiit3iy.reminder.controller;

import com.dmiit3iy.reminder.DTO.ResponseResult;
import com.dmiit3iy.reminder.model.User;
import com.dmiit3iy.reminder.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<ResponseResult<User>> add(@RequestBody User user) {
        this.userService.add(user);
        return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<User>> get(@PathVariable("id") long id) {
        User user = this.userService.get(id);
        return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<ResponseResult<User>> delete(@PathVariable("id") long id) {
        User user = userService.delete(id);
        return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
    }
}
