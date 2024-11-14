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
public class StudentDisplayModel {
    private Student student;
    private int currentModuleIndex;

    public void setStudent(Student student) {
        this.student = student;
        this.currentModuleIndex = 0;
    }

    public Module getCurrentModule() {
        if (student != null && currentModuleIndex >= 0 && currentModuleIndex < student.getModules().size()) {
            return student.getModules().get(currentModuleIndex);
        }
        return null;
    }

    public void nextModule() {
        if (student != null && currentModuleIndex < student.getModules().size() - 1) {
            currentModuleIndex++;
        }
    }

    public void prevModule() {
        if (student != null && currentModuleIndex > 0) {
            currentModuleIndex--;
        }
    }

    public int getCurrentModuleIndex() {
        return currentModuleIndex;
    }

    public int getTotalModules() {
        return student != null ? student.getModules().size() : 0;
    }

    public void setCurrentModuleIndex(int index) {
        if (student != null && index >= 0 && index < student.getModules().size()) {
            currentModuleIndex = index;
        }
    }
}
