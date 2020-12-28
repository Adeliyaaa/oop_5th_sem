
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Report {

    private List <Student> ListOfStudents = new ArrayList<>();
    private int groupNumber;
    private String Subject;
    private String professorName;
    private String professorSurname;
    private String professorLastname;

    public List<Student> getStudents() {
        return ListOfStudents;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public String getSubject() {
        return Subject;
    }

    public String getProfessorName() {
        return professorName;
    }

    public String getProfessorSurname() {
        return professorSurname;
    }

    public String getProfessorLastname() {
        return professorLastname;
    }

    public String getFullname(){
        if (professorName != null)
            return professorName + ' ' + professorSurname + ' ' + professorLastname;
        else
            return "Name not set";
    }

    public Report(int groupNumber, String subject) {
        this.groupNumber = groupNumber;
        Subject = subject;
    }

    public void setProfessor (String name, String surname, String lastname){
        this.professorName = name;
        this.professorSurname = surname;
        this.professorLastname = lastname;
    }

    public void editGroupNumber(int group){
        this.groupNumber = group;
    }

    public void editSubject (String subject){
        this.Subject = subject;
    }

    public void addStudent (String name, String surname, String lastname){
        Student temp = new Student(name, surname, lastname);
        ListOfStudents.add(temp);
    }

    public void setGrade (String name, String surname, String lastname, int grade){
        Student temp = findStudent(name, surname, lastname);
        temp.addGrade(grade);
    }

    public void setStudents (List <Student> studs){
        ListOfStudents = new ArrayList<>(studs);
    }

    public Student findStudent (String name, String surname, String lastname){
        for (Student temp : ListOfStudents){
            if (temp.getName().equals(name) && temp.getSurname().equals(surname) && temp.getLastname().equals(lastname))
                return temp;
        }
        return null;
    }

    public void addGradeToAll(int grade){
        for (Student temp : ListOfStudents){
            temp.addGrade(grade);
        }

    }

}

