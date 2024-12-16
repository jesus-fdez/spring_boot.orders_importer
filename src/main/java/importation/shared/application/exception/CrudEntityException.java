package importation.shared.application.exception;

public class CrudEntityException extends BaseException
{
    private static final long serialVersionUID = 4897537939094778170L;

    public CrudEntityException(Throwable error)
    {
	super(error.getMessage(), error);
	this.publicMessage = "Error in an entity operating with database. Please, try later.";
    }
}
