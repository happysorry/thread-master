import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class start {
    static ArrayList<String> id = new ArrayList(); // store all replicas' container id
    static ArrayList<String> machine_id = new ArrayList<String>(); // docker machine id

    public static void start_ql(String con_name) {

        // ql ql = new ql(con_name);//ql
        ql2 ql = new ql2(con_name);// ql2
        ql.init_5();
        ql.init_state();
        for (int j = 0; j < 160; j++) {
            System.out.println("iteration " + j);
            ql.print_state_action("iteration " + j);
            ql.learn();
        }
        ql.print();
    }

    public static void start_ql3(String con_name) {
        ql3 ql = new ql3(con_name);
        ql.get_machine_id();
        // for (int i = 0; i < 10; i++) {
        // ql.get_state2();
        // }
        ql.init_5();
        ql.init_state();
        for (int j = 0; j < 1080; j++) {
            System.out.println("iteration " + j);
            ql.print_state_action("iteration " + j);
            ql.learn();
        }
        ql.print();
        ql.print_tmax_vio();
    }

    public static void service_discover() {

        int i = 0;
        /**
         * // * get cpu utilization,replicas
         */
        String cmd = "docker service ls";
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmd);
        builder.redirectErrorStream(true);
        try {
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            String result = "";

            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }

                if (line.indexOf("happysorry") < 0) {
                    continue;
                }
                // System.out.println(line);
                String[] sp = line.split(" ");
                String ii = sp[8];
                // System.out.println(ii);

                id.add(ii);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static class example implements Runnable {

        String con_name;

        public example(String con_name) {
            this.con_name = con_name;
        }

        @Override
        public void run() {
            System.out.println(con_name);
            start_ql3(con_name);// start qlearning
        }
    }

    public static void main(String[] args) {
        service_discover();
        ExecutorService ex = Executors.newFixedThreadPool(10);
        Runnable r = new get_use();
        ex.execute(r);
        try {
            Thread.sleep(60000);
        } catch (Exception e) {

        }
        Runnable r2 = new globe();
        ex.execute(r2);
        for (int i = 0; i < id.size(); i++) {
            String con_name = id.get(i);
            // mkdir(con_name);
            try {
                Runnable tmp = new example(con_name);
                ex.execute(tmp);
            } catch (Throwable e) {
                System.out.println(e);
            }

            // ex.execute(tmp);
        }

        // ex.shutdown(); // shotdown ExecutorService Thread pool
        // System.exit(0);
    }

}
