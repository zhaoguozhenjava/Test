import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AIOTest {

    public void testServer() throws IOException, InterruptedException {
        new AServer(7788);

        Thread.sleep(10000);
    }

    public void testClient() throws IOException, InterruptedException,
            ExecutionException {
        AClient client = new AClient("localhost", 7788);
        client.write((byte) 11);
    }

    public static void main(String[] args) {
        AIOTest test = new AIOTest();
        try {
            test.testServer();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            test.testClient();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}