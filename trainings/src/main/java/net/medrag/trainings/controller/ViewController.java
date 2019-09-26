package net.medrag.trainings.controller;

import net.medrag.trainings.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 30.10.2018
 */
@Controller
public class ViewController {

    @GetMapping("/")
    public String returnView(Model model) {
        prepareModel(model);
        return "thymeleaf";
    }

    @GetMapping("/playSR")
    public String playSR(Model model) {
        return "sr-table";
    }

    @PostMapping("/newStudent")
    public String newStudent(@ModelAttribute Student newStudent, BindingResult errors, RedirectAttributes re) {
        System.out.println(newStudent);
        validateStudent(newStudent, errors);
        if (errors.hasErrors()) {
            System.out.println("ERRORS!");
            re.addFlashAttribute("errors", errors);
        }
//        re.addAttribute("newcomer", newStudent);
        return "redirect:/";
    }

    private List<Student> getStudentList() {
        return Arrays.asList(new Student(15, "Vasyan", true), new Student(16, "Olga", false), new Student(17, "Petr", true));
    }

    private void validateStudent(Student student, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "messages.invalid.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "messages.invalid.age");

        if (student.getName().trim().length() > 16) {
            errors.rejectValue("name", "messages.invalid.name");
        }

        if (student.getAge() < 12) {
            errors.rejectValue("age", "messages.invalid.age");
        }
    }

    private void prepareModel(Model model) {
        String s = "this is text example from model";
        model.addAttribute("textExample", s);
        model.addAttribute("students", getStudentList());
        model.addAttribute("newStudent", new Student());
    }

}
