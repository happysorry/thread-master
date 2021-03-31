import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class send_request implements Runnable {

    public static void stage1(String path) {
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
                        + "\", \"cnf\": \"application/xml\",\"lbl\":\"req\",\"rn\":\"" + val + "\"}}";
                // '{"m2m:cin": {"con": "EXAMPLE_VALUE", "cnf": "text/plain:0"}}'
                out.write(request.toString().getBytes("UTF-8"));
                out.flush();
                out.close();
                int satus = http.getResponseCode();
                System.out.println(satus);
                // System.out.println(mncse);
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

    public static void stage2(String path) {
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
                        + "\", \"cnf\": \"application/xml\",\"lbl\":\"req\",\"rn\":\"" + val + "\"}}";
                // '{"m2m:cin": {"con": "EXAMPLE_VALUE", "cnf": "text/plain:0"}}'
                out.write(request.toString().getBytes("UTF-8"));
                out.flush();
                out.close();
                int satus = http.getResponseCode();
                System.out.println(satus);
                // System.out.println(mncse);
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

    @Override
    public void run() {
        // TODO Auto-generated method stub

        send_request s = new send_request();
        s.stage1("http://192.168.99.141:666/~/mn-cse/mn-name/AE1/Liquid_Level_Container");
    }

    public static void main(String[] args) {
        send_request s = new send_request();
        s.stage1("http://192.168.99.141:666/~/mn-cse/mn-name/AE1/Liquid_Level_Container");
    }

}
