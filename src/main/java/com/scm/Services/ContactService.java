package com.scm.Services;

import com.scm.Models.Contact;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ContactService {
    public Contact saveContact(Contact contact, Authentication authentication);
    public List<Contact> getContactsByUserEmail(String email);
    public Contact getContactById(Long Id);
    public Contact updateContact(Contact contact);
    public Page<Contact> getContactsByUserId(Long user_id,int page,int size,String sortBy,String direction);
}
