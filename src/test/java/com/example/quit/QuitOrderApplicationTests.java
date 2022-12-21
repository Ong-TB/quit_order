package com.example.quit;

import com.example.quit.entity.Employee;
import com.example.quit.mapper.EmployMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QuitOrderApplicationTests {

    @Autowired
    private EmployMapper em;
    @Test
    void contextLoads() {
    }

    @Test
    public void testSelectEmp(){
        System.out.println("---testSelectEmp---");
        List<Employee> empList = em.selectList(null);
        System.out.println(empList);
    }

}
