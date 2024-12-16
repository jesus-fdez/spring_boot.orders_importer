package importation.shared.application.exception;

public class ConnectionException extends BaseException
{
    private static final long serialVersionUID = -6487241875534156349L;

    public ConnectionException(Throwable error)
    {
	super(error.getMessage(), error);
	this.publicMessage = "Connection error. Please, try later.";
    }
}
