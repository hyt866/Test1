/**
 * Created by tony on 9/29/16.
 */
public class Sms {
    private String keyword;
    private String _flowExecutionKey;
    private String p_ie;

    public String getKeyword() {
        return keyword;
    }

    public String get_flowExecutionKey() {
        return _flowExecutionKey;
    }

    public String getP_ie() {
        return p_ie;
    }

    @Override
    public String toString() {
        return keyword + ", " +
                _flowExecutionKey + ", " +
                p_ie;
    }
}
