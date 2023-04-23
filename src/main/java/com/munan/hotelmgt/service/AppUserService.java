package com.munan.hotelmgt.service;


import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.dto.RoleListDto;
import com.munan.hotelmgt.dto.UserDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.AppUser;
import com.munan.hotelmgt.model.Role;
import com.munan.hotelmgt.repository.AppUserRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService {
    
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository userRepository;
    
    private final RoleService roleService;

    
    
    //ADD NEW USER 
//    public AppUser addUser(UserDto userDto) throws AlreadyExistException, NotFoundException{
//    
//        Optional<AppUser> findUser = userRepository.findByUsername(userDto.getUsername());
//
//        if(findUser.isPresent()){
//            throw new AlreadyExistException("User "+userDto.getUsername()+" already exist");
//        }
//        
//        AppUser newUser = new AppUser();
//        newUser.setFirstName(userDto.getFirstName());
//        newUser.setLastName(userDto.getLastName());
//        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        newUser.setUsername(userDto.getUsername());
//
//        return newUser;
//    }

    //ADD NEW USER WITH ROLE LIST
    public ResponseEntity<HttpResponse<?>> add(UserDto userDto) throws AlreadyExistException, NotFoundException{
        
        System.out.println("Roles: "+userDto.getRoleIds());
        AppUser newUser = addUserFromDto(userDto);
        
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        userRepository.save(newUser))
        );
    }
    
    //ADD ROLES TO A USER
    public ResponseEntity<HttpResponse<?>> addRoleList(Long uId, RoleListDto roleList) throws NotFoundException {
        AppUser user = findUserById(uId);
        AppUser updateUser = addRoleToUser(user, roleList.getRoles());
        
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        userRepository.save(updateUser)));    
    }
    
    //GET ALL USERS
    public ResponseEntity<HttpResponse<?>> getAll() {
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        userRepository.findAll()));
    }
    
    //GET USER BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {
        AppUser user = findUserById(id);
        
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        user));
    }
    
    //GET USER BY ID
    public ResponseEntity<HttpResponse<?>> getByRoleId(Long id) throws NotFoundException {
        Role role = roleService.findById(id);
        
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        userRepository.findByRoles_id(role.getId()))
        );
    }
    
    

    //DELETE USER BY ID
    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {
        AppUser findUser = findUserById(id);
        
        findUser = removeRoleFromUser(findUser);
        
        findUser.setDeleted(true);

        String name = findUser.getFirstName();

        userRepository.delete(findUser);

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "User with name " + name +" has been deleted")
        );
    }
    
    //REMOVE ROLE FROM USER
    public ResponseEntity<HttpResponse<?>> removeRoleList(Long uId, RoleListDto roleList) throws NotFoundException {
        AppUser user = findUserById(uId);
        
        roleList.getRoles().stream().forEach(role ->{
            user.removeRole(role.getId());
            });

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        userRepository.save(user))
        );
    }
    
    //UPDATE USER
    public ResponseEntity<HttpResponse<?>> update(AppUser user) throws NotFoundException {
        AppUser savedUser = addUserFromUser(user);
        
        if(user.getId() != null){
            savedUser.setId(user.getId());
        }
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        userRepository.save(savedUser))
        );
    }
    
    //FIND USER BY ID 
    public AppUser findUserById(Long id) throws NotFoundException{        
        return userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User with id "+id+", Does not exist"));
    }
    
    //PRIVATE METHOD TO ADD NEW USER FROM DTO
    private AppUser addUserFromDto(UserDto user) throws NotFoundException, AlreadyExistException{
    
        Optional<AppUser> findUser = userRepository.findByUsername(user.getUsername());

        if(findUser.isPresent()){
            throw new AlreadyExistException("User "+user.getUsername()+" already exist");
        }
        AppUser newUser = new AppUser();
        
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return addRoleIdToUser(newUser,user.getRoleIds());
    }
    
    //PRIVATE METHOD TO ADD NEW USER FROM USER
    private AppUser addUserFromUser(AppUser user) throws NotFoundException{
    
        AppUser newUser = findUserById(user.getId());
        
        
        if(user.getFirstName() != null){
            newUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null){
            newUser.setLastName(user.getLastName());
        }
        
        if(user.getUsername() != null){
            newUser.setUsername(user.getUsername());
        }
        
        if(user.getPassword() != null){
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        if(user.getRoles() != null){
            newUser.setRoles(user.getRoles());
        }
       
        return newUser;
    }
    
    //PRIVATE METHOD TO ADD ROLE LIST TO USER
    private AppUser addRoleIdToUser(AppUser user, List<Long> roleIds) throws NotFoundException{
            
        Iterator<Long> role = roleIds.iterator();
        System.out.println("Roles: "+roleIds);
        while(role.hasNext()){
            Role newRole = roleService.findById(role.next());
            
            user.addRole(newRole);
        }
        return user;
    }
    
    
     //PRIVATE METHOD TO ADD ROLE LIST TO USER
    private AppUser addRoleToUser(AppUser user, List<Role> role) throws NotFoundException{
            
        Iterator<Role> newrole = role.iterator();
        while(newrole.hasNext()){
            Role newRole = roleService.findById(newrole.next().getId());
            
            user.addRole(newRole);
        }
        return user;
    }
    
    
    
    //PRIVATE METHOD TO REMOVE ROOM FROM GUEST
    private AppUser removeRoleFromUser(AppUser user){
    
        user.getRoles().stream().forEach(role ->{
            user.removeRole(role.getId());
        });
        
        return userRepository.save(user);
    }

    

    

    

    

    

    
    
}
