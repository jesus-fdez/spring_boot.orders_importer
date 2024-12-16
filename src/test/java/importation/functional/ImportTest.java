
package importation.functional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import importation.BaseTest;
import importation.order.application.controller.ImportController;
import importation.order.application.dto.ImportResultDto;
import importation.order.application.dto.SummaryDto;
import importation.shared.application.ApiRequester;
import importation.shared.domain.Order;
import importation.shared.infrastructure.repository.contract.CountryRepository;
import importation.shared.infrastructure.repository.contract.OrderRepository;
import importation.shared.infrastructure.repository.contract.OrderTypeRepository;
import importation.shared.infrastructure.repository.contract.RegionRepository;
import importation.shared.infrastructure.repository.contract.SalesChannelRepository;

@SuppressWarnings("unchecked")
public class ImportTest extends BaseTest
{
    @Mock
    private ApiRequester apiRequester;

    @Autowired
    private ImportController importController;

    @Value("classpath:mockSummary.json")
    Resource responseJsonSummary;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SalesChannelRepository salesChannelRepository;

    @BeforeEach
    public void setUp()
    {
	try {
	    when(mockResponse.body()).thenReturn(getResponseFileContent());
	    when(client.send(any(), any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(mockResponse);

	    this.apiRequester = new ApiRequester(client);
	} catch (Exception e) {
	    fail(e);
	}
    }

    @Test
    public void validImport()
    {
	try {
	    importController.setApiRequester(apiRequester);
	    ImportResultDto result = importController.orders();

	    JSONArray ordersFromResponse = new JSONObject(getResponseFileContent()).getJSONArray("content");

	    assertTrue(result.getResult().equals("success"));
	    assertTrue(result.getMessage().equals("Process completed"));
	    assertTrue(getJson(result.getSummary()).equals(getResponseJsonSummary()));

	    IntStream.range(0, ordersFromResponse.length()).forEach(index -> {
		try {
		    JSONObject orderResponse = ordersFromResponse.getJSONObject(index);
		    Optional<Order> orderPersisted = orderRepository.findById(orderResponse.getLong("id"));

		    if (!orderPersisted.isPresent()) {
			fail("Id order not found on database: " + orderResponse.getLong("id"));
			return;
		    }

		    assertOrderContent(orderPersisted.get(), orderResponse);
		} catch (Exception e) {
		    fail(e.getMessage());
		}
	    });

	    assertTrue(orderRepository.count() == 100);
	    assertTrue(countryRepository.count() == 80);
	    assertTrue(orderTypeRepository.count() == 12);
	    assertTrue(regionRepository.count() == 7);
	    assertTrue(salesChannelRepository.count() == 2);
	} catch (Exception e) {
	    fail(e);
	}
    }

    private void assertOrderContent(Order orderPersisted, JSONObject orderResponse) throws Exception
    {
	assertTrue(orderPersisted.getUuid().equals(orderResponse.get("uuid")));
	assertTrue(orderPersisted.getId().equals(orderResponse.getLong("id")));
	assertTrue(orderPersisted.getRegion().getName().equals(orderResponse.get("region")));
	assertTrue(orderPersisted.getCountry().getName().equals(orderResponse.get("country")));
	assertTrue(orderPersisted.getType().getName().equals(orderResponse.get("item_type")));
	assertTrue(orderPersisted.getChannel().getName().equals(orderResponse.get("sales_channel")));
	assertTrue(orderPersisted.getPriority().equals(orderResponse.get("priority")));
	assertTrue(sdf.format(orderPersisted.getDate()).equals(orderResponse.get("date")));
	assertTrue(sdf.format(orderPersisted.getShipDate()).equals(orderResponse.get("ship_date")));
	assertTrue(orderPersisted.getUnitsSold().equals(orderResponse.get("units_sold")));
	assertTrue(orderPersisted.getUnitPrice().equals(convertToBigDecimal(orderResponse.get("unit_price"))));
	assertTrue(orderPersisted.getUnitCost().equals(convertToBigDecimal(orderResponse.get("unit_cost"))));
	assertTrue(orderPersisted.getTotalRevenue().equals(convertToBigDecimal(orderResponse.get("total_revenue"))));
	assertTrue(orderPersisted.getTotalCost().equals(convertToBigDecimal(orderResponse.get("total_cost"))));
	assertTrue(orderPersisted.getTotalProfit().equals(convertToBigDecimal(orderResponse.get("total_profit"))));
    }

    private String getResponseJsonSummary() throws Exception
    {
	return responseJsonSummary.getContentAsString(Charset.defaultCharset());
    }

    private String getJson(SummaryDto summary) throws Exception
    {
	return new ObjectMapper().writeValueAsString(summary);
    }
}
