package com.project.sanhak.company.repository;

import com.project.sanhak.domain.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface companyRepository extends JpaRepository<Company, Integer> {
}
