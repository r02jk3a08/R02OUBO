
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Page1CheckServlet
 */
@WebServlet("/Page2Check")
public class Page2CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Page2CheckServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String driverName = "oracle.jdbc.driver.OracleDriver";
		final String url = "jdbc:oracle:thin:@192.168.54.226:1521/orcl";
		final String id = "OUBO";
		final String pass = "TOUSEN";

		String sEmail = request.getParameter("email");
		String sNumA = request.getParameter("numa");
		String sNumB = request.getParameter("numb");

		boolean NumCheck1 = true;
		NumCheck1 = Num(sNumA);
		String sError = null;

		if (NumCheck1 == true) {
			try {

				Class.forName(driverName);
				Connection connection = DriverManager.getConnection(url, id, pass);
				PreparedStatement st = connection.prepareStatement("Select email From OUBO where  numa=? and numb=?");
				st.setString(1, sNumA);
				st.setString(2, sNumB);

				ResultSet rs = st.executeQuery();

				if (rs.next() == true) {
					sError = "Same";
					request.setAttribute("Error", sError);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/page1.jsp");
					rd.forward(request, response);
				} else {
					request.setAttribute("email", sEmail);
					request.setAttribute("numa", sNumA);
					request.setAttribute("numb", sNumB);
					RequestDispatcher rd = request.getRequestDispatcher("/Page2Print");
					rd.forward(request, response);
				}

			} catch (SQLException e) {
				System.out.println("SQLException");
				response.getWriter().println("SQLException");
				e.printStackTrace();
				e.printStackTrace(response.getWriter());
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException");
				response.getWriter().println("ClassNotFoundException");
				e.printStackTrace();
				e.printStackTrace(response.getWriter());
			}
		} else {
			sError = "Num";
			request.setAttribute("Error", sError);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/page1.jsp");
			rd.forward(request, response);
		}

	}

	public boolean Num(String sNum) {
		char[] Change = new char[7];
		int Num = 0;
		boolean Ans =false;
		for (int a = 0; a < Change.length; a++) {
			Change[a] = sNum.charAt(a);
		}
		for (int b = 0; b < Change.length; b++) {
			Num += Character.getNumericValue(Change[b]);
		}
		if (Num % 9 == 0) {
			Ans = true;
		}
		return Ans;

	}
}
