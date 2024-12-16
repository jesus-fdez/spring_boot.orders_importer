package importation.shared.application.contract;

import java.net.http.HttpResponse;

import importation.shared.application.exception.ConnectionException;

public interface IApiRequester
{
    HttpResponse<String> get(final String endpoint) throws ConnectionException;
}
