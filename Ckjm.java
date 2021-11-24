public class Ckjm {

    public static void run(String classPath, String idealClassPath){
        String command = "java " + "-jar " + "lib/ckjm-1.8.jar " + classPath;
        System.out.println(command);
        Process p = Executer.exec(command);
        System.out.println(p);
        Executer.printResults(p);
    }
}
