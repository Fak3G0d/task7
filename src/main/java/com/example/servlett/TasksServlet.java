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
@WebServlet("/tasks")
public class TasksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TasksServlet() {
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

        try(Socket socket= new Socket("127.0.0.1", 1243);
            Scanner socketIn = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());) {
            out.println("Gettasks");
            out.flush();
            ArrayList<String[]> tasks = new ArrayList<>();

            ArrayList<String> curSessionTasks = (ArrayList<String>) session.getAttribute("tasks");
            while(socketIn.hasNext()) {
                String[] s = socketIn.nextLine().split(" ");
                tasks.add(s);
            }
            request.setAttribute("tasks", tasks);
        }
        catch(IOException e) {
            request.setAttribute("connectError", true);
        }
        getServletContext().getRequestDispatcher("/tasks.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
