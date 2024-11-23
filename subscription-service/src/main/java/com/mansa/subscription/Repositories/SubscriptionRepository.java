package com.mansa.subscription.Repositories;


import com.mansa.subscription.Entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,String> {
    List<Subscription> findByUserId(String id);
}
