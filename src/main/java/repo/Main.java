package repo;

/**
 * Created with IntelliJ IDEA.
 * User: subhash
 * Date: 3/24/13
 * Time: 12:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String args[]) {
        Scheduler scheduler = new Scheduler();
        try {
            scheduler.scheduleThreads();
        }
        catch (Exception ex) {
            Utils.printlnData(ex.getMessage());
            Utils.printlnData(Utils.getStackTraceAsString(ex));
        }
    }
}
