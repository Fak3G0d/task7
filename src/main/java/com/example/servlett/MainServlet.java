import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Main
 */
@WebServlet("")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
			ArrayList<String> taskIds = new ArrayList<>();
			session.setAttribute("tasks", taskIds);
		}


		String L = request.getParameter("L");
		String R = request.getParameter("R");

		if (L != null && R != null) {
			try(Socket socket= new Socket("127.0.0.1", 1243);
				Scanner socketIn = new Scanner(socket.getInputStream());
				PrintWriter out = new PrintWriter(socket.getOutputStream());) {
				out.println("Addtask " + L + " " + R);
				out.flush();

				String serverResponse = socketIn.nextLine();

				if (serverResponse == "Failed") {
					request.setAttribute("submitResult", "error");
				}
				else {
					request.setAttribute("submitResult", "success");

					ArrayList<String> taskIds = (ArrayList<String>) session.getAttribute("tasks");
					taskIds.add(serverResponse.split(" ")[1]);
					session.setAttribute("tasks", taskIds);
				}

			} catch (IOException e) {
				request.setAttribute("submitResult", "error");
				e.printStackTrace();
			}
		}
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
