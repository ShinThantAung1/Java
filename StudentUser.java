/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;

import java.util.List;

/**
 *
 * @author Alexa
 */
//Class: DIT/FT/2B/23
//Admission Number: p2329776
//Name: Shin Thant Aung
//Class: DIT/FT/2B/23
//Admission Number: p2340643
//Name: Han Thu Lin
public class StudentUser {
    public static void main(String[] args) {
        List<Student> students = StudentData.readStudentsFromFile("student.txt");

      
        StudentDisplayView view = new StudentDisplayView();
        StudentManagement controller = new StudentManagement(students, view);

        view.setController(controller);
        controller.start();

    
        view.setVisible(true);
    }
}