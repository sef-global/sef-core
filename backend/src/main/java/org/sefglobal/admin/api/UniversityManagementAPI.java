package org.sefglobal.admin.api;

import org.sefglobal.admin.beans.University;
import org.sefglobal.admin.dao.UniversityDAO;
import org.sefglobal.admin.exception.AdminAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UniversityManagementAPI {
    
    @Autowired
    private UniversityDAO universityDAO;

    @GetMapping("/universities")
    public List<University> getAllUniversities(HttpServletRequest request){
        System.out.println(request.getRemoteAddr());
        return universityDAO.getAllUniversities();
    }

    @GetMapping("/universities/{id}")
    public University getUniversity(@PathVariable int id) throws AdminAPIException {
        return universityDAO.getUniversity(id);
    }
}
