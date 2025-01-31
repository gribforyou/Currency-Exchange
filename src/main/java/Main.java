import model.ModelManager;
import model.exceptions.LinesNotFoundException;
import model.exceptions.UnavailableDBException;
import model.exceptions.UniqueConstraintFailedException;

public class Main {
    public static void main(String[] args) throws UnavailableDBException, LinesNotFoundException {
        var result = ModelManager.getCurrencies(100);
        for (var line : result) {
            System.out.println(line.id() + " " + line.name() + " " + line.code());
        }
    }
}
