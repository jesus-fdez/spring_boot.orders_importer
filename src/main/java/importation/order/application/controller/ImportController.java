package importation.order.application.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import importation.order.application.dto.ImportResultDto;
import importation.order.application.dto.OrderDto;
import importation.order.application.dto.ResponseDto;
import importation.order.application.dto.SummaryDto;
import importation.order.application.dto.unmarshall.OrdersUnmarshaller;
import importation.order.domain.service.contract.ICreateOrder;
import importation.order.domain.service.contract.IResetOrders;
import importation.report.application.contract.IGenerateReport;
import importation.shared.application.contract.IApiRequester;
import importation.shared.application.exception.ConnectionException;
import importation.shared.application.exception.CrudEntityException;
import importation.shared.application.exception.ImportException;
import lombok.Setter;

@RestController
@RequestMapping("/api/v1")
@Setter()
public class ImportController
{
    private static final Logger LOG = LoggerFactory.getLogger(ImportController.class);

    @Setter
    @Autowired
    private IApiRequester apiRequester;

    @Autowired
    private ICreateOrder orderCreator;

    @Autowired
    private OrdersUnmarshaller ordersUnmarshaller;

    @Autowired
    private Environment env;

    @Autowired
    private IResetOrders resetOrders;

    @Autowired
    private IGenerateReport generateReport;

    @GetMapping("/import")
    public ImportResultDto orders()
    {
	String url = env.getProperty("import.service_url");
	SummaryDto summary = new SummaryDto();
	ResponseDto ordersDto;

	try {
	    resetOrders.handle();

	    while (url != null && isValidUrl(url)) {
		HttpResponse<String> response = apiRequester.get(url);
		ordersDto = new ResponseDto();
		ordersDto = ordersUnmarshaller.handle(response.body());

		try {
		    orderCreator.handle(ordersDto.getContent());
		} catch (CrudEntityException e) {
		    throw e;
		}

		addSummary(summary, ordersDto.getContent());

		LOG.info(String.format("Page %s dispatched.", url));
		url = nextPage(ordersDto);
	    }

	    generateReport.handle();

	    return new ImportResultDto("success", "Process completed", summary);
	} catch (ConnectionException | ImportException e) {
	    LOG.error(e.getCause().getMessage(), e);
	    return new ImportResultDto("error", e.getPublicMessage());
	} catch (Exception e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    return new ImportResultDto("error", "Unexpected error. Please, try later.");
	}
    }

    private String nextPage(ResponseDto ordersDto)
    {
	return ordersDto.getPagination().get("next");
    }

    private void addSummary(SummaryDto summary, List<OrderDto> orders)
    {
	orders.stream().forEach(order -> {
	    summary.addRegion(order.getRegion());
	    summary.addCountry(order.getCountry());
	    summary.addType(order.getItemType());
	    summary.addSalesChannel(order.getSalesChannel());
	    summary.addPriority(order.getPriority());
	});
    }

    boolean isValidUrl(String url) throws MalformedURLException
    {
	try {
	    new URL(url);
	    return true;
	} catch (MalformedURLException e) {
	    return false;
	}
    }
}
