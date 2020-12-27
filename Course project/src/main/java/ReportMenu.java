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
                if (studentsList.isSelectionEmpty()){
                    errorLabel.setText("Select student first");
                    errorLabel.setVisible(true);
                }
                else
                {
                    listModel.remove(studentsList.getSelectedIndex());
                    errorLabel.setVisible(false);
                }
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (studentsList.isSelectionEmpty()){
                    errorLabel.setText("Select student first");
                    errorLabel.setVisible(true);
                }
                else if (!a2RadioButton.isSelected() && !a3RadioButton.isSelected() &&
                !a4RadioButton.isSelected() && !a5RadioButton.isSelected()){
                    errorLabel.setText("Select grade value first");
                    errorLabel.setVisible(true);
                }
                else {
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
                String fileName = comboBox2.getSelectedItem().toString() + ".json";
                ReportConverter anotherConverter = new ReportConverter(fileName);
               // if (comboBox2.getSelectedIndex() > -1)
                //{
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
               // }
               // else{
                 //   errorLabel.setText("Select group first");
                   // errorLabel.setVisible(true);
                //}


            }
        });
        setSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox4.getSelectedIndex() > -1){
                    subject.setText(comboBox4.getSelectedItem().toString());
                    errorLabel.setVisible(false);
                }
                else{
                    errorLabel.setText("Select subject first");
                    errorLabel.setVisible(true);
                }
            }
        });
        setProfessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox3.getSelectedIndex() > -1){
                    prof.setText(comboBox3.getSelectedItem().toString());
                    errorLabel.setVisible(false);
                }
                else{
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
                ReportConverter newConverter = new ReportConverter("report.json");
                if (comboBox2.getSelectedItem().toString().equals("8302"))
                    newConverter = new ReportConverter("8302.json");
                else if (comboBox2.getSelectedItem().toString().equals("8309"))
                    newConverter = new ReportConverter("8309.json");
                else if (comboBox2.getSelectedItem().toString().equals("8301"))
                    newConverter = new ReportConverter("8301.json");

                try {newConverter.convertToJson(newReport);}
                catch (IOException x) {
                    JOptionPane.showMessageDialog(ReportMenu.this, "Error! Couldn't save to the file");
                    x.printStackTrace();}
            }

        });
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String filename = "Report.xls" ;
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

                    for (Student stud: newstuds) {
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
                    for(int i = 0; i < 4; i++) {
                        sheet.autoSizeColumn(i);
                    }
                        String Filename;
                    Filename = group.getText() + ' ' + subject.getText() + ".xlsx";
                        FileOutputStream fileOut = new FileOutputStream(Filename);
                        workbook.write(fileOut);
                        fileOut.close();
                        // Closing the workbook
                        workbook.close();

                    }
                catch(Exception ex){
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


}

