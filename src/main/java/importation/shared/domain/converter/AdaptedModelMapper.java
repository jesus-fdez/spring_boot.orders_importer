package importation.shared.domain.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

@Service("mapper")
public class AdaptedModelMapper extends ModelMapper
{
    public AdaptedModelMapper()
    {
	super();
	this.addConverter(new StringToDateConverter());
    }

    public TypeMap<?, ?> addTypeMap(Class<?> from, Class<?> to)
    {
	return this.createTypeMap(from, to);
    }

}
