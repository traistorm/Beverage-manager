package com.example.beveragemanager.Service;

import com.example.beveragemanager.DTO.UserDTO;
import com.example.beveragemanager.DTO.UserDTOReturnClient;
import com.example.beveragemanager.Entiry.User;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import com.example.beveragemanager.Reponsitory.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public UserDTO login(String username, String password, String tokenValue)
    {
        UserDTO userDTO = new UserDTO();
        // Generate token
        User user;// Return token
        if (tokenValue != null)
        {
            user = userRepository.findByToken(tokenValue);
            if (user != null)
            {
                Date date = new Date();
                if ((user.getInitializationtokentime() + 60L * 60 * 1000 * 24 * 365) > date.getTime())
                {
                    userDTO.setUser(user);
                    userDTO.setResult("Token is valid");
                    //tokenService.delete(token);
                }
                else // Token timeout
                {
                    user.setToken(""); // Reset token from database
                    userDTO.setResult("Token timeout");
                }
            }
            else
            {
                //userDTO.setToken(token);
                userDTO.setResult("Token is invalid");
            }

        }
        else
        {
            user = userRepository.findByUsername(username);
            if (user != null)
            {
                System.out.println(user.getPassword());
                boolean valuate = BCrypt.checkpw(password, user.getPassword());
                if (valuate)
                {
                    Random random = ThreadLocalRandom.current();
                    byte[] randomBytes = new byte[32];
                    random.nextBytes(randomBytes);
                    Date date = new Date();
                    user.setToken(Base64.getUrlEncoder().encodeToString(randomBytes));
                    user.setInitializationtokentime(date.getTime());
                    userRepository.save(user);
                    userDTO.setResult("Login success");
                    userDTO.setUser(user);
                }
                else
                {
                    userDTO.setResult("Password is incorrect");
                }

            }
            else
            {
                userDTO.setResult("User not found");
            }
        }
        return userDTO;

    }
    public ResponseEntity<UserDTOReturnClient> findAll(String token, Integer page, Integer itemPerPage)
    {
        try
        {
            UserDTO userDTO = login(null, null, token);
            if (userDTO.getResult().equals("Token is valid"))
            {
                UserDTOReturnClient userDTOReturnClient = new UserDTOReturnClient();
                List<User> userList = userRepository.findAll();
                if (page != null && itemPerPage != null)
                {
                    List<User> userListReturn = userRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("userid").ascending())).getContent();
                    HeaderReturnMix info = new HeaderReturnMix();
                    info.setMaxPage((int) ((userRepository.findAll().size() / itemPerPage) + 1));
                    info.setCurrentPage(page);
                    info.setItemPerPage(itemPerPage);
                    userDTOReturnClient.setInfo(info);
                    userDTOReturnClient.setUserList(userListReturn);
                }
                else
                {
                    HeaderReturnMix info = new HeaderReturnMix();
                    info.setMaxPage(null);
                    info.setCurrentPage(null);
                    info.setItemPerPage(null);
                    userDTOReturnClient.setInfo(info);
                    userDTOReturnClient.setUserList(userList);
                }
                return new ResponseEntity<>(userDTOReturnClient, HttpStatus.OK);
            }
            else if (userDTO.getResult().equals("Token timeout"))
            {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            else
            {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
}
