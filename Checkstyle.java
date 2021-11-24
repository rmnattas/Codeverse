public class Checkstyle {

    final static String GOOGLE_STANDARD_PATH = "./google_checks.xml";
    final static String SUN_STANDARD_PATH = "./sun_checks.xml";

    public static void run(String codePath){
        run(codePath, GOOGLE_STANDARD_PATH);
    }

    public static void run(String codePath, String standardPath){
        Executer.exec("ping www.stackabuse.com");
    }
}
