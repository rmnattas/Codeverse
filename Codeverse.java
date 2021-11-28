import java.nio.file.Paths;


public class Codeverse {

    final static String TEMP_PATH = "/tmp/Codeverse/";
    public static void main(String[] args){
        String sourcePath, classPath, idealSourcePath, idealClassPath;
        if (args.length == 0){
            printHelp();
            return;
        }

        // clean Codeverse tmp
        Executer.exec(new String[]{"rm", "-rf", TEMP_PATH});

        // compile the java source cocde
        Executer.exec(new String[]{"javac", "-d", TEMP_PATH+"source/" , args[0]});  // .java -> .class
        sourcePath = args[0];
        classPath = TEMP_PATH+"source/";
        
        if (args.length == 2){
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
        System.out.println("java -jar Codeverse.jar <source java file> [solution java file]");
    }

    public static String[] removeLast(String[] array){
        String[] newArray = new String[array.length-1];
        for (int i=0; i<newArray.length; i++){
            newArray[i] = array[i];
        }
        return newArray;
    }
}