import java.util.ArrayList;

public class WorkerThread implements Runnable {
  private channel channel;
  private String p = "http://192.168.99.141:666/~/mn-cse/mn-name/AE1/";
  public int ind = 0;
  ArrayList<String> stage = new ArrayList<String>();
  public int service_time = 0;

  public WorkerThread(channel channel) {
    this.channel = channel;
    add_stage();
  }

  public void add_stage() {
    stage.add("RFID_Container_for_stage0");
    stage.add("RFID_Container_for_stage1");
    stage.add("Liquid_Level_Container");
    stage.add("RFID_Container_for_stage2");
    stage.add("Color_Container");
    stage.add("RFID_Container_for_stage3");
    stage.add("Contrast_Data_Container");
    stage.add("RFID_Container_for_stage4");
  }

  @Override
  public void run() {
    while (true) {
      Request request = this.channel.take();
      String path = p + stage.get(ind);
      ind = request.execute(path, ind);
      // System.out.println(ind);
      if (ind == stage.size())
        ind = 0;
      else
        channel.put(request);

      try {
        Thread.sleep(service_time);
      } catch (InterruptedException e) {
        System.out.println(e);
      }
    }
  }
}