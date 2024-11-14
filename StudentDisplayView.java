/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package CA2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;

import javax.swing.DefaultComboBoxModel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


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
public class StudentDisplayView extends JFrame {
    private StudentManagement controller;
    private StudentDisplayModel model;

    public void setController(StudentManagement controller) {
        this.controller = controller;
    }

    private void updateStudentPanelTitle(int currentIndex, int totalStudents) {
        String title = "Student " + (currentIndex + 1) + " of " + totalStudents;
        StudentDetailPanel.setBorder(BorderFactory.createTitledBorder(title));
    }

    private void updateModulePanelTitle(int currentIndex, int totalModules) {
        String title = "Module " + (currentIndex + 1) + " of " + totalModules;
        ModuleDetailPanel.setBorder(BorderFactory.createTitledBorder(title));
    }

    public StudentDisplayView() {
        initComponents();
        model = new StudentDisplayModel();
        setupSearchFunctionality();
    }

    private void setupSearchFunctionality() {
        jRadioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jRadioButton1.isSelected()) {
                    jRadioButton2.setSelected(false);
                    jRadioButton3.setSelected(false);
                }
                handleRadioButtonSelection();
            }
        });

        jRadioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jRadioButton2.isSelected()) {
                    jRadioButton1.setSelected(false);
                    jRadioButton3.setSelected(false);
                }
                handleRadioButtonSelection();
            }
        });

        jRadioButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jRadioButton3.isSelected()) {
                    jRadioButton1.setSelected(false);
                    jRadioButton2.setSelected(false);
                }
                handleRadioButtonSelection();
            }
        });

        jButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = jTextField10.getText().trim();
                if (jRadioButton1.isSelected()) {
                    controller.searchStudentByClass(searchText);
                } else if (jRadioButton2.isSelected()) {
                    controller.searchStudentByName(searchText);
                } else if (jRadioButton3.isSelected()) {
                    try {
                        double gpa = Double.parseDouble(searchText);
                        controller.searchStudentByGpa(gpa);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid GPA!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    controller.showAllStudents();
                }
            }
        });

        jTextField10.setEnabled(false);
    }

    private void handleRadioButtonSelection() {
        if (jRadioButton1.isSelected() || jRadioButton2.isSelected() || jRadioButton3.isSelected()) {
            jTextField10.setEnabled(true);
        } else {
            jTextField10.setEnabled(false);
            jTextField10.setText("");
        }
    }

    public void setStudentList(List<Student> students) {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        for (int i = 0; i < students.size(); i++) {
            comboBoxModel.addElement("Student " + (i + 1));
        }
        jComboBox1.setModel(comboBoxModel);
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jComboBox1.getSelectedIndex();
                if (selectedIndex >= 0) {
                    controller.displayStudent(selectedIndex);
                }
            }
        });
    }

    public void displayStudentDetails(Student student, int studentIndex, int totalStudents) {
        model.setStudent(student);
        jTextField1.setText(student.getName());
        jTextField2.setText(student.getAdminNumber());
        jTextField3.setText(student.getStudentClass());
        jTextField4.setText(String.format("%.2f", student.getGpa()));
        updateStudentPanelTitle(studentIndex, totalStudents);
        setModuleList(student.getModules());
        displayModuleDetails();
    }

    private void setModuleList(List<Module> modules) {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        for (int i = 0; i < modules.size(); i++) {
            comboBoxModel.addElement("Module " + (i + 1));
        }
        jComboBox2.setModel(comboBoxModel);
        jComboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jComboBox2.getSelectedIndex();
                if (selectedIndex >= 0) {
                    model.setCurrentModuleIndex(selectedIndex);
                    displayModuleDetails();
                }
            }
        });
    }

    private void displayModuleDetails() {
        Module module = model.getCurrentModule();
        int currentIndex = model.getCurrentModuleIndex();
        int totalModules = model.getTotalModules();
        if (module != null) {
            jTextField5.setText(module.getModuleCode());
            jTextField6.setText(module.getModuleName());
            jTextField7.setText(String.valueOf(module.getModuleMarks()));
            jTextField8.setText(String.valueOf(module.getCreditUnits()));
            jTextField9.setText(module.getModuleGrade());
            updateModulePanelTitle(currentIndex, totalModules);
        } else {
            clearModuleDetails();
        }
    }

    public void nextModule() {
        model.nextModule();
        displayModuleDetails();
    }

    public void prevModule() {
        model.prevModule();
        displayModuleDetails();
    }

    public void displayNoStudentsFoundForClass(String searchTerm) {
        jTextArea1.setText("No students found for class: " + searchTerm);
        clearStudentDetails();
        clearModuleDetails();
    }

    public void displayNoStudentsFoundForName(String searchTerm) {
        jTextArea1.setText("No such student found");
        clearStudentDetails();
        clearModuleDetails();
    }
    
    public void displayNoStudentsFoundForGpa(double gpa) {
        jTextArea1.setText("No students found with GPA: " + gpa);
        clearStudentDetails();
        clearModuleDetails();
    }

    public void displayClassSummary(String classInfo, int studentCount, double averageGpa) {
        jTextArea1.setText("Class: " + classInfo + "\nNumber of Students: " + studentCount + "\nAverage GPA: " + String.format("%.2f", averageGpa));
    }

    public void displayError(String errorMessage) {
        jTextArea1.setText("Error: " + errorMessage);
    }

    public void clearStudentDetails() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jComboBox1.setModel(new DefaultComboBoxModel<>());
        StudentDetailPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));
    }

    public void clearModuleDetails() {
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jComboBox2.setModel(new DefaultComboBoxModel<>());
        ModuleDetailPanel.setBorder(BorderFactory.createTitledBorder("Module Details"));
    }
    

    public void clearResults() {
        jTextArea1.setText("");
    }
    
    private void refreshView() {
        controller.showAllStudents();
        clearResults();
    }

    private void clearCreateStudent() {
        textFieldForName.setText("");
        textFieldForAdmin.setText("");
        textFieldForClass.setText("");
        textFieldForGPA.setText("");
    }

    private void clearAddModule() {
        textFieldForStudentAdmin.setText("");
        textFieldForModuleCode.setText("");
        textFieldForModuleName.setText("");
        textFieldForMarks.setText("");
        textFieldForCredit.setText("");
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        EnquirySystem = new javax.swing.JPanel();
        StudentDetailPanel = new javax.swing.JPanel();
        NameLabel = new javax.swing.JLabel();
        AdminLabel = new javax.swing.JLabel();
        ClassLabel = new javax.swing.JLabel();
        GPALabel = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        ModuleDetailPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        SearchPanel = new javax.swing.JPanel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTextField10 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        ResultPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        exitbutton = new javax.swing.JButton();
        mainTabbedPane = new javax.swing.JTabbedPane();
        panelForCreateStudent = new javax.swing.JPanel();
        Student = new javax.swing.JLabel();
        labelForName = new javax.swing.JLabel();
        labelForAdmin = new javax.swing.JLabel();
        labelForClass = new javax.swing.JLabel();
        labelForGPA = new javax.swing.JLabel();
        textFieldForAdmin = new javax.swing.JTextField();
        textFieldForName = new javax.swing.JTextField();
        textFieldForGPA = new javax.swing.JTextField();
        textFieldForClass = new javax.swing.JTextField();
        buttonForCreate = new javax.swing.JButton();
        buttonForExit1 = new javax.swing.JButton();
        panelForDeleteStudent = new javax.swing.JPanel();
        radioButtonForByAdmin = new javax.swing.JRadioButton();
        radioButtonForByName = new javax.swing.JRadioButton();
        Search = new javax.swing.JLabel();
        textFieldForSearch = new javax.swing.JTextField();
        buttonForSearch = new javax.swing.JButton();
        Results = new javax.swing.JLabel();
        buttonForExit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaForResults = new javax.swing.JTextArea();
        buttonForDelete = new javax.swing.JButton();
        panelForAddModule = new javax.swing.JPanel();
        Module = new javax.swing.JLabel();
        labelForModuleCode = new javax.swing.JLabel();
        labelForModuleName = new javax.swing.JLabel();
        labelForMarks = new javax.swing.JLabel();
        textFieldForModuleCode = new javax.swing.JTextField();
        textFieldForModuleName = new javax.swing.JTextField();
        textFieldForCredit = new javax.swing.JTextField();
        textFieldForMarks = new javax.swing.JTextField();
        buttonForAdd = new javax.swing.JButton();
        buttonForExit2 = new javax.swing.JButton();
        labelForStudentAdmin = new javax.swing.JLabel();
        textFieldForStudentAdmin = new javax.swing.JTextField();
        labelForCredit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        EnquirySystem.setVerifyInputWhenFocusTarget(false);
        EnquirySystem.setLayout(new java.awt.GridBagLayout());

        StudentDetailPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        StudentDetailPanel.setLayout(new java.awt.GridBagLayout());

        NameLabel.setText("Name :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        StudentDetailPanel.add(NameLabel, gridBagConstraints);
        NameLabel.getAccessibleContext().setAccessibleDescription("");

        AdminLabel.setText("Admin :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        StudentDetailPanel.add(AdminLabel, gridBagConstraints);
        AdminLabel.getAccessibleContext().setAccessibleDescription("");

        ClassLabel.setText("Class :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        StudentDetailPanel.add(ClassLabel, gridBagConstraints);

        GPALabel.setText("GPA :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        StudentDetailPanel.add(GPALabel, gridBagConstraints);

        jTextField1.setEditable(false);
        jTextField1.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        StudentDetailPanel.add(jTextField1, gridBagConstraints);

        jTextField2.setEditable(false);
        jTextField2.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        StudentDetailPanel.add(jTextField2, gridBagConstraints);

        jTextField3.setEditable(false);
        jTextField3.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        StudentDetailPanel.add(jTextField3, gridBagConstraints);

        jTextField4.setEditable(false);
        jTextField4.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        StudentDetailPanel.add(jTextField4, gridBagConstraints);

        jButton1.setText("Prev");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 75, 0, 0);
        StudentDetailPanel.add(jButton1, gridBagConstraints);

        jButton2.setText("Next");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 75, 0, 0);
        StudentDetailPanel.add(jButton2, gridBagConstraints);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        StudentDetailPanel.add(jComboBox1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.ipady = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 30, 30);
        EnquirySystem.add(StudentDetailPanel, gridBagConstraints);

        ModuleDetailPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ModuleDetailPanel.setMinimumSize(new java.awt.Dimension(236, 122));
        ModuleDetailPanel.setName(""); // NOI18N
        ModuleDetailPanel.setPreferredSize(new java.awt.Dimension(295, 122));
        ModuleDetailPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Mod Code :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 14;
        ModuleDetailPanel.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Mod Name :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 14;
        ModuleDetailPanel.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Marks :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 14;
        ModuleDetailPanel.add(jLabel3, gridBagConstraints);

        jLabel4.setText("Grade :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 14;
        ModuleDetailPanel.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Credits :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 14;
        ModuleDetailPanel.add(jLabel5, gridBagConstraints);

        jTextField5.setEditable(false);
        jTextField5.setText("jTextField5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        ModuleDetailPanel.add(jTextField5, gridBagConstraints);

        jTextField6.setEditable(false);
        jTextField6.setText("jTextField5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        ModuleDetailPanel.add(jTextField6, gridBagConstraints);

        jTextField7.setEditable(false);
        jTextField7.setText("jTextField5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        ModuleDetailPanel.add(jTextField7, gridBagConstraints);

        jTextField8.setEditable(false);
        jTextField8.setText("jTextField5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        ModuleDetailPanel.add(jTextField8, gridBagConstraints);

        jTextField9.setEditable(false);
        jTextField9.setText("jTextField5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        ModuleDetailPanel.add(jTextField9, gridBagConstraints);

        jButton3.setText("Next");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 75, 0, 0);
        ModuleDetailPanel.add(jButton3, gridBagConstraints);

        jButton4.setText("Prev");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 75, 0, 0);
        ModuleDetailPanel.add(jButton4, gridBagConstraints);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        ModuleDetailPanel.add(jComboBox2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.ipady = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(13, 0, 30, 30);
        EnquirySystem.add(ModuleDetailPanel, gridBagConstraints);

        SearchPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SearchPanel.setMinimumSize(new java.awt.Dimension(100, 90));
        SearchPanel.setPreferredSize(new java.awt.Dimension(200, 90));

        jRadioButton2.setText("By Name");
        jRadioButton2.setMaximumSize(new java.awt.Dimension(69, 18));
        jRadioButton2.setMinimumSize(new java.awt.Dimension(69, 18));
        jRadioButton2.setPreferredSize(new java.awt.Dimension(69, 18));
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jTextField10.setPreferredSize(new java.awt.Dimension(78, 18));

        jRadioButton1.setText("By Class");
        jRadioButton1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 204, 51));
        jButton5.setForeground(new java.awt.Color(0, 0, 0));
        jButton5.setText("Search");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel7.setText("Search");

        jButton6.setBackground(new java.awt.Color(255, 204, 51));
        jButton6.setForeground(new java.awt.Color(0, 0, 0));
        jButton6.setText("Refresh");
        jButton6.setMaximumSize(new java.awt.Dimension(66, 28));
        jButton6.setMinimumSize(new java.awt.Dimension(66, 28));
        jButton6.setPreferredSize(new java.awt.Dimension(66, 28));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jRadioButton3.setText("By GPA");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SearchPanelLayout = new javax.swing.GroupLayout(SearchPanel);
        SearchPanel.setLayout(SearchPanelLayout);
        SearchPanelLayout.setHorizontalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SearchPanelLayout.createSequentialGroup()
                        .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 1105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SearchPanelLayout.setVerticalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SearchPanelLayout.createSequentialGroup()
                        .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SearchPanelLayout.createSequentialGroup()
                                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jRadioButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(54, Short.MAX_VALUE))
                    .addGroup(SearchPanelLayout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 54, Short.MAX_VALUE))))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 30, 0);
        EnquirySystem.add(SearchPanel, gridBagConstraints);

        ResultPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ResultPanel.setMinimumSize(new java.awt.Dimension(100, 150));
        ResultPanel.setPreferredSize(new java.awt.Dimension(200, 40));
        ResultPanel.setLayout(new java.awt.GridBagLayout());

        jLabel6.setText("Results");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ResultPanel.add(jLabel6, gridBagConstraints);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 2000;
        gridBagConstraints.ipady = 500;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        ResultPanel.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        EnquirySystem.add(ResultPanel, gridBagConstraints);

        exitbutton.setBackground(new java.awt.Color(255, 204, 51));
        exitbutton.setForeground(new java.awt.Color(0, 0, 0));
        exitbutton.setText("Exit");
        exitbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbuttonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        EnquirySystem.add(exitbutton, gridBagConstraints);

        jTabbedPane1.addTab("Enquiry System", EnquirySystem);

        mainTabbedPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Student.setText("Student");

        labelForName.setText("Name:");

        labelForAdmin.setText("Admin:");

        labelForClass.setText("Class:");

        labelForGPA.setText("GPA:");

        textFieldForAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldForAdminActionPerformed(evt);
            }
        });

        textFieldForName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldForNameActionPerformed(evt);
            }
        });

        textFieldForGPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldForGPAActionPerformed(evt);
            }
        });

        buttonForCreate.setBackground(new java.awt.Color(255, 215, 0));
        buttonForCreate.setText("Create");
        buttonForCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonForCreateActionPerformed(evt);
            }
        });

        buttonForExit1.setBackground(new java.awt.Color(255, 215, 0));
        buttonForExit1.setText("Save and Exit");
        buttonForExit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonForExit1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelForCreateStudentLayout = new javax.swing.GroupLayout(panelForCreateStudent);
        panelForCreateStudent.setLayout(panelForCreateStudentLayout);
        panelForCreateStudentLayout.setHorizontalGroup(
            panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelForCreateStudentLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonForExit1)
                .addGap(21, 21, 21))
            .addGroup(panelForCreateStudentLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonForCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelForCreateStudentLayout.createSequentialGroup()
                            .addGroup(panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(labelForName)
                                .addComponent(Student))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textFieldForName, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelForCreateStudentLayout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addGroup(panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelForCreateStudentLayout.createSequentialGroup()
                                    .addGroup(panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(labelForAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelForClass))
                                    .addGap(65, 65, 65))
                                .addGroup(panelForCreateStudentLayout.createSequentialGroup()
                                    .addComponent(labelForGPA)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(textFieldForGPA, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textFieldForClass, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textFieldForAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(1079, Short.MAX_VALUE))
        );
        panelForCreateStudentLayout.setVerticalGroup(
            panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelForCreateStudentLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(Student)
                .addGap(45, 45, 45)
                .addGroup(panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelForName)
                    .addComponent(textFieldForName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelForAdmin)
                    .addComponent(textFieldForAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelForClass)
                    .addComponent(textFieldForClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelForCreateStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelForGPA)
                    .addComponent(textFieldForGPA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(buttonForCreate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 411, Short.MAX_VALUE)
                .addComponent(buttonForExit1)
                .addGap(16, 16, 16))
        );

        mainTabbedPane.addTab("Create Student", panelForCreateStudent);

        radioButtonForByAdmin.setText("By Admin");

        radioButtonForByName.setText("By Name");

        Search.setText("Search");

        buttonForSearch.setBackground(new java.awt.Color(255, 215, 0));
        buttonForSearch.setText("Search");
        buttonForSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonForSearchActionPerformed(evt);
            }
        });

        Results.setText("Results");

        buttonForExit.setBackground(new java.awt.Color(255, 215, 0));
        buttonForExit.setText("Save and Exit");
        buttonForExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonForExitActionPerformed(evt);
            }
        });

        textAreaForResults.setColumns(20);
        textAreaForResults.setRows(5);
        jScrollPane2.setViewportView(textAreaForResults);

        buttonForDelete.setBackground(new java.awt.Color(255, 215, 0));
        buttonForDelete.setText("Delete");
        buttonForDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonForDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelForDeleteStudentLayout = new javax.swing.GroupLayout(panelForDeleteStudent);
        panelForDeleteStudent.setLayout(panelForDeleteStudentLayout);
        panelForDeleteStudentLayout.setHorizontalGroup(
            panelForDeleteStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelForDeleteStudentLayout.createSequentialGroup()
                .addGroup(panelForDeleteStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelForDeleteStudentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Search))
                    .addGroup(panelForDeleteStudentLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panelForDeleteStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelForDeleteStudentLayout.createSequentialGroup()
                                .addGroup(panelForDeleteStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelForDeleteStudentLayout.createSequentialGroup()
                                        .addComponent(radioButtonForByAdmin)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioButtonForByName))
                                    .addComponent(Results))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(textFieldForSearch))))
                .addGap(223, 223, 223))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelForDeleteStudentLayout.createSequentialGroup()
                .addGroup(panelForDeleteStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelForDeleteStudentLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelForDeleteStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(buttonForExit, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonForSearch)))
                    .addGroup(panelForDeleteStudentLayout.createSequentialGroup()
                        .addGap(334, 334, 334)
                        .addComponent(buttonForDelete)))
                .addGap(19, 19, 19))
            .addGroup(panelForDeleteStudentLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelForDeleteStudentLayout.setVerticalGroup(
            panelForDeleteStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelForDeleteStudentLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(Search)
                .addGap(18, 18, 18)
                .addGroup(panelForDeleteStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioButtonForByAdmin)
                    .addComponent(radioButtonForByName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textFieldForSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonForSearch)
                .addGap(11, 11, 11)
                .addComponent(Results)
                .addGap(3, 3, 3)
                .addComponent(buttonForDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 383, Short.MAX_VALUE)
                .addComponent(buttonForExit)
                .addContainerGap())
        );

        mainTabbedPane.addTab("Delete Student", panelForDeleteStudent);

        Module.setText("Module");

        labelForModuleCode.setText("Module Code:");

        labelForModuleName.setText("Module Name:");

        labelForMarks.setText("Marks:");

        textFieldForModuleCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldForModuleCodeActionPerformed(evt);
            }
        });

        textFieldForCredit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldForCreditActionPerformed(evt);
            }
        });

        textFieldForMarks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldForMarksActionPerformed(evt);
            }
        });

        buttonForAdd.setBackground(new java.awt.Color(192, 192, 192));
        buttonForAdd.setText("Add");
        buttonForAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonForAddActionPerformed(evt);
            }
        });

        buttonForExit2.setBackground(new java.awt.Color(255, 215, 0));
        buttonForExit2.setText("Save and Exit");
        buttonForExit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonForExit2ActionPerformed(evt);
            }
        });

        labelForStudentAdmin.setText("Student Admin:");

        textFieldForStudentAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldForStudentAdminActionPerformed(evt);
            }
        });

        labelForCredit.setText("Credit:");

        javax.swing.GroupLayout panelForAddModuleLayout = new javax.swing.GroupLayout(panelForAddModule);
        panelForAddModule.setLayout(panelForAddModuleLayout);
        panelForAddModuleLayout.setHorizontalGroup(
            panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelForAddModuleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonForExit2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(panelForAddModuleLayout.createSequentialGroup()
                .addGroup(panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelForAddModuleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelForStudentAdmin)
                            .addComponent(labelForModuleCode)
                            .addComponent(Module)
                            .addGroup(panelForAddModuleLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelForModuleName)
                                    .addGroup(panelForAddModuleLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelForCredit)
                                            .addComponent(labelForMarks))))))
                        .addGap(28, 28, 28)
                        .addGroup(panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textFieldForCredit, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldForModuleCode, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(textFieldForModuleName, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldForMarks, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldForStudentAdmin)))
                    .addGroup(panelForAddModuleLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(buttonForAdd)))
                .addContainerGap(1095, Short.MAX_VALUE))
        );
        panelForAddModuleLayout.setVerticalGroup(
            panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelForAddModuleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Module)
                .addGap(47, 47, 47)
                .addGroup(panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelForAddModuleLayout.createSequentialGroup()
                        .addGroup(panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelForStudentAdmin)
                            .addComponent(textFieldForStudentAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelForModuleCode)
                            .addComponent(textFieldForModuleCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelForAddModuleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelForModuleName)
                            .addComponent(textFieldForModuleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelForMarks)
                        .addGap(18, 18, 18)
                        .addComponent(labelForCredit))
                    .addGroup(panelForAddModuleLayout.createSequentialGroup()
                        .addComponent(textFieldForMarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldForCredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 45, 45)
                .addComponent(buttonForAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 411, Short.MAX_VALUE)
                .addComponent(buttonForExit2)
                .addGap(16, 16, 16))
        );

        mainTabbedPane.addTab("Add Module", panelForAddModule);

        jTabbedPane1.addTab("Admin System", mainTabbedPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 38, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1311, 904));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        controller.nextModule();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        controller.prevModule();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void exitbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbuttonActionPerformed
      
        JOptionPane.showMessageDialog(this, "Thank you!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
       
        System.exit(0);
    }//GEN-LAST:event_exitbuttonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        controller.nextStudent();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        controller.prevStudent();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void textFieldForAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldForAdminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldForAdminActionPerformed

    private void textFieldForNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldForNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldForNameActionPerformed

    private void textFieldForGPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldForGPAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldForGPAActionPerformed

    private void buttonForCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonForCreateActionPerformed
        // Retrieve values from the input fields
        String name = textFieldForName.getText().trim();
        String admin = textFieldForAdmin.getText().trim();
        String studentClass = textFieldForClass.getText().trim();
        double gpa = Double.parseDouble(textFieldForGPA.getText().trim()); // This will be 0.0 initially
        ArrayList<Module> modules = new ArrayList<>();

        // Validate inputs
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty!", "Student Management System", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (admin.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Admin number cannot be empty!", "Student Management System", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if admin number already exists
        if (controller.checkAdmin(admin)) {
            JOptionPane.showMessageDialog(null, "Student with this admin number already exists!", "Student Management System", JOptionPane.ERROR_MESSAGE);
            textFieldForAdmin.setText("");
            return; // Exit method to prevent adding duplicate student
        }

        if (studentClass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student Class cannot be empty!", "Student Management System", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add new student
        controller.addStudent(name, admin, studentClass, gpa, modules);
        JOptionPane.showMessageDialog(null, "Student Added Successfully!", "Student Management System", JOptionPane.INFORMATION_MESSAGE);

        // Save data to file
        controller.saveDataToFile();

        // Clear input fields
        clearCreateStudent();

    }//GEN-LAST:event_buttonForCreateActionPerformed

    private void buttonForExit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonForExit1ActionPerformed
        // Save data to file
        controller.saveDataToFile();

        // Exit the application
        System.exit(0);
    }//GEN-LAST:event_buttonForExit1ActionPerformed

    private void buttonForSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonForSearchActionPerformed
        boolean searchByAdmin = true; // Use a meaningful variable name
        String input = textFieldForSearch.getText().trim(); // Retrieve and trim user input

        if (radioButtonForByAdmin.isSelected()) {
            searchByAdmin = true;
        } else if (radioButtonForByName.isSelected()) {
            searchByAdmin = false;
        }

        // Search for the student
        Student student = controller.searchStudent(input, searchByAdmin);

        if (student != null) {
            // Generate the output string
            String st = "Name: " + student.getName() + "\n" +
            "Admin: " + student.getAdminNumber() + "\n" +
            "Class: " + student.getStudentClass() + "\n" +
            "GPA: " + student.getGpa() + "\n" +
            "Module(s) Taken: \n" + student.getModules().toString();

            // Display the results
            textAreaForResults.setText(st);
        } else {
            // Handle case where student is not found
            JOptionPane.showMessageDialog(null, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            textAreaForResults.setText(""); // Clear text area if no student is found
        }
    }//GEN-LAST:event_buttonForSearchActionPerformed

    private void buttonForExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonForExitActionPerformed
        // Save data to file
        controller.saveDataToFile();

        // Exit the application
        System.exit(0);
    }//GEN-LAST:event_buttonForExitActionPerformed

    private void buttonForDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonForDeleteActionPerformed
        buttonForDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textFieldForSearch.getText().trim();

                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (radioButtonForByAdmin.isSelected()) {
                    controller.deleteStudentByAdmin(input);
                } else if (radioButtonForByName.isSelected()) {
                    controller.deleteStudentByName(input);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a search criterion (Admin or Name).", "Student Management System", JOptionPane.ERROR_MESSAGE);
                }

                textAreaForResults.setText("");
            }
        });
    }//GEN-LAST:event_buttonForDeleteActionPerformed

    private void textFieldForModuleCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldForModuleCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldForModuleCodeActionPerformed

    private void textFieldForCreditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldForCreditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldForCreditActionPerformed

    private void textFieldForMarksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldForMarksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldForMarksActionPerformed

    private void buttonForAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonForAddActionPerformed
        // Retrieve values from module-related fields
        String admin = textFieldForStudentAdmin.getText().trim();

        if (admin.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Admin number cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = controller.searchStudent(admin, true);

        if (student != null) {
            String moduleName = textFieldForModuleName.getText().trim();
            String moduleCode = textFieldForModuleCode.getText().trim();
            String markstr = textFieldForMarks.getText().trim();
            String creditstr = textFieldForCredit.getText().trim();

            boolean moduleExists = false;

            if (moduleCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Module Code cannot be empty!", "Student Management System", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                for (Module module : student.getModules()) {
                    if (module.getModuleCode().equalsIgnoreCase(moduleCode)) {
                        JOptionPane.showMessageDialog(null, "Module Code already assigned to this student! \n Please try another.", "Error", JOptionPane.ERROR_MESSAGE);
                        textFieldForModuleCode.setText("");
                        moduleExists = true;
                        break;
                    }
                }
            }

            if (moduleName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Module Name cannot be empty!", "Student Management System", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                for (Module module2 : student.getModules()) {
                    if (module2.getModuleName().equalsIgnoreCase(moduleName)) {
                        JOptionPane.showMessageDialog(null, "Module Name already assigned to this student! \n Please try another.", "Error", JOptionPane.ERROR_MESSAGE);
                        textFieldForModuleName.setText("");
                        moduleExists = true;
                        break;
                    }
                }
            }

            if (markstr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Marks cannot be empty!", "Student Management System", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (creditstr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Credit units cannot be empty!", "Student Management System", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int marks = 0;
            int credit = 0;

            try {
                marks = Integer.parseInt(markstr);
                credit = Integer.parseInt(creditstr);

                if (marks < 0 || marks > 100) {
                    JOptionPane.showMessageDialog(null, "Marks must be between 0 and 100!", "Student Management System", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Marks and credit must be valid integers", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!moduleExists) {
                Module newModule = new Module(moduleCode, moduleName, marks, credit);
                controller.addModuleToStudent(admin, newModule);
                JOptionPane.showMessageDialog(null, "Module Added Successfully!", "Student Management System", JOptionPane.INFORMATION_MESSAGE);

                clearAddModule();
                controller.saveDataToFile();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonForAddActionPerformed

    private void buttonForExit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonForExit2ActionPerformed
        // Save data to file
        controller.saveDataToFile();

        // Exit the application
        System.exit(0);
    }//GEN-LAST:event_buttonForExit2ActionPerformed

    private void textFieldForStudentAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldForStudentAdminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldForStudentAdminActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        refreshView();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentDisplayView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentDisplayView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentDisplayView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentDisplayView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentDisplayView().setVisible(true);
            }
        });
        
   
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdminLabel;
    private javax.swing.JLabel ClassLabel;
    private javax.swing.JPanel EnquirySystem;
    private javax.swing.JLabel GPALabel;
    private javax.swing.JLabel Module;
    private javax.swing.JPanel ModuleDetailPanel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JPanel ResultPanel;
    private javax.swing.JLabel Results;
    private javax.swing.JLabel Search;
    private javax.swing.JPanel SearchPanel;
    private javax.swing.JLabel Student;
    private javax.swing.JPanel StudentDetailPanel;
    private javax.swing.JButton buttonForAdd;
    private javax.swing.JButton buttonForCreate;
    private javax.swing.JButton buttonForDelete;
    private javax.swing.JButton buttonForExit;
    private javax.swing.JButton buttonForExit1;
    private javax.swing.JButton buttonForExit2;
    private javax.swing.JButton buttonForSearch;
    private javax.swing.JButton exitbutton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel labelForAdmin;
    private javax.swing.JLabel labelForClass;
    private javax.swing.JLabel labelForCredit;
    private javax.swing.JLabel labelForGPA;
    private javax.swing.JLabel labelForMarks;
    private javax.swing.JLabel labelForModuleCode;
    private javax.swing.JLabel labelForModuleName;
    private javax.swing.JLabel labelForName;
    private javax.swing.JLabel labelForStudentAdmin;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JPanel panelForAddModule;
    private javax.swing.JPanel panelForCreateStudent;
    private javax.swing.JPanel panelForDeleteStudent;
    private javax.swing.JRadioButton radioButtonForByAdmin;
    private javax.swing.JRadioButton radioButtonForByName;
    private javax.swing.JTextArea textAreaForResults;
    private javax.swing.JTextField textFieldForAdmin;
    private javax.swing.JTextField textFieldForClass;
    private javax.swing.JTextField textFieldForCredit;
    private javax.swing.JTextField textFieldForGPA;
    private javax.swing.JTextField textFieldForMarks;
    private javax.swing.JTextField textFieldForModuleCode;
    private javax.swing.JTextField textFieldForModuleName;
    private javax.swing.JTextField textFieldForName;
    private javax.swing.JTextField textFieldForSearch;
    private javax.swing.JTextField textFieldForStudentAdmin;
    // End of variables declaration//GEN-END:variables
}
