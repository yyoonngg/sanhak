package com.project.sanhak.company.repository;

import com.project.sanhak.domain.company.CompanyReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface companyReqRepository extends JpaRepository<CompanyReq, Integer> {
}
