import java.lang.Runtime;
import java.io.*;
import java.util.ArrayList;

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

    // return ArrayList of for the lines of STDOUT of the process
    public static ArrayList<String> getOutLines(Process process){
        ArrayList<String> arr = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        try{
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                arr.add(line);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return arr;
    }

    public static String getFirstResult(Process process){
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        try{
            line = reader.readLine();
        } catch (Exception e){
            line = "";
        }
        return line;
    }
}
