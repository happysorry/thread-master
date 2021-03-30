import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class mu_test {

    /**
     * calculate 1/lambda
     */
    public static long cal_send_time(double lambda) {

        double send_time = Math.log(1 - new Random().nextDouble()) / (-lambda);
        send_time *= 1e9;// change to nanosecond
        long s = (long) send_time;
        // System.out.println(s);
        return s;

    }

    public static void stage1() {
        try {
            int val = (int) ((Math.random() * 8999999) + 1);
            String con = "";
            if ((val % 2) == 1)
                con = "false";
            else
                con = "true";
            String path = "http://192.168.99.141:666/~/mn-cse/mn-name/AE1/RFID_Container_for_stage0";
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
                        + "\", \"cnf\": \"application/xml\",\"lbl\":\"req\",\"rn\":\"" + val + "\"}}";
                // '{"m2m:cin": {"con": "EXAMPLE_VALUE", "cnf": "text/plain:0"}}'
                out.write(request.toString().getBytes("UTF-8"));
                out.flush();
                out.close();
                int satus = http.getResponseCode();
                System.out.println(satus);
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

    static void print_single_time(double elapsed, String filename) {
        try {
            elapsed /= 1e6; // convert nanosecond into microsecond
            filename = filename + ".txt";
            FileWriter fw1 = new FileWriter(filename, true);
            fw1.write(elapsed + "\n");
            fw1.flush();
            fw1.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static void send_req(double lambda, String filename, double simtime) {
        long send_time = cal_send_time(lambda);
        double starttime = System.nanoTime();
        double endtime = System.nanoTime();
        while (true) {
            double tmp = System.nanoTime();
            stage1();
            double elapse = System.nanoTime() - tmp;
            print_single_time(elapse, filename);
            long end = System.nanoTime() + send_time;
            while (System.nanoTime() < end) {
            }
            endtime = System.nanoTime();
            tmp = (endtime - starttime) / 1e9;
            if (tmp > simtime)
                break;
        }
    }

    void get_use() {
        String cmd = "docker stats --no-stream";
        Process pr;
        Runtime run = Runtime.getRuntime();
        try {
            pr = run.exec(cmd);
            BufferedReader r = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            String result = "";

            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                if (line.indexOf("app_mn1") < 0) {
                    continue;
                }
                String[] sp = line.split("%");
                String[] sp2 = sp[0].split(" ");
                String ii = sp2[sp2.length - 1];
                // System.out.println("ii" + ii);
                print_use(ii);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void print_use(String avg) {
        try {
            String filename = "use.txt";
            FileWriter fw1 = new FileWriter(filename, true);
            fw1.write(avg + "\n");
            fw1.flush();
            fw1.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        send_req(50.0, "50s", 60);
    }
}
