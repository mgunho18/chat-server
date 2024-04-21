import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {

		Socket socket = null;                //Client와 통신하기 위한 Socket
		ServerSocket server_socket = null;   //서버 생성을 위한 ServerSocket
		BufferedReader reader = null;        //Client로부터 데이터를 읽어들이기 위한 입력 스트림
		PrintWriter writer = null;           //Client로 데이터를 내보내기 위한 출력 스트림

		try{
			server_socket = new ServerSocket(5050);

		}catch(IOException e) {
			System.out.println("해당 포트가 열려있습니다.");
		}
		try {
			while(true) {
				System.out.println("연결 대기중..");
				socket = server_socket.accept();    //서버 생성 , Client 접속 대기

				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("연결 수락됨! 접속호스트명 : " + isa.getHostName());

				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //입력스트림 생성

				String str = reader.readLine();    //Client로부터 데이터를 읽어옴

				System.out.println("Client로 부터 온 메세지 : " + str);

				//출력스트림 생성
				writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

				writer.write(str);
				writer.flush();

				reader.close();
				writer.close();
				socket.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}