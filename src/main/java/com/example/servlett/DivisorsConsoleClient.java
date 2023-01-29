import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DivisorsConsoleClient {

	public static void main(String[] args) {
		String ip = args.length>0?args[0]:"127.0.0.1";
		final int port = 1243;
		
		Scanner consoleIn = new Scanner(System.in);
		while(consoleIn.hasNext()) {
			String s = consoleIn.nextLine();
			if(s.equals("Exit"))
				break;
			else {
				try (Socket socket = new Socket(ip, port);
					Scanner socketIn = new Scanner(socket.getInputStream());
					PrintWriter socketOut = new PrintWriter(socket.getOutputStream());)
				{
					socketOut.println(s);
					socketOut.flush();
					while(socketIn.hasNext()) {
						String str = socketIn.nextLine();
						System.out.println(str);
					}
					socketOut.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
