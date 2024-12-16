package importation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import org.jeasy.random.EasyRandom;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;

import importation.shared.domain.converter.StringToDateConverter;
import importation.shared.infrastructure.EspublicoApplication;

@SpringBootTest
@ContextConfiguration(classes = EspublicoApplication.class)
public class BaseTest extends Mockito
{
    @Value("classpath:mockResponse.json")
    protected Resource responseJsonFile;

    @Mock
    protected HttpClient client;

    @Mock
    protected HttpResponse<String> mockResponse;

    protected EasyRandom generator = new EasyRandom();

    protected SimpleDateFormat sdf = new SimpleDateFormat(StringToDateConverter.dateFormat);

    protected String getResponseFileContent() throws Exception
    {
	return responseJsonFile.getContentAsString(Charset.defaultCharset());
    }

    protected BigDecimal convertToBigDecimal(Object value)
    {
	return new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
    }
}
