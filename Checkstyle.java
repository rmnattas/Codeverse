public class Checkstyle {

    final static String GOOGLE_STANDARD_PATH = "lib/google_checks.xml";
    final static String SUN_STANDARD_PATH = "lib/sun_checks.xml";

    // gets the .java files
    public static void run(String codePath){
        run(codePath, GOOGLE_STANDARD_PATH);
    }

    public static void run(String codePath, String standardPath){
        String[] command = {"java", "-jar", "lib/checkstyle-9.1-all.jar", "-c", standardPath, codePath};
        Process p = Executer.exec(command);
        Executer.printResults(p);
    }
}
