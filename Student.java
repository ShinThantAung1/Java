/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;

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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String adminNumber;
    private String studentClass;
    private double gpa;
    private List<Module> modules;

    // Constructor
    public Student(String name, String adminNumber, String studentClass, double gpa, List<Module> modules) {
        this.name = name;
        this.adminNumber = adminNumber;
        this.studentClass = studentClass;
        this.modules = new ArrayList<>(modules);
        calculateGpa();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    

    public void calculateGpa() {
        if (modules.isEmpty()) {
            this.gpa = 0.0;
            return;
        }

        double totalGradePoints = 0.0;
        int totalCredits = 0;

        for (Module module : modules) {
            totalGradePoints += getGradePointFromMarks(module.getModuleMarks()) * module.getCreditUnits();
            totalCredits += module.getCreditUnits();
        }

        this.gpa = totalGradePoints / totalCredits;
    }

    private double getGradePointFromMarks(int marks) {
        if (marks >= 80) {
            return 4.0;
        } else if (marks >= 70) {
            return 3.0;
        } else if (marks >= 60) {
            return 2.0;
        } else if (marks >= 50) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    public List<Module> getModules() {
        return new ArrayList<>(modules);
    }

    public void setModules(List<Module> modules) {
        this.modules = new ArrayList<>(modules);
        calculateGpa();
    }

    public void addModule(Module module) {
        this.modules.add(module);
        calculateGpa();
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', adminNumber='" + adminNumber + "', studentClass='" + studentClass + "', gpa=" + gpa + ", modules=" + modules + "}";
    }
}
