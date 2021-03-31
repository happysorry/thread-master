import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class avg {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader(
                    "use.txt");
            BufferedReader br = new BufferedReader(fr);
            double ans = 0.0;
            int con = 0;
            try {
                while (br.ready()) {
                    // int tmp = Integer.parseInt(br.readLine());
                    double tmp = Double.parseDouble(br.readLine());
                    ans += tmp;
                    con++;

                }
                ans /= con;
                System.out.println(ans);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
