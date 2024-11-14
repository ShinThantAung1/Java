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
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StudentManagement {
    private List<Student> students;
    private List<Student> filteredStudents;
    private StudentDisplayView view;
    private int currentStudentIndex = 0;
    private static final double GPA_TOLERANCE = 0.01;

    public StudentManagement(List<Student> students, StudentDisplayView view) {
        this.students = students;
        this.filteredStudents = new ArrayList<>(students); 
        this.view = view;
        view.setController(this);
        view.setStudentList(filteredStudents);
    }

    public void start() {
        if (!filteredStudents.isEmpty()) {
            displayStudent(0);
        }
    }

    public void displayStudent(int index) {
        if (index >= 0 && index < filteredStudents.size()) {
            currentStudentIndex = index;
            view.displayStudentDetails(filteredStudents.get(index), index, filteredStudents.size());
        }
    }

    public void nextStudent() {
        if (currentStudentIndex < filteredStudents.size() - 1) {
            displayStudent(currentStudentIndex + 1);
        }
    }

    public void prevStudent() {
        if (currentStudentIndex > 0) {
            displayStudent(currentStudentIndex - 1);
        }
    }

    public void nextModule() {
        view.nextModule();
    }

    public void prevModule() {
        view.prevModule();
    }

    public void searchStudentByClass(String classInfo) {
        if (classInfo.trim().isEmpty()) {
            view.displayError("Class information cannot be empty.");
            return;
        }
        filteredStudents.clear();
        double totalGpa = 0.0;

        for (Student student : students) {
            if (student.getStudentClass().equalsIgnoreCase(classInfo)) {
                filteredStudents.add(student);
                totalGpa += student.getGpa();
            }
        }

        if (filteredStudents.isEmpty()) {
            view.displayNoStudentsFoundForClass(classInfo);
        } else {
            int studentCount = filteredStudents.size();
            double averageGpa = totalGpa / studentCount;
            view.displayClassSummary(classInfo, studentCount, averageGpa);
            view.setStudentList(filteredStudents);
            displayStudent(0);
        }
    }

    public void searchStudentByName(String name) {
        if (name.trim().isEmpty()) {
            view.displayError("Name cannot be empty.");
            return;
        }
        filteredStudents.clear();

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                filteredStudents.add(student);
            }
        }

        if (filteredStudents.isEmpty()) {
            view.displayNoStudentsFoundForName(name);
        } else {
            view.setStudentList(filteredStudents);
            displayStudent(0);
        }
    }

     public void searchStudentByGpa(double gpa) {
        if (Double.isNaN(gpa) || gpa < 0.0 || gpa > 4.0) {
            view.displayError("GPA must be between 0.0 and 4.0.");
            return;
        }

        filteredStudents = students.stream()
            .filter(student -> Math.abs(student.getGpa() - gpa) < GPA_TOLERANCE)
            .collect(Collectors.toList());

        if (filteredStudents.isEmpty()) {
            view.displayNoStudentsFoundForGpa(gpa);
        } else {
            view.setStudentList(filteredStudents);
            displayStudent(0);
        }
    }
    
    
    public void showAllStudents() {
        filteredStudents = new ArrayList<>(students);
        view.setStudentList(filteredStudents);
        if (!filteredStudents.isEmpty()) {
            displayStudent(0);
        } else {
            view.clearStudentDetails();
            view.clearModuleDetails();
        }
    }

    public void addStudent(String name, String adminNumber, String studentClass, double gpa, ArrayList<Module> modules) {
        Student student = new Student(name, adminNumber, studentClass, gpa, modules);
        students.add(student);
        saveDataToFile();
    }

    public void deleteStudentByAdmin(String adminNumber) {
        boolean removed = students.removeIf(student -> student.getAdminNumber().equals(adminNumber));
        if (removed) {
            JOptionPane.showMessageDialog(null, "Student removed successfully", "Student Management System", JOptionPane.INFORMATION_MESSAGE);
            saveDataToFile();
        } else {
            JOptionPane.showMessageDialog(null, "Student with admin number " + adminNumber + " not found.", "Student Management System", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteStudentByName(String name) {
        boolean removed = students.removeIf(student -> student.getName().equals(name));
        if (removed) {
            JOptionPane.showMessageDialog(null, "Student removed successfully", "Student Management System", JOptionPane.INFORMATION_MESSAGE);
            saveDataToFile();
        } else {
            JOptionPane.showMessageDialog(null, "Student with name " + name + " not found.", "Student Management System", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Student searchStudent(String searchQuery, boolean searchByAdmin) {
        for (Student student : students) {
            if (searchByAdmin && student.getAdminNumber().equals(searchQuery)) {
                return student;
            } else if (!searchByAdmin && student.getName().equalsIgnoreCase(searchQuery)) {
                return student;
            }
        }
        return null;
    }

    public void addModuleToStudent(String adminNumber, Module newModule) {
        for (Student student : students) {
            if (student.getAdminNumber().equals(adminNumber)) {
                student.addModule(newModule);
                student.calculateGpa();
                view.setStudentList(filteredStudents);
                saveDataToFile();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Student not found!");
    }

    public List<Module> getModules(String adminNumber) {
        Student student = searchStudent(adminNumber, true);
        if (student != null) {
            return student.getModules();
        } else {
            return new ArrayList<>();
        }
    }

    public int getNumberOfModules(String adminNumber) {
        Student student = searchStudent(adminNumber, true);
        if (student != null) {
            return student.getModules().size();
        } else {
            return 0;
        }
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public boolean checkAdmin(String admin) {
        for (Student student : students) {
            if (student.getAdminNumber().equalsIgnoreCase(admin)) {
                return true;
            }
        }
        return false;
    }

    private void updateStudentGpa(Student student) {
        List<Module> modules = student.getModules();
        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Module module : modules) {
            double gradePoint = convertMarksToGradePoint(module.getModuleMarks());
            totalPoints += gradePoint * module.getCreditUnits();
            totalCredits += module.getCreditUnits();
        }

        double gpa = (totalCredits > 0) ? (totalPoints / totalCredits) : 0.0;
        student.setGpa(gpa);
    }

    private double convertMarksToGradePoint(int marks) {
        if (marks >= 85) return 4.0;
        if (marks >= 70) return 3.5;
        if (marks >= 55) return 3.0;
        if (marks >= 40) return 2.0;
        return 0.0;
    }

    private List<Student> readStudentsFromFile() {
        List<Student> students = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("student.txt"))) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().split(": ")[1];
                String admin = scanner.nextLine().split(": ")[1];
                String studentClass = scanner.nextLine().split(": ")[1];
                double gpa = Double.parseDouble(scanner.nextLine().split(": ")[1]);
                scanner.nextLine();
                ArrayList<Module> modules = new ArrayList<>();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.isEmpty()) break;
                    String moduleCode = line.split(": ")[1];
                    String moduleName = scanner.nextLine().split(": ")[1];
                    int credits = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                    int marks = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                    modules.add(new Module(moduleCode, moduleName, marks, credits));
                }
                students.add(new Student(name, admin, studentClass, gpa, modules));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void saveDataToFile() {
        try (FileWriter writer = new FileWriter("student.txt")) {
            writer.write(students.size() + "\n");

            for (Student student : students) {
                writer.write(student.getStudentClass() + ";" + student.getAdminNumber() + ";" + student.getName() + ";" + student.getModules().size());
                for (Module module : student.getModules()) {
                    writer.write(";" + module.getModuleCode() + ";" + module.getModuleName() + ";" + module.getCreditUnits() + ";" + module.getModuleMarks());
                }
                writer.write("\n");
            }

            JOptionPane.showMessageDialog(null, "Data saved to file successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null, "Error writing to file!", "Error", JOptionPane.ERROR_MESSAGE);
            ioException.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        for (Student student : students) {
            st.append("Student Name=").append(student.getName()).append(", Admin Number=").append(student.getAdminNumber()).append(", Modules=");
            for (Module module : student.getModules()) {
                st.append("[Module Code=").append(module.getModuleCode())
                  .append(", Name=").append(module.getModuleName())
                  .append(", Marks=").append(module.getModuleMarks())
                  .append(", Credit=").append(module.getCreditUnits()).append("]");
            }
            st.append("\n");
        }
        return st.toString();
    }
}