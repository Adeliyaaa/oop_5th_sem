import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
       //List <Student> studs = new ArrayList<>();
        //studs.add(new Student("adelia", "sayarova", "mansurovna"));
        //studs.add(new Student("ekaterina", "melnik", "gregorevna"));

        Report report = new Report(8302, "OOP");
        report.addStudent("adelia", "sayarova", "mansurovna");
        report.addStudent("ekaterina", "melnik", "gregorevna");
        report.addGradeToAll(5);
       ReportConverter converter = new ReportConverter("report.json");
        try {converter.convertToJson(report);}
        catch (IOException e) {e.printStackTrace();}

        try {
            Report readReport = converter.convertFromJson();
            //readReport.showAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

       // System.out.println(studs.toString());
       /* converter Converter = new converter("studs.json");
        try {Converter.convertToJson(studs);}
        catch (IOException e) {e.printStackTrace();}

        try {
            List<Student> readStuds = Converter.convertFromJson();
            readStuds.forEach(stud -> out.println(stud.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }  */

        //ReportMenu menu = new ReportMenu();
        //menu.setVisible(true);
    }
}