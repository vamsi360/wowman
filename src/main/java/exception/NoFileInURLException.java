package exception;

/**
 * Created with IntelliJ IDEA.
 * User: subhash
 * Date: 3/24/13
 * Time: 1:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class NoFileInURLException extends Exception {
    public NoFileInURLException() {
        super();
    }
    public NoFileInURLException(String msg) {
        super(msg);
    }
}
