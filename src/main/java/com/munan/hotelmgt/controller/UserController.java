package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.dto.RoleListDto;
import com.munan.hotelmgt.dto.UserDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.AppUser;
import com.munan.hotelmgt.service.AppUserService;
import com.munan.hotelmgt.utils.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author godwi
 */

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "User Controller")
@RequiredArgsConstructor
public class UserController {
    private final AppUserService userService;
    
    //ADD
    @Operation(summary = "ADD USER", description = "Add a new User")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addUser(@RequestBody UserDto userDto) throws NotFoundException, AlreadyExistException{
       return userService.add(userDto);

    }
    
    @Operation(summary = "ADD ROLE TO USER", description = "Add roles to a user")
    @PostMapping("/add/roles/{user_id}")
    public ResponseEntity<HttpResponse<?>> addRolesToUser(@PathVariable(value = "user_id") Long uId, @RequestBody RoleListDto roleList) throws AlreadyExistException, NotFoundException, Exception {
        return userService.addRoleList(uId, roleList);
    }
    
    
    //GET
    @Operation(summary = "RETRIEVE ALL", description = "Retrieve all users")
    @GetMapping("/get")
    public ResponseEntity<HttpResponse<?>> getAllUsers() {
        return userService.getAll();
    }
    
    @Operation(summary = "GET BY ID", description = "Retrieve User by id")
    @GetMapping("/get/{user_id}")
    public ResponseEntity<HttpResponse<?>> getUserById(@PathVariable(value = "user_id") Long id) throws NotFoundException {
        return userService.getById(id);
    }
    
    @Operation(summary = "GET BY ROLE ID", description = "Retrieve User by role id")
    @GetMapping("/get/role/{role_id}")
    public ResponseEntity<HttpResponse<?>> getUserByRoleId(@PathVariable(value = "role_id") Long id) throws NotFoundException {
        return userService.getByRoleId(id);
    }
    
    //DELETE 
    @Operation(summary = "DELETE BY ID", description = "Remove single user by id")
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<HttpResponse<?>> deleteUserById(@PathVariable(value = "user_id") Long id) throws NotFoundException {
        return userService.deleteById(id);
    }
    
    @Operation(summary = "REMOVE ROLE FROM USER BY ID", description = "Remove single user by id")
    @DeleteMapping("/remove/roles/{user_id}")
    public ResponseEntity<HttpResponse<?>> removeRoleFromUser(@PathVariable(value = "user_id") Long uId,@RequestBody RoleListDto roleList ) throws NotFoundException {
        return userService.removeRoleList(uId, roleList);
    }
    
    
    //UPDATE
    @Operation(summary = "UPDATE USER", description = "Update user record")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateUser(@RequestBody AppUser user) throws NotFoundException{
        return userService.update(user);
    }
    
}
