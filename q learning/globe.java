import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * create a linkedlist to simulate a path of services
 */
public class globe implements Runnable {
    static ArrayList<String> machine_id = new ArrayList<String>(); // docker machine id
    static ArrayList<String> con_id = new ArrayList<String>();// service id
    static LinkedList<cons> list = new LinkedList<cons>();
    static double phi = 0.1;
    static double Tmax = 50.0;
    static cons mn1 = new cons();
    static cons mn2 = new cons();
    static cons mnae1 = new cons();
    static cons mnae2 = new cons();

    static void add_service() {

        list.add(mn1);
        list.add(mnae1);
        list.add(mnae2);
        list.add(mn2);
        mn1.con_name = "app_mn1";
        mn2.con_name = "app_mn2";
        mnae1.con_name = "app_mnae1";
        mnae2.con_name = "app_mnae2";
        /**
         * add request percentage
         */
        mn1.p_pi = 1.5;
        mn2.p_pi = 0.5;
        mnae1.p_pi = 1.0;
        mnae2.p_pi = 0.5;
        /**
         * initial vm
         */
        mn1.vm = 0.25;
        mn2.vm = 0.25;
        mnae1.vm = 0.25;
        mnae2.vm = 0.25;

    }

    /**
     * calculate request percentage
     */
    static void cal_pi() {
        double tmp = 0.0;
        for (int i = 0; i < list.size(); i++) {
            tmp += list.get(i).p_pi;
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).p_pi /= tmp;
        }
    }

    /**
     * read cpu use
     */
    static void read_use() {
        int i;
        ArrayList<cons> con = new ArrayList<cons>();
        for (i = 0; i < list.size(); i++) {
            String con_name = list.get(i).con_name;
            String filename = con_name + "/" + con_name + "_use.txt";
            // System.out.println(con_name);
            try {
                FileReader fr = new FileReader(filename);
                BufferedReader r = new BufferedReader(fr);
                String line = "";
                try {
                    while ((line = r.readLine()) != null) {
                        list.get(i).use = Double.parseDouble(line);
                    }
                } catch (IOException e) {

                }
            } catch (IOException e) {

            }

        }
    }

    static void read_res_time() {
        int i;
        ArrayList<cons> con = new ArrayList<cons>();
        for (i = 0; i < list.size(); i++) {
            String con_name = list.get(i).con_name;
            String filename = con_name + "/" + con_name + "_response_time.txt";
            // System.out.println(filename);
            try {
                FileReader fr = new FileReader(filename);
                BufferedReader r = new BufferedReader(fr);
                String line = "";
                try {
                    while ((line = r.readLine()) != null) {
                        list.get(i).res_time = Double.parseDouble(line);
                    }
                } catch (IOException e) {

                }
            } catch (IOException e) {

            }

        }
    }

    /**
     * get docker machine id
     */
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

    /**
     * calculate average contribution of microservice m to overall application cpu
     * use
     */
    static void cal_vm_use() {
        double t_pi_m = 0.0;
        double p_sum = 0.0;
        for (int i = 0; i < list.size(); i++) {
            p_sum += list.get(i).p_pi;
        }
        for (int i = 0; i < list.size(); i++) {
            double tmp = 0.0;
            tmp = list.get(i).p_pi * list.get(i).use;
            t_pi_m += tmp;
        }

        t_pi_m /= p_sum;

        for (int i = 0; i < list.size(); i++) {
            double vmm = 0.0;
            double tmp = 0.0;
            tmp = phi * list.get(i).use;
            tmp /= t_pi_m;
            vmm = (1 - phi) * list.get(i).vm;
            vmm += tmp;
            // System.out.println(tmp);
            list.get(i).vm = vmm;
        }
    }

    /**
     * calculate average contribution of microservice m to overall application cpu
     * use
     */
    static void cal_vm_res() {
        double t_pi_m = 0.0;
        double p_sum = 0.0;
        for (int i = 0; i < list.size(); i++) {
            p_sum += list.get(i).p_pi;
        }
        for (int i = 0; i < list.size(); i++) {
            double tmp = 0.0;
            tmp = list.get(i).p_pi * list.get(i).res_time;
            t_pi_m += tmp;
            // System.out.println(tmp);
        }

        t_pi_m /= p_sum;

        for (int i = 0; i < list.size(); i++) {
            double vmm = 0.0;
            double tmp = 0.0;
            tmp = phi * list.get(i).res_time;
            tmp /= t_pi_m;
            vmm = (1 - phi) * list.get(i).vm;
            vmm += tmp;
            list.get(i).vm = vmm;
            list.get(i).tmax = vmm * Tmax; // calculate new tmax.Tmax always be 50ms
            // System.out.println(tmp);
            System.out.println(list.get(i).con_name);
            System.out.println(list.get(i).vm);
        }
    }

    void prnt_cpus() {

    }

    static void print_Tmax() {

        int i;
        for (i = 0; i < list.size(); i++) {
            String con_name = list.get(i).con_name;
            double tmax = list.get(i).tmax;
            try {
                String filename = con_name + "/" + con_name + "_t_max.txt";
                FileWriter fw1 = new FileWriter(filename, true);
                fw1.write(tmax + "\n");
                fw1.flush();
                fw1.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    static void init_res_time() {

        int i;
        for (i = 0; i < list.size(); i++) {
            String con_name = list.get(i).con_name;
            double tmax = list.get(i).tmax;
            try {
                String filename = con_name + "/" + con_name + "_response_time.txt";
                FileWriter fw1 = new FileWriter(filename, true);
                fw1.write(Tmax + "\n");
                fw1.flush();
                fw1.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    // @Override
    // public void run() {
    // // TODO Auto-generated method stub
    // add_service();
    // for (int i = 0; i < 10; i++) {
    // read_use();
    // cal_vm_use();
    // try {
    // Thread.sleep(60000);
    // } catch (InterruptedException e) {
    // System.out.println(e);
    // }
    // }

    // }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        add_service();
        init_res_time();
        for (int i = 0; i < 1620; i++) {
            read_res_time();
            cal_vm_res();
            print_Tmax();
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

    }

    public static void main(String[] args) {
        add_service();
        init_res_time();
        for (int i = 0; i < 10; i++) {
            read_res_time();
            cal_vm_res();
            print_Tmax();
        }

    }

}

class cons {
    String con_name;
    double use;
    double res_time;
    double p_pi; // request percentage
    double vm; // vm as average contribution of microservice
    double tmax;
}
