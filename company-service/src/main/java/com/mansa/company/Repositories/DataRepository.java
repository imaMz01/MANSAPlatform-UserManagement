package com.mansa.company.Repositories;

import com.mansa.company.Entities.Company;
import com.mansa.company.Entities.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Data,String> {
    List<Data> findByCompany(Company company);
}
