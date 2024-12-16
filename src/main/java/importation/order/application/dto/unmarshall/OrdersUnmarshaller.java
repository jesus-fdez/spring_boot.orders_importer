package importation.order.application.dto.unmarshall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import importation.order.application.dto.ResponseDto;
import importation.shared.application.dto.AdaptedObjectMapper;
import importation.shared.application.exception.ImportException;

@Service
public class OrdersUnmarshaller
{
    @Autowired
    private AdaptedObjectMapper objectMapper;

    public ResponseDto handle(String data) throws ImportException
    {
	try {
	    TypeReference<ResponseDto> jacksonTypeReference = new TypeReference<>() {
	    };
	    ResponseDto orders = objectMapper.readValue(data, jacksonTypeReference);

	    return orders;
	} catch (Exception e) {
	    throw new ImportException(e);
	}
    }
}
