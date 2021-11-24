
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
        classPath = args[0].replace(".java", ".class");

        if (args.length == 2){
            // Compile ideal solution
            Executer.exec(new String[]{"javac", args[1]});  // .java -> .class
            idealSourcePath = args[1];
            idealClassPath = args[1].replace(".java", ".class");
        }

        Checkstyle.run(sourcePath);
        SpotBugs.run(classPath);
    }

    public static void printHelp(){
        System.out.println("java -jar Codeverse.jar <source code java file> [ideal source code java file]");
    }
}