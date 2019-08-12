import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet()
public class Connect extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String id = request.getParameter("id");
        try {
            String url = "jdbc:mysql://159.69.65.178/avtomaster?serverTimezone=Europe/Kiev&useSSL=false";
            String username = "root";
            String password = "cjkywt";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {

                String sql = "SELECT * FROM cards WHERE card_number =?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    writer.println("Хуйню");
                    writer.println("Какую-то");
                    writer.println("Выведи");
                }
                else {
                    writer.println(resultSet.getString(2));
                    writer.println(resultSet.getString(3));
                    String companyId = resultSet.getString(4);
                    String sqlCompany = "SELECT * FROM company WHERE company_id ='" + companyId + "'";
                    PreparedStatement preparedStatementCompany = conn.prepareStatement(sqlCompany);
                    //preparedStatement.setString(1,companyId);
                    ResultSet resultSetCompany = preparedStatementCompany.executeQuery();
                    while (resultSetCompany.next()) {

                        writer.println(resultSetCompany.getString("company_name"));
                    }
                }


            }

        } catch (Exception ex) {
            writer.println("Connection failed...");
            writer.println(ex);
        } finally {
            writer.close();
        }
    }
}