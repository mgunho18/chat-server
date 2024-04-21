import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {

	public static void main(String[] args) {
		MultiServer multiServer = new MultiServer();
		multiServer.start();
	}

	private void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(8000);
			while (true) {
				System.out.println("[클라이언트 연결 대기 중]");
				socket = serverSocket.accept();

				ReceiveThread receiveThread = new ReceiveThread(socket);
				receiveThread.run();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
					System.out.println("[서버 종료]");
				} catch(IOException e) {
					e.printStackTrace();
					System.out.println("[서버 소켓 통신 오류]");
				}
			}
		}

	}


}
