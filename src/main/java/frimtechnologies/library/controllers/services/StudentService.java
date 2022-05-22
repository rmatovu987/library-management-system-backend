package frimtechnologies.library.controllers.services;

import frimtechnologies.library.controllers.services.payloads.StudentRequest;
import frimtechnologies.library.domains.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class StudentService {

    public Student create(StudentRequest request) {
        Student student = new Student(
                request.firstName,
                request.lastName,
                request.studentCode,
                request.email,
                request.phoneNumber,
                request.school
        );
        student.persist();

        return student;
    }

    public Student update(Long id, StudentRequest request) {
        Student student = Student.findById(id);
        if (student == null) throw new WebApplicationException("Invalid student selected", 404);

        student.firstName = request.firstName;
        student.lastName = request.lastName;
        student.studentCode = request.studentCode;
        student.email = request.email;
        student.phoneNumber = request.phoneNumber;
        student.school = request.school;

        return student;
    }

    public List<Student> get() {
        return Student.listAll();
    }

    public void delete(Long id){
        Student student = Student.findById(id);
        if (student == null) throw new WebApplicationException("Invalid student selected", 404);

        student.delete();
    }
}
