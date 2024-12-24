package com.mansa.company.Repositories;

import com.mansa.company.Entities.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<Data,String> {
}
