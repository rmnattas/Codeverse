import java.nio.file.Paths;


public class Codeverse {

    final static String TEMP_PATH = "/tmp/Codeverse/";
    public static void main(String[] args){
        String sourcePath, classPath, idealSourcePath, idealClassPath;
        if (args.length < 1){
            printHelp();
            return;
        }

        int separatorArg = strIndexOf(args,"-s");
        Boolean runCmp = false;
        if (separatorArg != -1){
            runCmp = true;
        }

        // clean Codeverse tmp
        Executer.exec(new String[]{"rm", "-rf", TEMP_PATH});

        // compile the java source cocde
        Executer.exec(new String[]{"javac", "-d", TEMP_PATH+"source/" , args[0]});  // .java -> .class
        sourcePath = args[0];
        classPath = TEMP_PATH+"source/";
        
        if (runCmp){
            // Compile ideal solution
            Executer.exec(new String[]{"javac", "-d", TEMP_PATH+"solution/" ,args[1]});  // .java -> .class
            idealSourcePath = args[1];
            idealClassPath = TEMP_PATH+"solution/";
            System.out.println(idealClassPath);
            Ckjm.run(classPath, idealClassPath);
        }

        System.out.println(sourcePath);
        System.out.println(classPath);

        Checkstyle.run(sourcePath);
        SpotBugs.run(classPath);
    }

    public static void printHelp(){
        System.out.println("java -jar Codeverse.jar <source java file> [-s <solution java file>]");
    }

    // Find "-s"
    public static int strIndexOf(String[] arr, String str){
        for (int i=0; i<=arr.length; i++){
            if (arr[i] == str) return i;
        }
        return -1;
    }

}