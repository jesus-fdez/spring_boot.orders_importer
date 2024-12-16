package importation.shared.application.dto;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.BigDecimalDeserializer;

public class AdaptedBigDecimalDeserializer extends BigDecimalDeserializer
{
    private static final long serialVersionUID = -7247775714729264504L;

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
	BigDecimal result = super.deserialize(p, ctxt);
	return result != null ? result.setScale(2, RoundingMode.HALF_UP) : null;
    }
}
