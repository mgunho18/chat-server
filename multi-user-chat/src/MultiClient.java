import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MultiClient {

	public static void main(String[] args) {
		MultiClient multiClient = new MultiClient();
		multiClient.start();
	}

	private void start() {
		Socket socket = null;
		BufferedReader reader;

		try {
			socket = new Socket("localhost", 8000);
			System.out.println("[서버와 연결되었습니다]");

			String name = "user" + (int)(Math.random()*10);
			Thread sendThread = new SendThread(socket, name);
			sendThread.start();

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true) {
				String inputMsg = reader.readLine();
				if(("[" + name + "]님이 나가셨습니다").equals(inputMsg))
					break;
				if(inputMsg.startsWith(name))
					continue;
				System.out.println("From:" + inputMsg);
			}
		} catch (IOException e) {
			System.out.println("[서버 접속끊김]");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[서버 연결종료]");
	}
}
