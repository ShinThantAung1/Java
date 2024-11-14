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

public class Module implements Serializable {
    private static final long serialVersionUID = 1L;

    private String moduleCode;
    private String moduleName;
    private int moduleMarks;
    private int creditUnits;

    public Module(String moduleCode, String moduleName, int moduleMarks, int creditUnits) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.moduleMarks = moduleMarks;
        this.creditUnits = creditUnits;
    }

    // Getters and Setters
    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getModuleMarks() {
        return moduleMarks;
    }

    public void setModuleMarks(int moduleMarks) {
        this.moduleMarks = moduleMarks;
    }

    public int getCreditUnits() {
        return creditUnits;
    }

    public void setCreditUnits(int creditUnits) {
        this.creditUnits = creditUnits;
    }

    public String getModuleGrade() {
        if (moduleMarks >= 80) {
            return "A";
        } else if (moduleMarks >= 70) {
            return "B";
        } else if (moduleMarks >= 60) {
            return "C";
        } else if (moduleMarks >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    @Override
    public String toString() {
        return "Module{moduleCode='" + moduleCode + "', moduleName='" + moduleName + "', moduleMarks=" + moduleMarks + ", creditUnits=" + creditUnits + "}";
    }
}
