package servlets;

import beans.ResultsBean;
import com.google.gson.Gson;
import utils.AreaChecker;
import utils.CoordinatesValidator;
import utils.ErrorUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {
    public static final int SC_INTERNAL_SERVER_ERROR = 500;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            double x = getDouble(request, "x");
            double y = getDouble(request, "y");
            double r = getDouble(request, "r");
            String type = request.getParameter("type");

            ResultsBean bean = (ResultsBean) request.getSession().getAttribute("results");
            if (bean == null) {
                bean = new ResultsBean();
                request.getSession().setAttribute("results", bean);
            }

            if (type.equals("clear")) {
                bean.clearResults();
                return;
            }

            CoordinatesValidator validator = new CoordinatesValidator(x, y, r, type);
            if (!validator.checkData()) {
                ErrorUtil.sendError(response, 422, "Unprocessable Entity: Error during validation");
                return;
            }

            ResultsBean.Result result = new ResultsBean.Result(String.valueOf(x),
                    String.valueOf(y), String.valueOf(r), AreaChecker.isInArea(x, y, r));
            if (!(type.equals("check"))) {
                bean.addResult(result);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(result));
        } catch (NumberFormatException e) {
            ErrorUtil.sendError(response, 422, "Unprocessable Entity: Invalid number format");
        } catch (Exception e) {
            e.printStackTrace();
            ErrorUtil.sendError(response, 500, "Internal Server Error");
        }
    }

    public static double getDouble(HttpServletRequest request, String parameter) {
        String param = request.getParameter(parameter);
        return Double.parseDouble(param.replace(",", "."));
    }
}
