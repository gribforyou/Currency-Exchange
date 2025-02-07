import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ExchangeRateDao;
import model.ModelManager;
import model.exceptions.LinesNotFoundException;
import model.exceptions.UnavailableDBException;
import model.exceptions.UniqueConstraintFailedException;

import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws UnavailableDBException, LinesNotFoundException, UniqueConstraintFailedException, IOException {
        var result = ModelManager.getCurrencies(15);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new PrintWriter(System.out), result);
    }
}
