import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class get_use implements Runnable {
    static void get_use() {
        String cmd = "sudo docker-machine ssh default docker stats --no-stream";
        Process pr;
        Runtime run = Runtime.getRuntime();
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
                if (line.indexOf("mn1") < 0) {
                    continue;
                }
                // System.out.println(line);
                // System.out.println("ii" + line);
                String[] sp = line.split("%");
                String[] sp2 = sp[0].split(" ");
                String ii = sp2[sp2.length - 1];
                System.out.println(ii);
                print_use(ii);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static void print_use(String avg) {
        try {
            String filename = "use10.txt";
            FileWriter fw1 = new FileWriter(filename, true);
            fw1.write(avg + "\n");
            fw1.flush();
            fw1.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static void print_start() {
        try {
            String filename = "use10.txt";
            FileWriter fw1 = new FileWriter(filename, true);
            fw1.write("------------------------------------------------------" + "\n");
            fw1.flush();
            fw1.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        double starttime = System.nanoTime();
        while (true) {
            get_use();
            try {
                Thread.sleep(1000);
                double endtime = (System.nanoTime() - starttime) / 1e9;
                if (endtime > 180)
                    break;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        print_start();
        // TODO Auto-generated method stub
        double starttime = System.nanoTime();
        while (true) {
            get_use();
            try {
                Thread.sleep(1000);
                double endtime = (System.nanoTime() - starttime) / 1e9;
                if (endtime > 180)
                    break;

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return;
    }

}
