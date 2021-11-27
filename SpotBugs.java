import java.util.ArrayList;

public class SpotBugs {

    /*
    Checks implemented (from http://findbugs.sourceforge.net/bugDescriptions.html)
    - M C UR: Uninitialized read
    - 
    */

    public static void run(String classPath){
        String[] command = {"java", "-jar", "lib/spotbugs/spotbugs.jar", "-textui", "test/"};
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
    ArrayList<String> rawMCUR = new ArrayList<String>();

    // split output
    for (String s : output){
        if (s.contains("M C UR")){
            rawMCUR.add(s);
        }
    }
    
    // pass each output to its formatter
    ArrayList<String> formattedMCUR = formatMCUR(rawMCUR);

    // merge formatted outputs
    formattedOutput.addAll(formattedMCUR);

    return formattedOutput;
    // return output;
}

public static ArrayList<String> formatMCUR(ArrayList<String> arr){
    ArrayList<String> out = new ArrayList<String>();
    String[] vars = new String[arr.size()];
    int i = 0;
    for (String s : arr){
        String[] tokens = s.split(" ");
        vars[i] = tokens[6];
        i += 1;
    }
    if (vars.length > 0)
        out.add("Variables should not be read before initialization, there is " + arr.size() + " accessed uninitialized variables: " + String.join(", ", vars) + ".");
    return out;
}
}