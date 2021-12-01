import java.nio.file.Paths;


public class Codeverse {

    final static String TEMP_PATH = "/tmp/Codeverse/";
    public static void main(String[] args){
        String sourcePath, classPath, solutionSourcePath, solutionClassPath;
        if (args.length < 1){
            printHelp();
            return;
        }
        
        
        // clean Codeverse tmp
        Executer.exec(new String[]{"rm", "-rf", TEMP_PATH});
                
        Boolean runCmp = false;
        int separatorIndex = strIndexOf(args,"-s");
        if (separatorIndex != -1){
            if (separatorIndex == args.length-1 || separatorIndex == 0){
                // Wrong usage of '-s'
                printHelp();
                return;
            }
            runCmp = true;

            String[] sourceFiles = new String[separatorIndex];
            for (int i=0; i<separatorIndex; i++) sourceFiles[i] = args[i];
            sourcePath = String.join(" ", sourceFiles);
        }else{
            sourcePath = String.join(" ", args);
        }

        // compile the java source cocde
        Executer.exec(new String[]{"javac", "-d", TEMP_PATH+"source/", sourcePath});  // .java -> .class
        classPath = TEMP_PATH+"source/";
        
        System.out.println(sourcePath);
        System.out.println(classPath);

        if (runCmp){
            String[] solutionFiles = new String[args.length - (separatorIndex + 1)];
            for (int i=0; i<solutionFiles.length; i++) solutionFiles[i] = args[separatorIndex+1+i];
            solutionSourcePath = String.join(" ", solutionFiles);

            System.out.println(solutionSourcePath);
            
            // Compile ideal solution
            Executer.exec(new String[]{"javac", "-d", TEMP_PATH+"solution/", solutionSourcePath});  // .java -> .class
            solutionClassPath = TEMP_PATH+"solution/";
            System.out.println(solutionClassPath);
            Ckjm.run(classPath, solutionClassPath);
        }

        // Checkstyle.run(sourcePath);
        // SpotBugs.run(classPath);
    }

    public static void printHelp(){
        System.out.println("java -jar Codeverse.jar <source java file> [-s <solution java file>]");
    }

    // Find "-s"
    public static int strIndexOf(String[] arr, String str){
        for (int i=0; i<arr.length; i++){
            if (arr[i].equals("-s")) return i;
        }
        return -1;
    }

}