package com.scm.Services;


import com.scm.Configuration.FetchUserEmail;
import com.scm.Exceptions.ResourceNotFoundException;
import com.scm.Models.Contact;
import com.scm.Models.User;
import com.scm.Repository.ContactRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImplementation implements ContactService {

    private final UserServiceImplementation userServiceImplementation;

    private final ContactRepository contactRepository;

    private final HttpSession session;

    public ContactServiceImplementation(UserServiceImplementation userServiceImplementation, ContactRepository contactRepository,HttpSession session) {
        this.userServiceImplementation = userServiceImplementation;
        this.contactRepository = contactRepository;
        this.session = session;
    }

    public void updateSessionDetails(User user){
        session.setAttribute("loggedInUser",user);
    }

    @Override
    public Contact saveContact(Contact contact, Authentication authentication){
        String email = FetchUserEmail.getLoggedInUserEmail(authentication);
        User user = userServiceImplementation.getUserByEmail(email);
        Integer favCounts = user.getFavCount();
        contact.setUser(user);
        if(contact.isFavourite()){
            favCounts++;
        }
        user.setFavCount(favCounts);
        user.setTotalContacts(user.getTotalContacts()+1);
        userServiceImplementation.updateUser(user);
        updateSessionDetails(user);
        return contactRepository.save(contact);
    }


    @Override
    public List<Contact> getContactsByUserEmail(String email) {
        return List.of();
    }

    @Override
    public Contact getContactById(Long Id) {

        Optional<Contact> contactOptional = contactRepository.findById(Id);
        if(contactOptional.isEmpty()){
            throw new ResourceNotFoundException("ID Does not exist");
        }

        return contactOptional.get();
    }

    @Override
    public Contact updateContact(Contact contact) {
        return null;
    }

    @Override
    public Page<Contact> getContactsByUserId(Long user_id, int page, int size, String sortBy,String direction) {

        Pageable pageRequest = PageRequest.of(
                page,
                size,
                direction.equalsIgnoreCase("desc")
                        ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending()
        );

        return contactRepository.findByUserId(user_id,pageRequest);
    }

    public void toggleFav(Long id){
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if(contactOptional.isEmpty()){
            throw new ResourceNotFoundException("No Contact With Given ID");
        }
        Contact contact = contactOptional.get();
        User user = userServiceImplementation.getUserById(contact.getUser().getId());
        if(contact.isFavourite()){
            user.setFavCount(user.getFavCount()-1);
        }else{
            user.setFavCount(user.getFavCount()+1);
        }
        userServiceImplementation.updateUser(user);

        contact.setFavourite(
                !contact.isFavourite()
        );
        contactRepository.save(contact);
    }

    public ResponseEntity<HttpStatus> deleteContact(Long id){
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if(contactOptional.isEmpty()){
            throw new ResourceNotFoundException("No Contact");
        }
        User user = contactOptional.get().getUser();
        if(contactOptional.get().isFavourite()){
            user.setFavCount(user.getFavCount()-1);
        }
        user.setTotalContacts(user.getTotalContacts()-1);
        userServiceImplementation.updateUser(user);
        session.setAttribute("loggedInUser",user);
        contactRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
