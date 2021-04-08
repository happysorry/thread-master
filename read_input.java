import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class read_input {
    static ArrayList<Double> list = new ArrayList<Double>();

    static ArrayList<Double> read_use() {
        String filename = "profile.dat";
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader r = new BufferedReader(fr);
            String line = "";
            try {
                while ((line = r.readLine()) != null) {
                    list.add(Double.parseDouble(line));
                    // System.out.println(line);
                }
            } catch (IOException e) {

            }
        } catch (Exception e) {

        }
        return list;
    }

}
