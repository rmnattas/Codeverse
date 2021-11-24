public class Ckjm {

    public static void run(String classPath, String idealClassPath){
        String[] command = {"java", "-jar", "lib/ckjm-1.8.jar", classPath};
        Process p = Executer.exec(command);
        Executer.printResults(p);
    }
}
