import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;

public class BioClient {
    public static void main(String[] args) throws UnknownHostException,
            IOException {
        String ip = "127.0.0.1";
        int port = 8001;
        Socket socket = new Socket(ip, port);
        OutputStream out = socket.getOutputStream();
        out.write("hell server呵呵".getBytes());
        out.write("end".getBytes());
//        InputStream in = socket.getInputStream();
//        byte[] buffer = new byte[64];
//        while (in.read(buffer) != -1) {
//            System.out.println(new String(buffer));
//        }
//        in.close();

        Reader reader = new InputStreamReader(socket.getInputStream());
        char[] buffer = new char[64];
        StringBuffer sb = new StringBuffer();
        while (reader.read(buffer) != -1) {
            sb.append(new String(buffer));
        }
        System.out.println(sb);

        out.close();
        socket.close();
    }

//    public static void main(String args[]) throws Exception {
//        //为了简单起见，所有的异常都直接往外抛
//        String host = "127.0.0.1";  //要连接的服务端IP地址
//        int port = 8899;   //要连接的服务端对应的监听端口
//        //与服务端建立连接
//        Socket client = new Socket(host, port);
//        //建立连接后就可以往服务端写数据了
//        Writer writer = new OutputStreamWriter(client.getOutputStream());
//        writer.write("Hello Server.");
//        writer.write("eof");
//        writer.flush();
//        //写完以后进行读操作
//        Reader reader = new InputStreamReader(client.getInputStream());
//        char chars[] = new char[64];
//        int len;
//        StringBuffer sb = new StringBuffer();
//        String temp;
//        int index;
//        while ((len = reader.read(chars)) != -1) {
//            temp = new String(chars, 0, len);
//            if ((index = temp.indexOf("eof")) != -1) {
//                sb.append(temp.substring(0, index));
//                break;
//            }
//            sb.append(new String(chars, 0, len));
//        }
//        System.out.println("from server: " + sb);
//        writer.close();
//        reader.close();
//        client.close();
//    }
}
