import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Request {
    private String RFID;
    private String path;
    ArrayList<String> stage = new ArrayList<String>();// store every stages

    public Request(String RFID) {
        this.RFID = RFID;
    }

    public static void stage1(String RFID, String path) {
        try {
            int val = (int) ((Math.random() * 89999) + 10000);
            String con = "";
            if ((val % 2) == 1)
                con = "false";
            else
                con = "true";
            URL url = new URL(path);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);
            // http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("X-M2M-Origin", "admin:admin");
            http.setRequestProperty("Content-Type", "application/json;ty=4");
            try {
                http.setRequestMethod("POST");
                http.connect();
                DataOutputStream out = new DataOutputStream(http.getOutputStream());

                String request = "{\"m2m:cin\": {\"con\": \"" + con
                        + "\", \"cnf\": \"application/xml\",\"lbl\":\"req\",\"rn\":\"" + RFID + "\"}}";
                // '{"m2m:cin": {"con": "EXAMPLE_VALUE", "cnf": "text/plain:0"}}'
                out.write(request.toString().getBytes("UTF-8"));
                out.flush();
                out.close();
                int satus = http.getResponseCode();
                // System.out.println(satus);
                // System.out.println(path);
                // System.out.println(status);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {

        }
    }

    void print_single_time(double elapsed) {
        try {
            String filename = "single_time.txt";
            FileWriter fw1 = new FileWriter(filename, true);
            fw1.write(elapsed + "\n");
            fw1.flush();
            fw1.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int execute(String p, int ind) {
        double elapsed = 0.0;
        double startTime = System.nanoTime();
        stage1(RFID, p);
        elapsed = System.nanoTime() - startTime;
        elapsed /= 1e6;
        // System.out.println(elapsed);
        print_single_time(elapsed);
        ind++;
        return ind;
    }
}