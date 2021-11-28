import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Checkstyle {

    /*
    Checks implemented (from https://checkstyle.org/checks.html)
    - OneTopLevelClass
    - TodoComment // Standard only check for "TODO:" and "FIXME:" exactly
    - ConstantName
    - 
    // Implementing more checks may require dublicate check, if found on multiple standard checks
    */


    // final static String GOOGLE_STANDARD_PATH = "lib/google_checks.xml";
    // final static String SUN_STANDARD_PATH = "lib/sun_checks.xml";
    // final static String CHECKSTYLE_STANDARD_PATH = "lib/checkstyle_checks.xml";
    final static String[] STANDARDS_PATHS = {"lib/checkstyle_checks.xml", "lib/sun_checks.xml", "lib/google_checks.xml"};
    final static String[] CHECKS = {"OneTopLevelClass", "TodoComment", "ConstantName"};

    public static void run(String codePath){
        String[] command = {"java", "-jar", "lib/checkstyle-9.1-all.jar", "-c", "STANDARD_PATH", codePath};
        ArrayList<String> rawOutput = new ArrayList<String>();
        Process p;

        // Run all standards configs
        for (int i=0; i<STANDARDS_PATHS.length; i++){
            command[4] = STANDARDS_PATHS[i];
            p = Executer.exec(command);
            rawOutput.addAll(Executer.getOutLines(p));
        }

        // format and print output
        ArrayList<String> formattedOutput = formatAll(rawOutput);
        for (String s : formattedOutput)
            System.out.println(s);
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
        formattedOutput.addAll(formatOneTopLevelClass(outputMap.get("OneTopLevelClass")));
        formattedOutput.addAll(formatTodoComment(outputMap.get("TodoComment")));
        formattedOutput.addAll(formatConstantName(outputMap.get("ConstantName")));

        return formattedOutput;
        // return output;
    }



    public static ArrayList<String> formatOneTopLevelClass(HashSet<String> arr){
        ArrayList<String> out = new ArrayList<String>();
        String[] classes = new String[arr.size()];

        int i = 0;
        for (String s : arr){
            String[] tokens = s.split(" ");
            classes[i] = tokens[tokens.length-10];
            i += 1;
        }

        if (arr.size() > 0)
            out.add("Each top-level public class should be in its separate file, you have " + arr.size() + " classes that should be in a separate file: " + String.join(", ", classes) + ".");
        return out;
    }

    public static ArrayList<String> formatTodoComment(HashSet<String> arr){
        ArrayList<String> out = new ArrayList<String>();

        if (arr.size() > 0)
            out.add("You have " + arr.size() + " \"TODO:\" or \"FIXME:\" comments:");

        for (String s : arr){
            String[] tokens = s.split(":");
            out.add(" - " + tokens[0].split(" ")[1] + ":" + tokens[1]);
        }
        return out;
    }

    public static ArrayList<String> formatConstantName(HashSet<String> arr){
        ArrayList<String> out = new ArrayList<String>();
        
        if (arr.size() > 0)
            out.add("Constants in Java should have an all capital name, you have the " + arr.size() + " constants that do not follow that standard: ");

        for (String s : arr){
            String[] tokens = s.split(":");
            out.add(" - " + tokens[0].split(" ")[1] + ":" + tokens[1] + " " + s.split(" ")[3]);
        }
        return out;
    }
}
