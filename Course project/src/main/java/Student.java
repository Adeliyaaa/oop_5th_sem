 public class Student {
    private String surname;
     private String name;
     private String lastname;
     private int grade;

    public Student(String name, String surname, String lastname) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void addGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return surname + ' ' + name + ' ' + lastname + ' ' + grade;
    }
}