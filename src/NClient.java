import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NClient {

    //定义检测Sockethannel的Selector对象
    private Selector selector = null;
    static final int PORT = 30000;
    //定义处理编码的字符集
    private Charset charset = Charset.forName("GBK");
    //客户端SocketChannel
    private SocketChannel sc = null;

    public void init() throws IOException {
        this.selector = Selector.open();
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", NClient.PORT);
        //调用open的静态方法创建连接指定的主机的SocketChannel
        this.sc = SocketChannel.open(isa);
        //设置该sc已非阻塞的方式工作
        this.sc.configureBlocking(false);
        //将SocketChannel对象注册到指定的Selector
        this.sc.register(this.selector, SelectionKey.OP_READ);
        //启动读取服务器数据端的线程
        new ClientThread().start();
//        //创建键盘输入流
//        Scanner scan = new Scanner(System.in);
//        while (scan.hasNextLine()) {
//            //读取键盘的输入
//            String line = scan.nextLine();
//            //将键盘的内容输出到SocketChanenel中
//            this.sc.write(this.charset.encode(line));
//        }
        this.sc.write(this.charset.encode(Thread.currentThread().getName()
            + ":" + Thread.currentThread().toString()));
    }

    //定义读取服务器端的数据的线程
    private class ClientThread extends Thread {

        @Override
        public void run() {
            try {
                while (NClient.this.selector.select() > 0) {
                    //遍历每个有可能的IO操作的Channel对银行的SelectionKey
                    for (SelectionKey sk : NClient.this.selector.selectedKeys()) {
                        //删除正在处理的SelectionKey
                        NClient.this.selector.selectedKeys().remove(sk);
                        //如果该SelectionKey对应的Channel中有可读的数据
                        if (sk.isReadable()) {
                            //使用NIO读取Channel中的数据
                            SocketChannel sc = (SocketChannel) sk.channel();
                            String content = "";
                            ByteBuffer bff = ByteBuffer.allocate(1024);
                            while (sc.read(bff) > 0) {
                                sc.read(bff);
                                bff.flip();
                                content += NClient.this.charset.decode(bff);
                            }
                            //打印读取的内容
                            System.out.println("聊天信息:" + content);
                            sk.interestOps(SelectionKey.OP_READ);

                        }
                    }
                }

            } catch (IOException io) {
                io.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws IOException {
        new NClient().init();
        for (int i = 0; i < 500; i++) {
            new Thread() {
                {
                    new NClient().init();
                }
            }.start();
        }
    }
}
