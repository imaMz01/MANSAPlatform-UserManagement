package com.mansa.user.Repositories;

import com.mansa.user.Entities.Invitation;
import com.mansa.user.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation,String> {

    List<Invitation> findByUser(User user);
}
