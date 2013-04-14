package repo;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: subhash
 * Date: 3/24/13
 * Time: 1:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    public static void printlnData (String msg) {
        System.out.println(msg);
    }

    public static String getStackTraceAsString(Exception ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}

