import java.util.LinkedList;
import java.util.Queue;

public class channel {
    private int workerNum;
    private final Queue<Request> queue = new LinkedList<Request>();

    public channel(int workerNum) {
        this.workerNum = workerNum;
    }

    public void startWorkerThread() {
        for (int i = 0; i < workerNum; i++) {
            Thread thread = new Thread(new WorkerThread(this));
            thread.start();
        }
    }

    public synchronized void put(Request request) {
        while (queue.size() >= 1e9) {
            try {
                wait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        queue.offer(request);
        notifyAll();
    }

    public synchronized Request take() {
        while (queue.peek() == null) {
            try {
                wait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        notifyAll();
        return queue.remove();
    }
}