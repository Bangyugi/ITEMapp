package com.bangvan.EMwebapp.repository;

import com.bangvan.EMwebapp.dto.response.CompanyResponse;
import com.bangvan.EMwebapp.entity.Company;
import com.bangvan.EMwebapp.entity.User;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "select C.id,C.incorporation_date, C.name, C.phone_number, C.register_number, C.website, C.address, C.email, C.vatnumber "+
            "from companies as C join user_company as UC on C.id = UC.company_id "+
            "join users as U on U.id = UC.user_id "+
            "where U.username = :username and C.name = :companyName",nativeQuery = true)
    Company findByName(@Param("companyName") String companyName, @Param("username") String username);

    List<Company> findAllByUsers(Set<User> user);
}
