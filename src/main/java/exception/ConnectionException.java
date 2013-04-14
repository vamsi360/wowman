package exception;

/**
 * Created with IntelliJ IDEA.
 * User: subhash
 * Date: 3/24/13
 * Time: 12:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionException extends Exception {

    public ConnectionException() {
        super();
    }

    public ConnectionException(String msg) {
        super(msg);
    }
}
