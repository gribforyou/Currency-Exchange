package servelets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CurrencyDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelManager;
import model.exceptions.UnavailableDBException;
import model.exceptions.UniqueConstraintFailedException;
import java.io.IOException;

import static service.CurrencyInputValidator.isValid;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private static final int CURRENCIES_LIMIT = 15;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            var result = ModelManager.getCurrencies(CURRENCIES_LIMIT);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), result);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (UnavailableDBException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");

        CurrencyDao dao = new CurrencyDao(null, name, code, sign);

        if(!isValid(dao)){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try{
            var result = ModelManager.addCurrency(dao);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), result);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (UnavailableDBException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (UniqueConstraintFailedException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT);
        }
    }
}
