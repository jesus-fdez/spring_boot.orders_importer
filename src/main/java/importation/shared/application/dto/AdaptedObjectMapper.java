package importation.shared.application.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Service("objectMapper")
public class AdaptedObjectMapper extends ObjectMapper
{
    private static final long serialVersionUID = 1890774937749645945L;

    public AdaptedObjectMapper()
    {
	super();

	SimpleModule module = new SimpleModule();
	module.addDeserializer(BigDecimal.class, new AdaptedBigDecimalDeserializer());
	this.registerModule(module);
    }

}
