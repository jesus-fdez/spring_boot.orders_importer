package importation.unit;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import importation.BaseTest;
import importation.order.application.dto.OrderDto;
import importation.order.application.dto.ResponseDto;
import importation.order.application.dto.unmarshall.OrdersUnmarshaller;
import importation.shared.application.exception.ImportException;

public class OrdersUnmarshallerTest extends BaseTest
{
    @Autowired
    private OrdersUnmarshaller ordersUnmarshaller;

    @Test
    void validUnmarshall()
    {
	try {
	    ResponseDto responseDto = ordersUnmarshaller.handle(getResponseFileContent());
	    JSONObject jsonObject = new JSONObject(getResponseFileContent());

	    assertTrue(responseDto.getPage().equals(jsonObject.get("page")));
	    assertTrue(responseDto.getPagination().get("next").equals(jsonObject.getJSONObject("links").get("next")));
	    assertTrue(responseDto.getPagination().get("self").equals(jsonObject.getJSONObject("links").get("self")));
	    assertOrderContent(responseDto.getContent(), jsonObject.getJSONArray("content"));
	} catch (Exception e) {
	    fail(e.getMessage());
	}
    }

    @Test
    void validExceptionControl()
    {
	try {
	    ResponseDto responseDto = ordersUnmarshaller.handle("");
	    JSONObject jsonObject = new JSONObject(getResponseFileContent());

	    assertTrue(responseDto.getPage().equals(jsonObject.get("page")));
	    assertTrue(responseDto.getPagination().get("next").equals(jsonObject.getJSONObject("links").get("next")));
	    assertTrue(responseDto.getPagination().get("self").equals(jsonObject.getJSONObject("links").get("self")));
	    assertOrderContent(responseDto.getContent(), jsonObject.getJSONArray("content"));
	} catch (Exception e) {
	    assertThatExceptionOfType(ImportException.class);
	}
    }

    private void assertOrderContent(List<OrderDto> orders, JSONArray jsonContent)
    {
	IntStream.range(0, orders.size()).forEach(index -> {
	    try {
		assertTrue(orders.get(index).getUuid().equals(jsonContent.getJSONObject(index).get("uuid")));
		assertTrue(orders.get(index).getId().equals(jsonContent.getJSONObject(index).get("id")));
		assertTrue(orders.get(index).getRegion().equals(jsonContent.getJSONObject(index).get("region")));
		assertTrue(orders.get(index).getCountry().equals(jsonContent.getJSONObject(index).get("country")));
		assertTrue(orders.get(index).getItemType().equals(jsonContent.getJSONObject(index).get("item_type")));
		assertTrue(orders.get(index).getSalesChannel()
			.equals(jsonContent.getJSONObject(index).get("sales_channel")));
		assertTrue(orders.get(index).getPriority().equals(jsonContent.getJSONObject(index).get("priority")));
		assertTrue(orders.get(index).getDate().equals(jsonContent.getJSONObject(index).get("date")));
		assertTrue(orders.get(index).getShipDate().equals(jsonContent.getJSONObject(index).get("ship_date")));
		assertTrue(orders.get(index).getUnitsSold().equals(jsonContent.getJSONObject(index).get("units_sold")));
		assertTrue(orders.get(index).getUnitPrice()
			.equals(convertToBigDecimal(jsonContent.getJSONObject(index).get("unit_price"))));
		assertTrue(orders.get(index).getUnitCost()
			.equals(convertToBigDecimal(jsonContent.getJSONObject(index).get("unit_cost"))));
		assertTrue(orders.get(index).getTotalRevenue()
			.equals(convertToBigDecimal(jsonContent.getJSONObject(index).get("total_revenue"))));
		assertTrue(orders.get(index).getTotalCost()
			.equals(convertToBigDecimal(jsonContent.getJSONObject(index).get("total_cost"))));
		assertTrue(orders.get(index).getTotalProfit()
			.equals(convertToBigDecimal(jsonContent.getJSONObject(index).get("total_profit"))));
	    } catch (Exception e) {
		fail(e.getMessage());
	    }
	});
    }
}
