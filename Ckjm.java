import java.lang.Runtime;
import java.io.*;
import java.util.*;

public class Ckjm {

    public static void run(String classPath, String idealClassPath){
        String command = "java " + "-jar " + "lib/ckjm-1.8.jar ";
        String get_class_files_command = "ls test/";
        System.out.println(get_class_files_command);
        Process p = Executer.exec(get_class_files_command);
        Map<String, Object> class_map = parseOutput(command, p);
        for (String key: class_map.keySet()) {
            System.out.println(key);
            System.out.println(class_map.get(key));
        }
    }

    public static  Map<String, Object> parseOutput(String command, Process process){
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        Map<String, Object> class_map = new HashMap<String, Object>();
        String[] metrics = { "WMC ", "DIT", "NOC", "CBO", "RFC", "LCOM", "Ca", "NPM" };

        try{
            while ((line = reader.readLine()) != null) {
                String curr_command = command + "test/" + line;
                Process p1 = Executer.exec(curr_command);
                String output_string = Executer.getFirstResult(p1);
                if(output_string != null){
                    String[] output_array = output_string.split(" ");
                    String key = output_array[0];
                    Map<String, Integer> values = new HashMap<String, Integer>();
                    for(int i = 1; i < output_array.length; i++){
                        values.put(metrics[i-1], Integer.parseInt(output_array[i]));
                    }
                    class_map.put(key, values);
                }
            }
            
        } catch (Exception e){
            System.out.println(e);
        }
        return class_map;
    }
}
