import java.util.ArrayList;

public class Checkstyle {

    /*
    Checks implemented (from https://checkstyle.org/checks.html)
    - OneTopLevelClass
    - 
    */


    final static String GOOGLE_STANDARD_PATH = "lib/google_checks.xml";
    final static String SUN_STANDARD_PATH = "lib/sun_checks.xml";

    // gets the .java files
    public static void run(String codePath){
        run(codePath, GOOGLE_STANDARD_PATH);
    }

    public static void run(String codePath, String standardPath){
        String[] command = {"java", "-jar", "lib/checkstyle-9.1-all.jar", "-c", standardPath, codePath};
        Process p = Executer.exec(command);
        ArrayList<String> rawOutput = Executer.getOutLines(p);
        ArrayList<String> formattedOutput = formatAll(rawOutput);
        for (String s : formattedOutput){
            System.out.println(s);
        }
    }

    // can be improved with 2D array
    public static ArrayList<String> formatAll(ArrayList<String> output){
        // init ArrayList
        ArrayList<String> formattedOutput = new ArrayList<String>();
        ArrayList<String> rawOneTopLevelClass = new ArrayList<String>();

        // split output
        for (String s : output){
            if (s.contains("[OneTopLevelClass]")){
                rawOneTopLevelClass.add(s);
            }
        }
        
        // pass each output to its formatter
        ArrayList<String> formattedOneTopLevelClass = formatOneTopLevelClass(rawOneTopLevelClass);

        // merge formatted outputs
        formattedOutput.addAll(formattedOneTopLevelClass);

        return formattedOutput;
        // return output;
    }

    public static ArrayList<String> formatOneTopLevelClass(ArrayList<String> arr){
        ArrayList<String> out = new ArrayList<String>();
        String[] classes = new String[arr.size()];
        int i = 0;
        for (String s : arr){
            String[] tokens = s.split(" ");
            classes[i] = tokens[tokens.length-10];
            i += 1;
        }
        if (classes.length > 0)
            out.add("Each top-level public class should be in its separate file, you have " + arr.size() + " classes that should be in a separate file: " + String.join(", ", classes) + ".");
        return out;
    }
}
