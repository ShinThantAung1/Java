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
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentData {

    public static List<Student> readStudentsFromFile(String filename) {
        List<Student> students = new ArrayList<>();

        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("The file " + filename + " does not exist.");
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            int numStudents = Integer.parseInt(line.trim());

            for (int i = 0; i < numStudents; i++) {
                line = reader.readLine();
                if (line != null) {
                    String[] parts = line.split(";");
                    String stclass = parts[0];
                    String admin = parts[1];
                    String name = parts[2];
                    int numModules = Integer.parseInt(parts[3]);

                    ArrayList<Module> modules = new ArrayList<>();
                    int index = 4;
                    for (int j = 0; j < numModules; j++) {
                        String moduleCode = parts[index++];
                        String moduleName = parts[index++];
                        int creditUnits = Integer.parseInt(parts[index++]);
                        double marks = Double.parseDouble(parts[index++]);
                        modules.add(new Module(moduleCode, moduleName, (int) marks, creditUnits));
                    }

                    students.add(new Student(name, admin, stclass, 0.0, modules));
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        return students;
    }

 
    public static void writeStudentsToFile(List<Student> students) {
        try (FileWriter writer = new FileWriter("student.txt")) {
            for (Student student : students) {
                writer.write("Name: " + student.getName() + "\n");
                writer.write("Admin: " + student.getAdminNumber() + "\n");
                writer.write("Class: " + student.getStudentClass() + "\n");
                writer.write("GPA: " + student.getGpa() + "\n");
                writer.write("Modules:\n");
                for (Module module : student.getModules()) {
                    writer.write("  Module Code: " + module.getModuleCode() + "\n");
                    writer.write("  Module Name: " + module.getModuleName() + "\n");
                    writer.write("  Credits: " + module.getCreditUnits() + "\n");
                    writer.write("  Marks: " + module.getModuleMarks() + "\n");
                }
                writer.write("\n");
            }
            System.out.println("Data written to students.txt successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing to file!");
        }
    }

    public static void main(String[] args) {
        List<Student> students = readStudentsFromFile("student.txt");
        writeStudentsToFile(students);
    }
}


