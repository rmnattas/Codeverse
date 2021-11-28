import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SpotBugs {

    /*
    Checks implemented (from http://findbugs.sourceforge.net/bugDescriptions.html)
    - M C UR: Uninitialized read
    - M P UuF: Unused field
    - 
    */

    final static String[] CHECKS = {"M C UR", "M P UuF"};


    public static void run(String classPath){
        String[] command = {"java", "-jar", "lib/spotbugs/spotbugs.jar", "-textui", "test/"};
        Process p = Executer.exec(command);
        ArrayList<String> rawOutput = Executer.getOutLines(p);
        ArrayList<String> formattedOutput = formatAll(rawOutput);
        for (String s : formattedOutput){
            System.out.println(s);
        }
    }

    public static ArrayList<String> formatAll(ArrayList<String> output){
        // init
        HashMap<String,HashSet<String>> outputMap = new HashMap<String,HashSet<String>>();
        ArrayList<String> formattedOutput = new ArrayList<String>();
        for (int i=0; i<CHECKS.length; i++)
            outputMap.put(CHECKS[i], new HashSet<String>()); 

        // split output to its checks sets
        for (String s : output){
            for (int i=0; i<CHECKS.length; i++){
                if (s.contains(CHECKS[i])){
                    outputMap.get(CHECKS[i]).add(s);
                    break;
                }
            }
        }

        // pass each check set to its formatter
        formattedOutput.addAll(formatMCUR(outputMap.get("M C UR")));
        formattedOutput.addAll(formatMPUuF(outputMap.get("M P UuF")));

        return formattedOutput;
        // return output;
    }

    public static ArrayList<String> formatMCUR(HashSet<String> arr){
        ArrayList<String> out = new ArrayList<String>();

        if (arr.size() > 0)
            out.add("Variables should not be read before initialization, there is " + arr.size() + " accesses to uninitialized variables:");

        for (String s : arr){
            String[] tokens = s.split(" ");
            out.add(" - " + tokens[tokens.length-2] + tokens[tokens.length-1] + " \'" + tokens[6] + "\' in " + tokens[9]);
        }

        return out;
    }

    public static ArrayList<String> formatMPUuF(HashSet<String> arr){
        ArrayList<String> out = new ArrayList<String>();

        if (arr.size() > 0)
            out.add("There is " + arr.size() + " fields that are never used, consider removing them from the code:");

        for (String s : arr){
            String[] tokens = s.split(" ");
            out.add(" - " + tokens[5] + " in " + tokens[tokens.length-1]);
        }

        return out;
    }
}