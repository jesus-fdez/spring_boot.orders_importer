package importation.shared.domain.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.modelmapper.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringToDateConverter extends AbstractConverter<String, Date>
{
    private static final Logger LOG = LoggerFactory.getLogger(StringToDateConverter.class);

    public static final String dateFormat = "M/d/yyyy";

    @Override
    protected Date convert(String source)
    {
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    return sdf.parse(source);
	} catch (ParseException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    return null;
	}
    }
}
