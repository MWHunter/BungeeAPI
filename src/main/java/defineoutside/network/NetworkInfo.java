package defineoutside.network;

import javax.xml.crypto.Data;
import java.io.*;

public class NetworkInfo implements Serializable {
    InputStream inputStream;
    OutputStream outputStream;

    public NetworkInfo(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }
}
