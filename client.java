import java.io.*;
import java.net.*;

public class client {
    public final static String FILE_TO_RECEIVED = "Downloaded.mp4";

    public static void main(String[] args) throws Exception {
        // int bytesRead;
        // int current = 0;
        FileOutputStream fos = null;
        FileInputStream fin = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        DataInputStream din = null;
        DataOutputStream dout = null;
        try {
            sock = new Socket("localhost", 2345);
            System.out.println("connecting....");
            // requesting file
            dout = new DataOutputStream(sock.getOutputStream());
            din = new DataInputStream(sock.getInputStream());
            dout.writeUTF("file.mp4");
            // getting file size
            int fileSize = din.readInt();
            if (fileSize > 0) {
                // recieve file
                byte[] data = new byte[fileSize];
                InputStream is = sock.getInputStream();
                int count = is.read(data, 0, data.length);
                System.out.println("Receiving video...");
                File video = new File(FILE_TO_RECEIVED);
                fos = new FileOutputStream(video);
                while (count != -1) {
                    fos.write(data, 0, count);
                    count = sock.getInputStream().read(data, 0, data.length);
                    System.out.println(getClass(count));
                }
                System.out.println("video recieved");
                // fos = new FileOutputStream(FILE_TO_RECEIVED);
                // bos = new BufferedOutputStream(fos);
                // bytesRead = is.read(data, 0, data.length);
                // current = bytesRead;
                // do {
                // bytesRead = is.read(data, current, (data.length - current));
                // if (bytesRead >= 0)
                // current += bytesRead;
                // } while (bytesRead > -1);
                // bos.write(data, 0, current);
                // bos.flush();
                // System.out.println("File " + FILE_TO_RECEIVED
                // + " downloaded (" + current + " bytes read)");
            } else {
                System.out.println("404 NOT FOUND");
            }
        } finally {
            if (fos != null)
                fos.close();
            if (bos != null)
                bos.close();
            if (sock != null)
                sock.close();
        }
    }
}
