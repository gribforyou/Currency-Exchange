package servelets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelManager;
import model.exceptions.LinesNotFoundException;
import model.exceptions.UnavailableDBException;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyByCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var result = req.getRequestURI().split("/");
        if (result.length != 3) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            var currency = ModelManager.getCurrencyByCode(result[2]);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), currency);
        } catch (UnavailableDBException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (LinesNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
