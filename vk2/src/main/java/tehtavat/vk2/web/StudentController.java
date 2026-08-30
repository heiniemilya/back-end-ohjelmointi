package tehtavat.vk2.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import tehtavat.vk2.domain.Student;



@Controller
public class StudentController {

    public static final List<Student> students = new ArrayList<>();

    static {
        students.add(new Student("Kate", "Cole"));
        students.add(new Student("Dan", "Brown"));
        students.add(new Student("Mike", "Mars"));
    }

    @RequestMapping("hellostudents")
      public String listStudents(Model model) {
        model.addAttribute("students", students);
        return "hellostudents";
    }

}
