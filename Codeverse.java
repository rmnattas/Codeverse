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

        String[] cmd = new String[]{"javac", "-d", TEMP_PATH+"source/", sourcePath};
        Process p = Executer.exec(cmd);  // .java -> .class
        System.out.println(String.join(" ", cmd));
        try { p.waitFor(); } catch (Exception e) { System.out.println(e);}
        classPath = TEMP_PATH+"source/";
        
        System.out.println("=====RECOMMENDATIONS=====");

        if (runCmp){
            String[] solutionFiles = new String[args.length - (separatorIndex + 1)];
            for (int i=0; i<solutionFiles.length; i++) solutionFiles[i] = args[separatorIndex+1+i];
            solutionSourcePath = String.join(" ", solutionFiles);


            // Compile ideal solution
            cmd = new String[]{"javac", "-d", TEMP_PATH+"solution/", solutionSourcePath};
            p = Executer.exec(cmd);  // .java -> .class
            try { p.waitFor(); } catch (Exception e) { System.out.println(e);}
            solutionClassPath = TEMP_PATH+"solution/";

            System.out.println("----------Design-----------\n");
            Ckjm.run(classPath, solutionClassPath);
        }

        System.out.println("-----Style Conventions-----\n");
        Checkstyle.run(sourcePath);
        
        System.out.println("-------Possible Bugs-------\n");
        SpotBugs.run(classPath);

        System.out.println("===========================\n");
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