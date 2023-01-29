/*
 * Пример получения списка задач с сервера
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
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
@WebServlet("/result")
public class ResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultServlet() {
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

        String TaskID = request.getParameter("id");

        if (TaskID != null) {
            try (Socket socket = new Socket("127.0.0.1", 1243);
                 Scanner socketIn = new Scanner(socket.getInputStream());
                 PrintWriter out = new PrintWriter(socket.getOutputStream());) {
                out.println("Getresult " + TaskID);
                out.flush();
                String serverResponse = socketIn.nextLine();
                if (Objects.equals(serverResponse, "No Task")) {
                    request.setAttribute("status", "no task");
                } else if (Objects.equals(serverResponse, "Not Ready")) {
                    request.setAttribute("status", "not ready");
                } else {
                    ArrayList<String> results = new ArrayList<>();
                    while (socketIn.hasNext()) {
                        String s = socketIn.nextLine();
                        results.add(s);
                    }
                    request.setAttribute("results", results);
                    request.setAttribute("status", "success");
                }
            } catch (IOException e) {
                request.setAttribute("status", "connect error");
            }
        }
        getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
