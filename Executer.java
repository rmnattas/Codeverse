import java.lang.Runtime;
import java.io.*;

public class Executer {
    public static void exec(String[] command){
        try{
            Process process = Runtime.getRuntime().exec(command);
            printResults(process);  // FOR TESTING
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void exec(String command){
        try{
            Process process = Runtime.getRuntime().exec(command);
            printResults(process);  // FOR TESTING
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
