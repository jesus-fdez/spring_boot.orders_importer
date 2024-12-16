package importation.unit;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import importation.BaseTest;
import importation.order.application.dto.OrderDto;
import importation.shared.application.exception.ImportException;
import importation.shared.domain.Order;
import importation.shared.domain.converter.OrderConverter;

public class OrderConverterTest extends BaseTest
{
    @Autowired
    private OrderConverter orderConverter;

    @Test
    void validMapDtoToEntity()
    {
	try {
	    List<OrderDto> ordersDto = generator.objects(OrderDto.class, 100).collect(Collectors.toList());

	    try {
		List<Order> orderEntities = orderConverter.dtoToModel(ordersDto);
		assertOrderContent(ordersDto, orderEntities);
	    } catch (Exception e) {
		fail(e.getMessage());
	    }
	} catch (Exception e) {
	    fail(e.getMessage());
	}
    }

    @Test
    void validExceptionControl()
    {
	try {
	    new OrderConverter().dtoToModel(new ArrayList<OrderDto>());
	} catch (Exception e) {
	    assertThatExceptionOfType(ImportException.class);
	}
    }

    private void assertOrderContent(List<OrderDto> orders, List<Order> ordersEntity)
    {
	IntStream.range(0, ordersEntity.size()).forEach(index -> {
	    assertTrue(orders.get(index).getUuid().equals(ordersEntity.get(index).getUuid()));
	    assertTrue(orders.get(index).getId().equals(ordersEntity.get(index).getId().toString()));
	    assertTrue(orders.get(index).getRegion().equals(ordersEntity.get(index).getRegion().getName()));
	    assertTrue(orders.get(index).getCountry().equals(ordersEntity.get(index).getCountry().getName()));
	    assertTrue(orders.get(index).getItemType().equals(ordersEntity.get(index).getType().getName()));
	    assertTrue(orders.get(index).getSalesChannel().equals(ordersEntity.get(index).getChannel().getName()));
	    assertTrue(orders.get(index).getPriority().equals(ordersEntity.get(index).getPriority()));
	    assertTrue(orders.get(index).getDate().equals(sdf.format(ordersEntity.get(index).getDate())));
	    assertTrue(orders.get(index).getShipDate().equals(sdf.format(ordersEntity.get(index).getShipDate())));
	    assertTrue(orders.get(index).getUnitsSold().equals(ordersEntity.get(index).getUnitsSold()));
	    assertTrue(orders.get(index).getUnitPrice()
		    .equals(convertToBigDecimal(ordersEntity.get(index).getUnitPrice())));
	    assertTrue(
		    orders.get(index).getUnitCost().equals(convertToBigDecimal(ordersEntity.get(index).getUnitCost())));
	    assertTrue(orders.get(index).getTotalRevenue()
		    .equals(convertToBigDecimal(ordersEntity.get(index).getTotalRevenue())));
	    assertTrue(orders.get(index).getTotalCost()
		    .equals(convertToBigDecimal(ordersEntity.get(index).getTotalCost())));
	    assertTrue(orders.get(index).getTotalProfit()
		    .equals(convertToBigDecimal(ordersEntity.get(index).getTotalProfit())));
	});
    }
}
