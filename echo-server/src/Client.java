import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		Socket socket = null;			//Server와 통신하기 위한 Socket
		BufferedReader serverReader = null;     //Server로부터 데이터를 읽어들이기 위한 입력스트림
		BufferedReader keyboardReader = null;   //키보드로부터 읽어들이기 위한 입력스트림
		PrintWriter writer = null;            	//서버로 데이터를 내보내기 위한 출력 스트림
		InetAddress ia = null;

		try {
			ia = InetAddress.getByName("127.0.0.1");
			socket = new Socket(ia,5050);
			serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			keyboardReader = new BufferedReader(new InputStreamReader(System.in));
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

			System.out.println(socket);
		}catch(IOException e) {
			e.printStackTrace();
		}

		try {
			System.out.print("서버로 보낼 메세지 : ");
			String data = keyboardReader.readLine();    //키보드로부터 입력
			writer.println(data);                       //서버로 데이터 전송
			writer.flush();

			String str2 = serverReader.readLine();      //서버로부터 되돌아오는 데이터 읽어들임
			System.out.println("서버로부터 되돌아온 메세지 : " + str2);

			serverReader.close();
			keyboardReader.close();
			writer.close();
			socket.close();

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
