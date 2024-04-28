import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceiveThread extends Thread {

	private static final List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;

	public ReceiveThread(Socket socket) {

		this.socket = socket;
		try {
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			list.add(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		String name = "";
		try {
			name = reader.readLine();
			System.out.println("[" + name + " 새 연결 생성]");
			sendAll(name + "님이 들어오셨습니다");

			while (reader != null) {
				String inputMsg = reader.readLine();
				if("quit".equals(inputMsg))
					break;
				sendAll(name + ">>" + inputMsg);
			}
		} catch (IOException e) {
			System.out.println("[" + name + " 접속 끊김]");
		} finally {
			sendAll("[" + name + "]님이 나가셨습니다");
			list.remove(writer);
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[" + name + " 연결종료]");
	}

	private void sendAll(String s) {
		for (PrintWriter writer : list) {
			writer.println(s);
			writer.flush();
		}
	}
}
