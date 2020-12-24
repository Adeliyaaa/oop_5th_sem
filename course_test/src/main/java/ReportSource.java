import java.io.IOException;
import java.util.List;

public interface ReportSource {
    void toFile(Report report) throws IOException;
    Report fromFile() throws IOException;
}