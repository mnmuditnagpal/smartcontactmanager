package com.scm.Services;

import com.scm.Dtos.VerificationResponseDto;
import com.scm.Helper.Status;
import com.scm.Models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public Status registerUser(User user);
    public User getUserById(Long id);
    public User updateUser(User user);
    public ResponseEntity<HttpStatus> deleteUser();
    public User getUserByEmail(String email);
    public boolean isUserExist(String email);
    public boolean isEmailVerified(String email);
    public User updateAboutUser(String email,String about);
    public VerificationResponseDto verifyUserEmail(String emailVerificationKey);
}
