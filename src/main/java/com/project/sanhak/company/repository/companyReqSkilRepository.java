package com.project.sanhak.company.repository;

import com.project.sanhak.domain.company.CompanyReqSkil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface companyReqSkilRepository extends JpaRepository<CompanyReqSkil, Integer> {

}
