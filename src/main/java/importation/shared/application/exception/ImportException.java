package importation.shared.application.exception;

public class ImportException extends BaseException
{
    private static final long serialVersionUID = -7525944462747882741L;

    public ImportException(Throwable error)
    {
	super(error.getMessage(), error);
	this.publicMessage = "Internal error during import process. Please, try later.";
    }
}
