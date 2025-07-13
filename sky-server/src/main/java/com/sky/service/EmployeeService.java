package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * New Employee
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * Pagination Query
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * Enable or disable employee accounts
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * Query employee information by id
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * Edit employee information
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}
