import java.io.File;

public class Connection implements AutoCloseable {

    public void sendData() throws Exception {
        throw new Exception("send data");
    }
    @Override
    public void close() throws Exception {
        throw new Exception("close");
    }

}


