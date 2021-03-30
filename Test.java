public class Test {
    public static void main(String args[]) {

        channel channel = new channel(1);
        channel.startWorkerThread();

        Thread clientThread0 = new Thread(new ClientThread("client2", channel));
        // Thread clientThread1 = new Thread(new ClientThread("client1", channel));
        // Thread clientThread2 = new Thread(new ClientThread("client3", channel));
        // Thread clientThread3 = new Thread(new ClientThread("client4", channel));
        // Thread clientThread4 = new Thread(new ClientThread("client5", channel));
        // Thread clientThread5 = new Thread(new ClientThread("client6", channel));
        // Thread clientThread6 = new Thread(new ClientThread("client7", channel));
        // Thread clientThread7 = new Thread(new ClientThread("client8", channel));
        // Thread clientThread8 = new Thread(new ClientThread("client9", channel));
        // Thread clientThread9 = new Thread(new ClientThread("client0", channel));

        // clientThread1.start();
        // clientThread2.start();
        // clientThread3.start();
        // clientThread4.start();
        // clientThread5.start();
        // clientThread6.start();
        // clientThread7.start();
        // clientThread8.start();
        // clientThread9.start();
        clientThread0.start();
    }
}
