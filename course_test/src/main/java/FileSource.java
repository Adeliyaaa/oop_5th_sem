import java.io.IOException;
import java.util.List;

public interface FileSource {
    void toFile(List<Student> list) throws IOException;
    List<Student> fromFile() throws IOException;
}