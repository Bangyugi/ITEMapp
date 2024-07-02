package com.bangvan.EMwebapp.repository;

import com.bangvan.EMwebapp.dto.response.CompanyResponse;
import com.bangvan.EMwebapp.dto.response.EmployeeResponse;
import com.bangvan.EMwebapp.dto.response.UserResponse;
import com.bangvan.EMwebapp.entity.Employee;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "select E.id, E.first_name,E.last_name,E.job_title,E.email, E.start_date,E.work_style,E.salary from users as U " +
            "join user_company as UC on U.id = UC.user_id " +
            "join companies as C on UC.company_id = C.id " +
            "join company_employee as CE on C.id = CE.company_id " +
            "join employees as E on CE.employee_id = E.id " +
            "where U.username = :username",nativeQuery = true)
    List<Tuple> findAllEmployeesByUsername(@Param("username") String username);

    @Query(value = "select count(*) from users as U " +
            "join user_company as UC on U.id = UC.user_id " +
            "join companies as C on UC.company_id = C.id " +
            "join company_employee as CE on C.id = CE.company_id " +
            "join employees as E on CE.employee_id = E.id " +
            "where U.username = :username",nativeQuery = true)
    int countEmployeesByUsername(@Param("username") String username);
}
