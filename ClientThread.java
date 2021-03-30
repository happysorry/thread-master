import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class ClientThread implements Runnable {
    private String clientName;
    private channel channel;
    private double send_time = 1000;
    ArrayList<Double> freq = new ArrayList<Double>();
    private int inteval = 300000;
    private double startTime = 0.0;

    public ClientThread(String clientName, channel channel) {
        this.clientName = clientName;
        this.channel = channel;
        add_freq();
    }

    public String rand_RFID() {
        int val = (int) ((Math.random() * 899999) + 100000);
        String RFID = String.valueOf(val);
        return RFID;
    }

    /**
     * read lambda
     */
    public void add_freq() {
        read_input r = new read_input();
        freq = r.read_use();
    }

    /**
     * calculate 1/lambda
     */
    public long cal_send_time() {
        double elapsed = System.nanoTime() - startTime;
        elapsed /= 1e9;
        int ind = (int) elapsed;
        double lambda = freq.get(ind);
        double send_time = Math.log(1 - new Random().nextDouble()) / (-lambda);
        send_time *= 1e9;// change to nanosecond
        long s = (long) send_time;
        // System.out.println(s);
        return s;

    }

    @Override
    public void run() {
        double elapsed = 0.0;
        startTime = System.nanoTime();
        while (true) {
            String RFID = rand_RFID();
            long send_time = cal_send_time();
            Request request = new Request(RFID);
            this.channel.put(request);
            /**
             * using busy waiting to control
             */
            long end = System.nanoTime() + send_time;
            while (System.nanoTime() < end) {
            }
        }
    }
}