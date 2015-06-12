import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;

/**
 * В файле хранится список веб-серверов. Надо
 * проверить какие из серверов доступны в данный
 * момент и создать отчет в формате сервер = статус.
 * Created by user on 12.06.2015.
 */
public class CheckHttp {
    private static void CheckByIsReachable(String line) throws Exception{
        try {
            if(InetAddress.getByName(line).isReachable(2000)){
                System.out.printf("%s = сервер доступен", line);
                System.out.println();
            }else {
                System.out.printf("%s = сервер не доступен", line);
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void CheckByIp(String line) throws Exception{
        Process p1 = java.lang.Runtime.getRuntime().exec("ping " + line);
        int returnVal = p1.waitFor();
        boolean reachable = (returnVal==0);

        if(reachable){
            System.out.printf("%s = сервер по ping доступен", line);
            System.out.println();
        }else {
            System.out.printf("%s = сервер по ping не доступен", line);
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader("F:\\Николай\\Java\\HttpServer\\src\\ServerList.dat"));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                CheckByIsReachable(line);
                CheckByIp(line);
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }
}
