import java.lang.Runtime;
import java.io.*;

public class Executer {
    public static Process exec(String[] command){
        Process process = null;
        try{
            process = Runtime.getRuntime().exec(command);
        }catch (Exception e){
            System.out.println(e);
        }
        return process;
    }
    public static Process exec(String command){
        Process process = null;
        try{
            process = Runtime.getRuntime().exec(command);
        }catch (Exception e){
            System.out.println(e);
        }
        return process;
    }

    public static void printResults(Process process){
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        try{
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
