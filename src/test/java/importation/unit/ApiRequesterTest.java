package importation.unit;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;

import importation.BaseTest;
import importation.shared.application.ApiRequester;
import importation.shared.application.exception.ImportException;

public class ApiRequesterTest extends BaseTest
{
    @Test
    void validGetResponse()
    {
	try {
	    mockResponse = prepareMockRequester().get("http://0.0.0.0/fake");
	    assertTrue(mockResponse.body().equals(getResponseFileContent()));
	} catch (Exception e) {
	    fail(e.getMessage());
	}
    }

    @Test
    void validExceptionControl()
    {
	try {
	    mockResponse = new ApiRequester().get("http://0.0.0.0/fake");
	    assertTrue(mockResponse.body().equals(getResponseFileContent()));
	} catch (Exception e) {
	    assertThatExceptionOfType(ImportException.class);
	}
    }

    @SuppressWarnings("unchecked")
    private ApiRequester prepareMockRequester() throws Exception
    {
	when(mockResponse.body()).thenReturn(getResponseFileContent());
	when(client.send(any(), any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(mockResponse);

	return new ApiRequester(client);
    }
}
