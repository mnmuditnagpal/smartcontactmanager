package com.scm.Repository;

import com.scm.Models.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

    Page<Contact> findByUserIdAndFavourite(Long user_id, Boolean fav, Pageable pageable);

    Page<Contact> findByUserId(Long user_id, Pageable pageable);




}
