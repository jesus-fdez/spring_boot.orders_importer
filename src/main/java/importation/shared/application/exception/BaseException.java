package importation.shared.application.exception;

public class BaseException extends Exception
{
    private static final long serialVersionUID = -8712609458503667914L;

    protected String publicMessage = "Unexpected error. Please, try later.";

    public BaseException(String message, Throwable cause)
    {
	super(message, cause);
    }

    public String getPublicMessage()
    {
	return publicMessage;
    }
}
