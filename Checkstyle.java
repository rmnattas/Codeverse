public class Checkstyle {

    final static String GOOGLE_STANDARD_PATH = "./google_checks.xml";
    final static String SUN_STANDARD_PATH = "./sun_checks.xml";

    public static void run(String codePath){
        run(codePath, GOOGLE_STANDARD_PATH);
    }

    public static void run(String codePath, String standardPath){
        // String[] command = {"java", "-jar", "./checkstyle-9.1-all.jar", "-c", standardPath, codePath};
        String[] command = {"pwd"};
        Executer.exec(command);
    }
}
