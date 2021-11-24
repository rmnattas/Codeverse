
public class Codeverse {
    public static void main(String[] args){
        if (args.length == 0 || args[0] == "-h"){
            printHelp();
            return;
        }

        // call javac on the source code to create the class file
        Executer.exec(new String[]{"javac", args[0]});

        Checkstyle.run(args[0]);
    }

    public static void printHelp(){
        System.out.println("java Codeverse [-h] <source code java file> [ideal source code java file]");
    }
}
