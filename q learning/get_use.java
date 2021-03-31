import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class get_use implements Runnable {
    /**
     * get docker machine id
     */
    static ArrayList<String> machine_id = new ArrayList<String>(); // docker machine id
    static ArrayList<String> con_id = new ArrayList<String>();// service id

    void get_machine_id() {
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
                machine_id.add(sp[0]);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void get_use() {
        for (int i = 0; i < con_id.size(); i++) {
            ArrayList<String> cpu = new ArrayList(); // store all replicas' container id
            int replicas = 0; // replicas of target container
            double use = 0.0;// calculate average cpu utilization
            // System.out.println(con_id.get(i));
            for (int j = 0; j < machine_id.size(); j++) {
                // System.out.println(machine_id.get(j));
                String cmd = "docker-machine ssh " + machine_id.get(j) + " docker stats --no-stream";
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
                        if (line == null) {
                            break;
                        }
                        if (line.indexOf(con_id.get(i)) < 0) {
                            continue;
                        }
                        String[] sp = line.split("%");
                        String[] sp2 = sp[0].split(" ");
                        String ii = sp2[sp2.length - 1];
                        // System.out.println("ii" + ii);
                        cpu.add(ii);
                        replicas++;
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            double avg = 0.0;
            for (int j = 0; j < cpu.size(); j++) {
                double tmp = Double.parseDouble(cpu.get(j));
                // System.out.println(tmp);
                avg += tmp;
            }
            avg /= replicas;
            print_use(avg, con_id.get(i));
            System.out.println(avg);
        }

    }

    void print_use(double avg, String con_name) {
        try {
            String filename = con_name + "/" + con_name + "_stats_use.txt";
            FileWriter fw1 = new FileWriter(filename, true);
            fw1.write(avg + "\n");
            fw1.flush();
            fw1.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void add_service() {
        con_id.add("app_mn1");
        con_id.add("app_mn2");
        con_id.add("app_mnae1");
        con_id.add("app_mnae2");
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        get_machine_id();
        add_service();
        for (int i = 0; i < 1080; i++) {
            get_use();
        }

    }
}
