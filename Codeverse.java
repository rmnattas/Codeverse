import java.nio.file.Paths;


public class Codeverse {
    public static void main(String[] args){
        String sourcePath, classPath, idealSourcePath, idealClassPath;
        if (args.length == 0){
            printHelp();
            return;
        }

        // compile the java source cocde
        Executer.exec(new String[]{"javac", args[0]});  // .java -> .class
        sourcePath = args[0];
        String[] pathSplit = args[0].split("/");
        classPath = String.join("/", removeLast(pathSplit)) + "/*.class";;

        if (args.length == 2){
            // Compile ideal solution
            Executer.exec(new String[]{"javac", args[1]});  // .java -> .class
            idealSourcePath = args[1];
            pathSplit = args[1].split("/");
            idealClassPath = String.join("/", removeLast(pathSplit)) + "/*.class";
            System.out.println(idealClassPath);
            Ckjm.run(classPath, idealClassPath);
        }

        System.out.println(sourcePath);
        System.out.println(classPath);

        Checkstyle.run(sourcePath);
        SpotBugs.run(classPath);
    }

    public static void printHelp(){
        System.out.println("java -jar Codeverse.jar <source code java file> [ideal source code java file]");
    }

    public static String[] removeLast(String[] array){
        String[] newArray = new String[array.length-1];
        for (int i=0; i<newArray.length; i++){
            newArray[i] = array[i];
        }
        return newArray;
    }
}