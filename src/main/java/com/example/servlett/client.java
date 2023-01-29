package com.example.servlett;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class client {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 1243;
    public Integer L = 2020;
    public Integer id;

    public client(Integer L, int id) {
        this.L = L;
        this.id = id;
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            System.out.println("Устанавливаем соединение с " + HOST + " по порту " + PORT);
            Socket socket = new Socket(HOST, PORT);
            System.out.println("Соединение установлено");
            Scanner input = new Scanner(socket.getInputStream());
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            int id = input.nextInt();
            System.out.println(id + "-ый клиент начал свою работу!");
                    int L = input.nextInt();
                    System.out.println(L);
                    int R = input.nextInt();
                    ArrayList<Integer> arl = new ArrayList<Integer>();


                    for (Integer i = L; i < L + R; i++) {
                        if (i % 46189 == 0) {
                            output.println(i);
                            output.flush();
                            output.println(i);
                            output.flush();
                        }
                    }
                    output.println(-1);
                    output.flush();
                    output.println(-1);
                    output.flush();
                    //for(int i : arl) {
            //	output.println(i);
            //	output.flush();
            //}
            }catch (IOException e) {
            e.printStackTrace();
        }
        in.close();

    }


}