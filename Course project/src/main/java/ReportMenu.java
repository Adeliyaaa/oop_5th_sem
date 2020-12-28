import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.hssf.usermodel.XSSFWorkbook;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReportMenu extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton addStudentButton;
    private JButton removeStudentButton;
    private JList studentsList;
    private JComboBox comboBox1;
    private JButton confirmButton;
    private JButton setProfessorButton;
    private JButton saveToFileButton;
    private JLabel group;
    private JLabel subject;
    private JLabel prof;
    private JButton setGroupButton;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton setSubjectButton;
    private JComboBox comboBox4;
    private JRadioButton a2RadioButton;
    private JRadioButton a3RadioButton;
    private JRadioButton a4RadioButton;
    private JRadioButton a5RadioButton;
    private JLabel groupLabel;
    private JLabel errorLabel;

    public ReportMenu() {
        setBounds(300, 100, 800, 300);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        errorLabel.setForeground(Color.red);
        errorLabel.setVisible(false);
        Report report;
        ReportConverter converter = new ReportConverter("report.json");
        DefaultListModel<Student> listModel = new DefaultListModel<>();

        try {
            report = converter.convertFromJson();
            for (Student temp : report.getStudents()) {
                listModel.addElement(temp);
                group.setText(String.valueOf((int) report.getGroupNumber()));
                subject.setText(report.getSubject());
                prof.setText(report.getFullname());

            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ReportMenu.this, "Error! Couldn't upload the file");
            e.printStackTrace();
        }
        studentsList.setListData(listModel.toArray());
        ButtonGroup groupGrades = new ButtonGroup();
        groupGrades.add(a2RadioButton);
        groupGrades.add(a3RadioButton);
        groupGrades.add(a4RadioButton);
        groupGrades.add(a5RadioButton);


        listModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                studentsList.setListData(listModel.toArray());
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                studentsList.setListData(listModel.toArray());
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                studentsList.setListData(listModel.toArray());
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudent addStudent = new AddStudent(listModel::addElement);
                addStudent.setVisible(true);
            }
        });
        removeStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (studentsList.isSelectionEmpty()) {
                    errorLabel.setText("Select student first");
                    errorLabel.setVisible(true);
                } else {
                    listModel.remove(studentsList.getSelectedIndex());
                    errorLabel.setVisible(false);
                }
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (studentsList.isSelectionEmpty()) {
                    errorLabel.setText("Select student first");
                    errorLabel.setVisible(true);
                } else if (!a2RadioButton.isSelected() && !a3RadioButton.isSelected() &&
                        !a4RadioButton.isSelected() && !a5RadioButton.isSelected()) {
                    errorLabel.setText("Select grade value first");
                    errorLabel.setVisible(true);
                } else {
                    int selectedIndex = studentsList.getSelectedIndex();
                    if (a2RadioButton.isSelected())
                        listModel.getElementAt(selectedIndex).addGrade(2);
                    else if (a3RadioButton.isSelected())
                        listModel.getElementAt(selectedIndex).addGrade(3);
                    else if (a4RadioButton.isSelected())
                        listModel.getElementAt(selectedIndex).addGrade(4);
                    else
                        listModel.getElementAt(selectedIndex).addGrade(5);
                    errorLabel.setVisible(false);
                }
            }
        });

        setGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report anotherReport;
                //now you can add new group
                if (comboBox2.getSelectedItem().equals("Add new...")) {
                    String result = JOptionPane.showInputDialog(
                            ReportMenu.this,
                            "Fill in new group's number");
                    comboBox2.addItem(result);
                    anotherReport = new Report(Integer.parseInt(result), subject.getText());
                    String fileName = result + ".json";
                    ReportConverter newConverter = new ReportConverter(fileName);

                    try {
                        newConverter.convertToJson(anotherReport);
                    } catch (IOException x) {
                        JOptionPane.showMessageDialog(ReportMenu.this, "Error! Couldn't save to the file");
                        x.printStackTrace();
                    }

                } else {
                    String fileName = comboBox2.getSelectedItem().toString() + ".json";
                    ReportConverter anotherConverter = new ReportConverter(fileName);
                    try {
                        anotherReport = anotherConverter.convertFromJson();
                        listModel.removeAllElements();
                        for (Student temp : anotherReport.getStudents()) {
                            listModel.addElement(temp);
                            group.setText(String.valueOf((int) anotherReport.getGroupNumber()));
                            subject.setText(anotherReport.getSubject());
                            prof.setText(anotherReport.getFullname());
                        }
                    } catch (IOException x) {
                        JOptionPane.showMessageDialog(ReportMenu.this, "Error! Couldn't upload the group list");
                        x.printStackTrace();
                    }
                    group.setText(comboBox2.getSelectedItem().toString());
                    studentsList.setListData(listModel.toArray());
                    errorLabel.setVisible(false);
                }

            }
        });
        setSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //now you can add new subject
                if (comboBox4.getSelectedItem().equals("Add new...")) {
                    String result = JOptionPane.showInputDialog(
                            ReportMenu.this,
                            "Fill in new subject's name");
                    comboBox4.addItem(result);
                }
                else if (comboBox4.getSelectedIndex() > -1) {
                    subject.setText(comboBox4.getSelectedItem().toString());
                    errorLabel.setVisible(false);
                }
                //now you can add new subject
                 else {
                    errorLabel.setText("Select subject first");
                    errorLabel.setVisible(true);
                }
            }
        });
        setProfessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox3.getSelectedItem().equals("Add new...")) {
                    //now you can add new prof
                    String result = JOptionPane.showInputDialog(
                            ReportMenu.this,
                            "Fill in new professor's surname, name and lastname");
                    comboBox3.addItem(result);
                }
                else if (comboBox3.getSelectedIndex() > -1) {
                    prof.setText(comboBox3.getSelectedItem().toString());
                    errorLabel.setVisible(false);
                }   else {
                    errorLabel.setText("Select professor first");
                    errorLabel.setVisible(true);
                }
            }
        });

        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report newReport = new Report(Integer.parseInt(group.getText()), subject.getText());
                String[] arrSplit = prof.getText().split(" ");
                newReport.setProfessor(arrSplit[0], arrSplit[1], arrSplit[2]);
                List<Student> newstuds = new ArrayList<>();
                for (int i = 0; i < listModel.getSize(); i++) {
                    newstuds.add(listModel.elementAt(i));
                }
                newReport.setStudents(newstuds);
                String fileName;
                //Changed to fileName instead of writing all the groups
                fileName = comboBox2.getSelectedItem().toString() + ".json";
                ReportConverter newConverter = new ReportConverter(fileName);

                try {
                    newConverter.convertToJson(newReport);
                } catch (IOException x) {
                    JOptionPane.showMessageDialog(ReportMenu.this, "Error! Couldn't save to the file");
                    x.printStackTrace();
                }
            }

        });
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String filename = "Report.xls";
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet("FirstSheet");
                    Row headerRow = sheet.createRow(0);
                    Cell cell = headerRow.createCell(0);
                    cell.setCellValue("Group: " + group.getText());
                    cell = headerRow.createCell(1);
                    cell.setCellValue("Subject: " + subject.getText());
                    cell = headerRow.createCell(2);
                    cell.setCellValue("Professor: " + prof.getText());
                    cell = headerRow.createCell(3);
                    headerRow = sheet.createRow(1);
                    cell = headerRow.createCell(0);
                    cell.setCellValue("Surname");
                    cell = headerRow.createCell(1);
                    cell.setCellValue("Name");
                    cell = headerRow.createCell(2);
                    cell.setCellValue("Lastname");
                    cell = headerRow.createCell(3);
                    cell.setCellValue("Grade");

                    int rowNum = 2;
                    List<Student> newstuds = new ArrayList<>();
                    for (int i = 0; i < listModel.getSize(); i++) {
                        newstuds.add(listModel.elementAt(i));
                    }

                    for (Student stud : newstuds) {
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0)
                                .setCellValue(stud.getSurname());
                        row.createCell(1)
                                .setCellValue(stud.getName());
                        row.createCell(2)
                                .setCellValue(stud.getLastname());
                        row.createCell(3)
                                .setCellValue(stud.getGrade());
                    }
                    for (int i = 0; i < 4; i++) {
                        sheet.autoSizeColumn(i);
                    }
                    String Filename;
                    Filename = group.getText() + ' ' + subject.getText() + ".xlsx";
                    FileOutputStream fileOut = new FileOutputStream(Filename);
                    workbook.write(fileOut);
                    fileOut.close();
                    // Closing the workbook
                    workbook.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ReportMenu.this, "Error! Couldn't save to the file");
                    ex.printStackTrace();
                }
            }
        });
    }


    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("Save to Excel");
        panel2.add(buttonOK, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Exit");
        panel2.add(buttonCancel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveToFileButton = new JButton();
        saveToFileButton.setText("Save to File");
        panel1.add(saveToFileButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addStudentButton = new JButton();
        addStudentButton.setText("Add Student");
        panel4.add(addStudentButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeStudentButton = new JButton();
        removeStudentButton.setText("Remove Student");
        panel4.add(removeStudentButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Set Grade");
        panel4.add(label1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        a2RadioButton = new JRadioButton();
        a2RadioButton.setText("2");
        panel6.add(a2RadioButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        a4RadioButton = new JRadioButton();
        a4RadioButton.setText("4");
        panel6.add(a4RadioButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        a5RadioButton = new JRadioButton();
        a5RadioButton.setText("5");
        panel6.add(a5RadioButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        a3RadioButton = new JRadioButton();
        a3RadioButton.setText("3");
        panel6.add(a3RadioButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        panel7.add(confirmButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        group = new JLabel();
        Font groupFont = this.$$$getFont$$$("Lora", -1, -1, group.getFont());
        if (groupFont != null) group.setFont(groupFont);
        group.setText("Label");
        panel3.add(group, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        groupLabel = new JLabel();
        Font groupLabelFont = this.$$$getFont$$$(null, -1, -1, groupLabel.getFont());
        if (groupLabelFont != null) groupLabel.setFont(groupLabelFont);
        groupLabel.setText("Group:");
        panel3.add(groupLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        subject = new JLabel();
        Font subjectFont = this.$$$getFont$$$("Lora", -1, -1, subject.getFont());
        if (subjectFont != null) subject.setFont(subjectFont);
        subject.setText("Label");
        panel3.add(subject, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Subject:");
        panel3.add(label2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        prof = new JLabel();
        Font profFont = this.$$$getFont$$$("Lora", -1, -1, prof.getFont());
        if (profFont != null) prof.setFont(profFont);
        prof.setText("Label");
        panel3.add(prof, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Professor:");
        panel3.add(label3, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel8, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        setGroupButton = new JButton();
        setGroupButton.setText("Set Group");
        panel8.add(setGroupButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Add new...");
        defaultComboBoxModel1.addElement("8301");
        defaultComboBoxModel1.addElement("8302");
        defaultComboBoxModel1.addElement("8309");
        comboBox2.setModel(defaultComboBoxModel1);
        panel8.add(comboBox2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel9, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        setSubjectButton = new JButton();
        setSubjectButton.setText("Set Subject");
        panel9.add(setSubjectButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox4 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Add new...");
        defaultComboBoxModel2.addElement("Data Bases");
        defaultComboBoxModel2.addElement("Economics");
        defaultComboBoxModel2.addElement("Operation Systems");
        defaultComboBoxModel2.addElement("OOP");
        defaultComboBoxModel2.addElement("Methods of Optimization");
        defaultComboBoxModel2.addElement("Metrology");
        defaultComboBoxModel2.addElement("Schematics");
        defaultComboBoxModel2.addElement("Foreign Language");
        comboBox4.setModel(defaultComboBoxModel2);
        panel9.add(comboBox4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel10, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        setProfessorButton = new JButton();
        setProfessorButton.setText("Set Professor");
        panel10.add(setProfessorButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox3 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("Add new...");
        defaultComboBoxModel3.addElement("Novakova Natalya Evgenevna");
        defaultComboBoxModel3.addElement("Karimov Artur Iskandarovich");
        defaultComboBoxModel3.addElement("Krasilnikov Aleksander Vitalevich");
        defaultComboBoxModel3.addElement("Isakov Aleskander Borisovich");
        defaultComboBoxModel3.addElement("Kalmichkov Vitaly Anatolevich");
        defaultComboBoxModel3.addElement("Goryachev Aleksander Vadimovich");
        defaultComboBoxModel3.addElement("Tutueva Aleksandra Vadimovna");
        comboBox3.setModel(defaultComboBoxModel3);
        panel10.add(comboBox3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        studentsList = new JList();
        Font studentsListFont = this.$$$getFont$$$("Lora", -1, -1, studentsList.getFont());
        if (studentsListFont != null) studentsList.setFont(studentsListFont);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        studentsList.setModel(defaultListModel1);
        panel3.add(studentsList, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        errorLabel = new JLabel();
        errorLabel.setText("Label");
        contentPane.add(errorLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}

