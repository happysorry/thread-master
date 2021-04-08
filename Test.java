public class Test {
    public static void main(String args[]) {

        channel channel = new channel(1);
        channel.startWorkerThread();

        Thread clientThread0 = new Thread(new ClientThread("client2", channel));
        clientThread0.start();
    }
}
