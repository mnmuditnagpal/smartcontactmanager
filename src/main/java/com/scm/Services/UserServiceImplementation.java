package com.scm.Services;

import com.scm.Dtos.VerificationResponseDto;
import com.scm.Exceptions.ResourceNotFoundException;
import com.scm.Helper.MessageContent;
import com.scm.Helper.MessageType;
import com.scm.Helper.Status;
import com.scm.Models.User;
import com.scm.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final HttpSession session;


    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder,EmailService emailService,HttpSession session) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.session = session;
    }

    public void updateSessionDetails(User user){
        session.setAttribute("loggedInUser",user);
    }

    @Override
    public Status registerUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()){
            return Status.FAILURE;
        }
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        String userEmailVerificationKey = UUID.randomUUID().toString();
        user.setEmailVerificationKey(userEmailVerificationKey);

        userRepository.save(user);

        String activationLink = "http://localhost:8081/auth/verifyaccount?token="+userEmailVerificationKey;

        emailService.sendEmail(
                user.getEmail(),
                "Accout Verification Link SmartContactManager",
                "Hi User,\n \nwelcome to SCM!! To activate your account, please click on the link shared below\n\n"+activationLink
                        +"\n\nThanks\n"
                        +"SCM Support Team"

        );

        return Status.SUCCESS;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new ResourceNotFoundException("User Id does not exist");
        }
        return userOptional.get();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteUser() {
        User user = (User)session.getAttribute("loggedInUser");
        userRepository.deleteById(
                user.getId()
        );
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }

    @Override
    public boolean isUserExist(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    @Override
    public boolean isEmailVerified(String email) {
        User user = (User)session.getAttribute("loggedInUser");
        return user.isEmailVerified();
    }

    @Override
    public User updateAboutUser(String email, String about) {
        User user = getUserByEmail(email);
        user.setAbout(about);
        return userRepository.save(user);
    }

    @Override
    public VerificationResponseDto verifyUserEmail(String emailVerificationKey) {
        Optional<User> optionalUser = userRepository.findByEmailVerificationKey(emailVerificationKey);
        VerificationResponseDto verificationResponseDto = new VerificationResponseDto();
        MessageContent messageContent = new MessageContent();
        if(optionalUser.isEmpty()){
            verificationResponseDto.setStatus(Status.FAILURE);
            messageContent.setMessageType(MessageType.red);
            messageContent.setMessage("Unable to verify your account");
            verificationResponseDto.setMessageContent(messageContent);
            return verificationResponseDto;
        }

        User user = optionalUser.get();
        if(user.isEnabled()){
            verificationResponseDto.setStatus(Status.SUCCESS);
            messageContent.setMessageType(MessageType.green);
            messageContent.setMessage("Your account is already verified. Please login to continue.");
            verificationResponseDto.setMessageContent(messageContent);
            return verificationResponseDto;
        }
        user.setEnabled(true);
        user.setEmailVerified(true);
        userRepository.save(user);

        verificationResponseDto.setStatus(Status.SUCCESS);
        messageContent.setMessageType(MessageType.green);
        messageContent.setMessage("Thanks for verification!! Your account is now verified. Please login to continue.");
        verificationResponseDto.setMessageContent(messageContent);
        return verificationResponseDto;

    }

}
