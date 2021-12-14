import java.io.File;
import java.net.ServerSocket;

import java.net.*;
import java.io.*;

public class fileServer {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        DataInputStream din = null;
        DataOutputStream dout = null;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(2345);
            Socket s = null;
            while (true) {
                System.out.println("Waiting.....");
                try {
                    s = ss.accept();
                    System.out.println("Accepted connection : " + s);
                    // accepting file name:
                    din = new DataInputStream(s.getInputStream());
                    dout = new DataOutputStream(s.getOutputStream());
                    String filename = din.readUTF();
                    File file = new File(filename);
                    if (file.exists()) {
                        // sending the size of file to client
                        dout.writeInt((int) file.length());
                        byte[] mybytearray = new byte[((int) file.length())];
                        fis = new FileInputStream(file);
                        // bis = new BufferedInputStream(fis);
                        int count = fis.read(mybytearray, 0, mybytearray.length);
                        os = s.getOutputStream();
                        while ((int) count != 1) {
                            os.write(mybytearray, 0, mybytearray.length);
                            count = fis.read(mybytearray, 0, mybytearray.length);
                            System.out.println(count);
                        }
                        System.out.println("Sending " + file + "(" + mybytearray.length + "bytes)");
                        // os.write(mybytearray, 0, mybytearray.length);
                        os.flush();
                        System.out.println("Done.");
                    } else {
                        dout.writeInt(0);
                    }
                } finally {
                    if (bis != null)
                        bis.close();
                    if (os != null)
                        os.close();
                    if (fis != null)
                        fis.close();
                }
            }
        } finally {
            if (ss != null) {
                ss.close();
            }
        }
    }
}
