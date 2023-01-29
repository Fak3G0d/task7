import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class server {
    public static Integer interv = 1000000;
    public static final int PORT = 1243;
    public static ArrayList<handler> clients = new ArrayList<handler>();
    public static ArrayList<String> tasks = new ArrayList<String>();
    public static ArrayList<ArrayList<String>> tasksans = new ArrayList<ArrayList<String>>();
    public static int taskid = 0;
    public  static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Integer leftside = 10000;
        Integer interv = 1000000;
        String ans = "";

        try {
            System.out.println("Готовимся начать прослушивать порт " + PORT);
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Сервер запущен, прослушиваем порт.");

            Thread[] threads_arr = new Thread[300];
            System.out.println("Hello, i`m " + Thread.currentThread().getName());
            long otr = (300000000 + 3) / 3;
            i = 0;
            while (true) {
                Socket socket = server.accept();
                clients.add(new handler(socket, i));
                threads_arr[i] = new Thread(clients.get(i));
                threads_arr[i].start();
                i++;
            }


            //System.out.println("Соединение установлено");
            //boolean flag = false;

            //System.out.println("Закрываем сокет и прекращаем слушать порт");
            //server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class taker implements Runnable {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 1243;
    public long L = 1243;
    public long R = 1243;
    public long id;
    public Socket S;

    public taker(long L, long R, Socket S, int id) {
        this.L = L;
        this.R = R;
        this.S = S;
        this.id = id;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        try {
            System.out.println("Устанавливаем соединение с " + HOST + " по порту " + PORT);
            System.out.println("Соединение установлено");
            Scanner input = new Scanner(this.S.getInputStream());
            Scanner servinp = new Scanner(System.in);
            PrintWriter output = new PrintWriter(this.S.getOutputStream());
            output.println(this.id);
            System.out.println(id);
            output.println(this.L);
            output.println(this.R);
            output.flush();

            while (true) {
                if (input.hasNext()) {
                    String ans = input.next();
                    System.out.println(ans);
                    if (!ans.equals("-1")) {
                        System.out.println(this.id + "-ый: " + ans);
                    } else {
                        System.out.println("Введите L, R в разных строчках для клиента # " + id);
                        this.L = servinp.nextLong();
                        this.R = servinp.nextLong();
                        output.println(this.L);
                        output.println(this.R);
                        output.flush();
                    }
                } else {
                    output.println(this.L);
                    output.println(server.interv);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        in.close();
        try {
            this.S.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class handler implements Runnable {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 1243;
    public int id;
    public Socket S;

    public handler(Socket S, int id) {
        this.S = S;
        this.id = id;
    }

    @Override
    public void run() {

        Scanner in = new Scanner(System.in);
        try {
            Thread[] threads_arr = new Thread[1];
            score obrabotka;
//            obrabotka = new taker(100, 10000000, this.S, this.id);
//            threads_arr[0] = new Thread(obrabotka);
//            threads_arr[0].start();
            Scanner input = new Scanner(this.S.getInputStream());
            PrintWriter output = new PrintWriter(this.S.getOutputStream());
            while (input.hasNext()) {
                //if (input.hasNext()) {
                    System.out.println("GGGGGG");
                    String a = input.nextLine();

                    if (a.indexOf("Addtask") != -1) {
                        String[] temp = a.split(" ");
                        obrabotka = new score(Long.valueOf(temp[1]), Long.valueOf(temp[2]), this.S, this.id);
                        threads_arr[0] = new Thread(obrabotka);
                        threads_arr[0].start();
                        System.out.println("TASKKK");
                        output.println("ok qwe");
                        //in.close();
                        continue;
                    }
                    if (a.equals("Getworkers")) {
                        System.out.println("WWWORKERS");
                        for(int i = 0; i < server.i; i++){
                            System.out.println(i + " 127.0.0.1");
                            if(i == this.id){
                                output.println("*"+i + " ok");
                            }else{
                                output.println(i + " ok");
                            }
                            output.flush();
                        }
                        output.println("-1 g");
                        System.out.println("WORKERS");
                        output.flush();
                        in.close();
                        break;
                    }
                    if (a.indexOf("Getresult") != -1) {
                        String[] temp = a.split(" ");
                        if(server.tasksans.size() > Integer.valueOf(temp[1])-1 && server.tasksans.get(Integer.valueOf(temp[1])-1) != null){
                            output.println("Ok");
                            output.flush();
                            for (String i:server.tasksans.get(Integer.valueOf(temp[1])-1)){
                                output.println(i);
                                output.flush();
                            }

                            break;
                        }else{
                            output.println("No Task");
                            output.flush();
                        }
                        System.out.println("RESULT");
                        break;
                    }
                    if (a.equals("Gettasks")) {
                        for (String i :server.tasks) {
                            output.println(i);
                            output.flush();
                        }
                        System.out.println("TASKS");
                        break;
                    }
                    if (a.indexOf("Exit") != -1) {
                        String[] temp = a.split(" ");
                        System.out.println(a);
                        server.clients.get(Integer.valueOf(temp[1])).S.close();
                        server.clients.remove(Integer.valueOf(temp[1]));
                        if(Integer.valueOf(temp[1]) < this.id){
                            this.id--;
                        }
                        output.println("Disconected");
                        output.flush();
                        break;
                    }
                //}
            }
            server.clients.remove(this.id);
            server.i--;
            in.close();
            try {
                this.S.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}

class score implements Runnable{
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 1243;
    public Long L;
    public Long R;
    public Socket S;
    public int id;

    public score(Long L, Long R, Socket S, int id) {
        this.L = L;
        this.R = R;
        this.S = S;
        this.id = id;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        try {
            PrintWriter output = new PrintWriter(this.S.getOutputStream());
            server.tasksans.add(new ArrayList<String>());
                    for (Long i = L; i < L + R; i++) {
                        if (i % 46189 == 0) {
                            output.println(server.taskid + " " + L + " " + R + " " + i + " " + 0);
                            output.flush();
                            output.println(server.taskid + " " + L + " " + R + " " + i + " " + 0);
                            output.flush();
                            server.tasksans.get(server.taskid).add(server.taskid + "| " + L + " " + R + " " + i);;
                        }
                    }
                    output.println(server.taskid + " " + L + " " + R + " " + -1 + " " + -1);
                    output.flush();
                    output.println(server.taskid + " " + L + " " + R + " " + -1 + " " + -1);
                    output.flush();
                    server.taskid++;
                    server.tasks.add(server.taskid + " " + L + " " + R + " " + -1 + " " + 1);

            }catch (IOException e) {
            e.printStackTrace();
        }
        in.close();

    }


}