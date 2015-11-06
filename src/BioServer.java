import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {

    public static void main(String[] args) throws IOException {
        int port = 8001;
        ServerSocket server = new ServerSocket(port);
        while (true) {
            Socket socket = server.accept();

            new Thread(new Task(socket)).start();
//            StringBuffer sb = new StringBuffer();
//            InputStream in = socket.getInputStream();
//            byte[] buffer = new byte[64];
//            while (in.read(buffer) != -1) {
//                String temp = new String(buffer);
//                int index;
//                if ((index = temp.indexOf("end")) != -1) {
//                    sb.append(temp.substring(0, index));
//                    break;
//                }
//                if ("end".equals(new String(buffer))) {
//                    break;
//                }
//                sb.append(new String(buffer));
//            }
//            System.out.println(sb);
//            OutputStream out = socket.getOutputStream();
//            out.write("hello client".getBytes());
//            in.close();
//            out.close();
//            socket.close();
        }
//        server.close();

    }

//    public static void main(String args[]) throws IOException {
//        //为了简单起见，所有的异常信息都往外抛
//        int port = 8899;
//        //定义一个ServerSocket监听在端口8899上
//        ServerSocket server = new ServerSocket(port);
//        //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
//        Socket socket = server.accept();
//        //跟客户端建立好连接之后，我们就可以获取socket的InputStream，并从中读取客户端发过来的信息了。
//        Reader reader = new InputStreamReader(socket.getInputStream());
//        char chars[] = new char[64];
//        int len;
//        StringBuilder sb = new StringBuilder();
//        String temp;
//        int index;
//        while ((len = reader.read(chars)) != -1) {
//            System.out.println("111111111111");
//            temp = new String(chars, 0, len);
//            if ((index = temp.indexOf("eof")) != -1) {//遇到eof时就结束接收
//                sb.append(temp.substring(0, index));
//                break;
//            }
//            sb.append(temp);
//        }
//        System.out.println("from client: " + sb);
//        //读完后写一句
//        Writer writer = new OutputStreamWriter(socket.getOutputStream());
//        writer.write("Hello Client.");
//        writer.flush();
//        writer.close();
//        reader.close();
//        socket.close();
//        server.close();
//    }
    public class Inner {

    }
}

class Task implements Runnable {
    Socket socket;

    public Task(Socket socket) {
        super();
        this.socket = socket;
    }

    @SuppressWarnings("null")
    @Override
    public void run() {
        try {
            this.handSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("resource")
    private void handSocket() throws Exception {
        StringBuffer sb = new StringBuffer();
        InputStream in = null;
        try {
            in = this.socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[64];
        while (in.read(buffer) != -1) {
            String temp = new String(buffer);
            int index;
            if ((index = temp.indexOf("end")) != -1) {
                sb.append(temp.substring(0, index));
                break;
            }
            if ("end".equals(new String(buffer))) {
                break;
            }
            sb.append(new String(buffer));
        }
        System.out.println(sb);
        OutputStream out = null;
        out = this.socket.getOutputStream();
        out.write("hello client".getBytes());
        in.close();
        out.close();
        this.socket.close();
    }

}
