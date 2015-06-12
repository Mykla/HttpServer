import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by user on 12.06.2015.
 */
public class HttpServer {
    public static void main(String[] args) throws Throwable{
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
            Socket newSocket = serverSocket.accept();
            System.err.println("Client accepted.");
            new Thread(new SocketProcessor(newSocket)).start();
        }
    }

    private static class SocketProcessor implements Runnable{
        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) {
            this.s = s;
            this.is = is;
            this.os = os;
        }

        public void run(){
            try{
                readInputHeaders();
                writeResponse("<html><body><h1>Hello from Habrahabr</h1></body></html>");
            }catch (Throwable t){
                /*do nothing*/
            }finally {
                try{
                    s.close();
                }catch (Throwable t){
                    /*do nothing*/
                }
                System.err.println("Client processing finished.");
            }
        }

        private void writeResponse(String s) throws Throwable {
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String result = response + s;
            os.write(result.getBytes());
            os.flush();
        }

        private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(true) {
                String s = br.readLine();
                if(s == null || s.trim().length() == 0) {
                    break;
                }
            }
        }
    }
}
