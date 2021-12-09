import java.lang.Runtime;
import java.io.*;
import java.util.*;

public class Ckjm {

    static int ctr = 1;

    public static void run(String classPath, String idealClassPath){
        String command = "java " + "-jar " + "lib/ckjm-1.9.jar ";
        String getClassFiles1 = "ls " + classPath;
        String getClassFiles2 = "ls " + idealClassPath;

        Process p = Executer.exec(getClassFiles1);
        Executer.printResults(p);
        Map<String, Map<String, Integer>> classMap = parseOutput(command + classPath, p);

        Process p1 = Executer.exec(getClassFiles2);
        Map<String, Map<String, Integer>> idealClassMap = parseOutput(command + idealClassPath, p1);


        inheritanceTest(classMap, idealClassMap);
        couplingTest(classMap, idealClassMap);
        cohesionTest(classMap, idealClassMap);
        publicMethodsTest(classMap);
    }

    public static  Map<String, Map<String, Integer>> parseOutput(String command, Process process){
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        Map<String, Map<String, Integer>> class_map = new HashMap<String, Map<String, Integer>>();
        String[] metrics = { "WMC", "DIT", "NOC", "CBO", "RFC", "LCOM", "Ca", "NPM" };

        try{
            while ((line = reader.readLine()) != null) {
                String curr_command = command + line;
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

    public static void inheritanceTest(Map<String, Map<String, Integer>> classMap, Map<String, Map<String, Integer>> idealClassMap){
        
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

        if (ratio == 0) {
            System.out.println("    " + ctr + ". Depth of Inheritance is low, try to incorporate some Inheritnce in your code\n");
            ctr += 1;
        }
        else if (ratio > 1) {
            System.out.println("    " + ctr + ". Depth of inheritance is too large, try to Use Composition over Inheritance for some classes\n");
            ctr += 1;
        }
    }


    public static void couplingTest(Map<String, Map<String, Integer>> classMap, Map<String, Map<String, Integer>> idealClassMap){
        
        float sumStudent = 0;
        float sumInstructor = 0;
        float ratio = 1;

        for (String studentClass: classMap.keySet()) {
            sumStudent += classMap.get(studentClass).get("CBO");
        }

        for (String instructorClass: idealClassMap.keySet()) {
            sumInstructor += idealClassMap.get(instructorClass).get("CBO");
        }

        if(sumInstructor == 0){
            if(sumStudent == 0){
                ratio = 1;
            }
            else{
                ratio = 10;
            }
        }

        else{ 
            ratio = (sumStudent*idealClassMap.size())/(sumInstructor*classMap.size());
        }

        if (ratio <= 0.75) {
            System.out.println("    " + ctr + ". Coupling between classes is very less. Possibility of redundancy in the code.\n");
            ctr += 1;
        }
        else if (ratio >= 1.25) {
            System.out.println("    " + ctr + ". Too much coupling between classes, try to make your classes more independent\n");
            ctr += 1;
        }
    }


    public static void cohesionTest(Map<String, Map<String, Integer>> classMap, Map<String, Map<String, Integer>> idealClassMap){
    
        for (String studentClass: classMap.keySet()) {
            float ratio, studentAvgLcom, instructorAvgLcom;

            if(idealClassMap.containsKey(studentClass)){
                studentAvgLcom = (float)classMap.get(studentClass).get("LCOM")/(float)classMap.get(studentClass).get("WMC");
                instructorAvgLcom = (float)idealClassMap.get(studentClass).get("LCOM")/(float)idealClassMap.get(studentClass).get("WMC");
                
                if(instructorAvgLcom == 0.0){
                    ratio = 1;
                }
                else{
                    ratio = studentAvgLcom/instructorAvgLcom;        
                }

                if (ratio >= 1.25) {
                    System.out.println("    " + ctr + ". " + studentClass + ".class: Methods in the class are less cohesive.\n\t  Remove or Rewrite the unrelated methods.\n");
                    ctr += 1;
                }
            }
        }
    }


    public static void publicMethodsTest(Map<String, Map<String, Integer>> classMap){
        for (String Class:classMap.keySet()){
            if(classMap.get(Class).get("WMC") <= 1) {
                System.out.println("    " + ctr + ". " + Class + ".class: No pubic methods found! Methods of this class can't be accessed anywhere.\n\t   kindly add public methods or delete the class\n");
                ctr += 1;
            }
        }
    }

}
