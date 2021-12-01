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
        cboTest(classMap, idealClassMap);
        lcomTest(classMap, idealClassMap);
        ProgramNocTest(classMap, idealClassMap);
        ClassNocTest(classMap);
        WMCtest(classMap,idealClassMap);
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
        else System.out.println("-----Inheritance TEST PASSED-----!");
        System.out.println(ratio);
    }


    public static void cboTest(Map<String, Map<String, Integer>> classMap, Map<String, Map<String, Integer>> idealClassMap){
        
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

        if (ratio <= 0.75) System.out.println("Coupling between classes is very less. Possibility of redundancy in the code.");
        else if (ratio >= 1.25) System.out.println("Too much coupling between classes, try to make your classes more independent");
        else System.out.println("-----Coupling TEST PASSED-----!");
        System.out.println(ratio);
    }


    public static void lcomTest(Map<String, Map<String, Integer>> classMap, Map<String, Map<String, Integer>> idealClassMap){
    
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

                if (ratio >= 1.25) System.out.println("Methods in the class: '" + studentClass + "' are less cohesive./n Remove or Rewrite the unrelated methods.");
                else System.out.println("-----" + studentClass + ".class: Cohesion TEST PASSED-----!");
                System.out.println(ratio);
            }
        }
    }

    public static void ProgramNocTest(Map<String, Map<String, Integer>> classMap, Map<String, Map<String, Integer>> idealClassMap){
        int sum=0;
        for (String Class:classMap.keySet()){
            sum+=classMap.get(Class).get("NOC");
        }

        float student_ratio=(float)sum/classMap.size();
        
        sum=0;
        for (String Class:idealClassMap.keySet()){
            sum+=idealClassMap.get(Class).get("NOC");
        }

        float ideal_ratio=(float)sum/idealClassMap.size();

        float ratio=student_ratio/ideal_ratio;

        if (ratio>1.25) System.out.println("Too many inheretance, potentially vulnerable to security explotation");
        else if (ratio<0.75) System.out.println("Probably not enough inheretance, potentially lacking cohesion in the program");
        else System.out.println("Program-wise NOC test passed");
    }

    public static void ClassNocTest(Map<String, Map<String, Integer>> classMap){
        for (String Class:classMap.keySet()){
            if (classMap.get(Class).get("NOC")==0){
                System.out.println("This class is not inherited, consider composition instead");
            }
        }
    }

    public static void WMCtest(Map<String, Map<String, Integer>> classMap, Map<String, Map<String, Integer>> idealClassMap){
        int sum=0;
        for (String Class:classMap.keySet()){
            sum+=classMap.get(Class).get("WMC");
        }

        float student_ratio=(float)sum/classMap.size();
        
        sum=0;
        for (String Class:idealClassMap.keySet()){
            sum+=idealClassMap.get(Class).get("WMC");
        }

        float ideal_ratio=(float)sum/idealClassMap.size();

        float ratio=student_ratio/ideal_ratio;

        if (ratio>1.25) System.out.println("This might be subtle, but do you really need this many classes?");
        else if (ratio<0.75) System.out.println("Creating more methods in classes can make coding more enjoyable");
        else System.out.println("Right number of methods among classes");
    }

}
