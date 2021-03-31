import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {

    public static void main(String[] args) {
        ArrayList<String> id = new ArrayList<String>();
        ArrayList<String> con_id = new ArrayList<String>();
        String cmd = "docker-machine ls";
        Runtime run = Runtime.getRuntime();
        Process pr;
        // System.out.println(cmd);
        try {
            pr = run.exec(cmd);
            BufferedReader r = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            String result = "";

            while (true) {
                line = r.readLine();
                // System.out.println(line);
                if (line == null) {
                    break;
                }
                if (line.indexOf("tcp") < 0) {
                    continue;
                }
                String[] sp = line.split(" ");
                id.add(sp[0]);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        for (int i = 0; i < id.size(); i++) {
            cmd = "docker-machine ssh " + id.get(i) + " docker ps";
            // System.out.println(cmd);
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
                    String[] sp = line.split(" ");
                    System.out.println(sp[0]);
                    con_id.add(sp[0]);
                }
            } catch (IOException e) {

            }
        }
    }
}