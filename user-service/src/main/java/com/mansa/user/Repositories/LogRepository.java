package com.mansa.user.Repositories;

import com.mansa.user.Entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log,String> {
}
