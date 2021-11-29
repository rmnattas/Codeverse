import java.lang.Runtime;
import java.io.*;
import java.util.*;

public class Ckjm {

    public static void run(String classPath, String idealClassPath){
        String command = "java " + "-jar " + "lib/ckjm-1.8.jar ";
        String get_class_files_command = "ls " + "test/";
        System.out.println(get_class_files_command);

        Process p = Executer.exec(get_class_files_command);
        Map<String, Map<String, Integer>> classMap = parseOutput(command, p);

        Process p1 = Executer.exec(get_class_files_command);
        Map<String, Map<String, Integer>> idealClassMap = parseOutput(command, p1);

        for (String key: classMap.keySet()) {
            System.out.println(key);
            System.out.println(classMap.get(key));
        }

        ditTest(classMap, idealClassMap);
    }

    public static  Map<String, Map<String, Integer>> parseOutput(String command, Process process){
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        Map<String, Map<String, Integer>> class_map = new HashMap<String, Map<String, Integer>>();
        String[] metrics = { "WMC", "DIT", "NOC", "CBO", "RFC", "LCOM", "Ca", "NPM" };

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

    public static void ditTest(Map<String, Map<String, Integer>> classMap, Map<String, Map<String, Integer>> idealClassMap){
        
        float sumStudent = 0;
        float sumInstructor = 0;
        float ratio = 1;

        for (String studentClass: classMap.keySet()) {
            sumStudent += classMap.get(studentClass).get("DIT");
        }

        for (String instructorClass: idealClassMap.keySet()) {
            sumInstructor += idealClassMap.get(instructorClass).get("DIT");
        }

        if(sumInstructor == 0){
            if(sumStudent != 0){
                ratio = 2;
            }
        }
        else{
            ratio = sumStudent/sumInstructor;
        }

        if (ratio == 0) System.out.println("Try incorporate some inheratnce in your code");
        else if (ratio > 1) System.out.println("Use Composition over inheritance");
        else System.out.println("-----DIT TEST PASSED-----!");
    }
}
