package importation.shared.application;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import importation.shared.application.contract.IApiRequester;
import importation.shared.application.exception.ConnectionException;

@Service
public class ApiRequester implements IApiRequester
{
    private HttpClient client;

    public ApiRequester()
    {
	this.client = HttpClient.newHttpClient();
    }

    public ApiRequester(HttpClient client)
    {
	this.client = client;
    }

    public HttpResponse<String> get(final String endpoint) throws ConnectionException
    {
	try {
	    HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(endpoint)).build();
	    return client.send(request, HttpResponse.BodyHandlers.ofString());
	} catch (Exception e) {
	    throw new ConnectionException(e);
	}
    }
}
