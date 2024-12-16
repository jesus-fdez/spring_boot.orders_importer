package importation.unit;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import importation.BaseTest;
import importation.order.application.dto.OrderDto;
import importation.order.domain.service.CreateOrder;
import importation.order.domain.service.contract.ICreateOrder;
import importation.shared.application.exception.ImportException;
import importation.shared.domain.Order;
import importation.shared.domain.converter.OrderConverter;
import importation.shared.infrastructure.repository.contract.CountryRepository;
import importation.shared.infrastructure.repository.contract.IPersistRepository;
import importation.shared.infrastructure.repository.contract.OrderRepository;
import importation.shared.infrastructure.repository.contract.OrderTypeRepository;
import importation.shared.infrastructure.repository.contract.RegionRepository;
import importation.shared.infrastructure.repository.contract.SalesChannelRepository;

public class OrderCreatorTest extends BaseTest
{
    @Mock
    private ICreateOrder orderCreator;

    @Autowired
    private OrderConverter orderConverter;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private OrderTypeRepository orderTypeRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private SalesChannelRepository salesChannelRepository;

    @Mock
    private IPersistRepository persistRepository;

    @Test
    void validMapDtoToEntity()
    {
	List<OrderDto> ordersDto = generator.objects(OrderDto.class, 100).collect(Collectors.toList());

	doNothing().when(persistRepository).order(any());
	doNothing().when(persistRepository).country(any());
	doNothing().when(persistRepository).region(any());
	doNothing().when(persistRepository).salesChannel(any());
	doNothing().when(persistRepository).type(any());

	try {
	    orderCreator = new CreateOrder(countryRepository, orderTypeRepository, regionRepository,
		    salesChannelRepository, orderConverter, persistRepository);

	    List<Order> ordersEntities = orderCreator.handle(ordersDto);
	    assertOrderContent(ordersDto, ordersEntities);

	} catch (Exception e) {
	    fail(e.getMessage());
	}

    }

    @Test
    void validExceptionControl()
    {
	try {
	    orderCreator.handle(new ArrayList<OrderDto>());
	} catch (Exception e) {
	    assertThatExceptionOfType(ImportException.class);
	}
    }

    private void assertOrderContent(List<OrderDto> orders, List<Order> ordersEntity)
    {
	IntStream.range(0, ordersEntity.size()).forEach(index -> {
	    try {
		assertTrue(ordersEntity.get(index).getCountry().getName().equals(orders.get(index).getCountry()));
		assertTrue(ordersEntity.get(index).getType().getName().equals(orders.get(index).getItemType()));
		assertTrue(ordersEntity.get(index).getRegion().getName().equals(orders.get(index).getRegion()));
		assertTrue(ordersEntity.get(index).getChannel().getName().equals(orders.get(index).getSalesChannel()));
		assertTrue(ordersEntity.get(index).getId().toString().equals(orders.get(index).getId()));
		assertTrue(ordersEntity.get(index).getUuid() == orders.get(index).getUuid());
		assertTrue(ordersEntity.get(index).getPriority() == orders.get(index).getPriority());
		assertTrue(sdf.format(ordersEntity.get(index).getDate()).equals(orders.get(index).getDate()));
		assertTrue(sdf.format(ordersEntity.get(index).getShipDate()).equals(orders.get(index).getShipDate()));
		assertTrue(ordersEntity.get(index).getUnitsSold() == orders.get(index).getUnitsSold());
		assertTrue(ordersEntity.get(index).getUnitPrice() == orders.get(index).getUnitPrice());
		assertTrue(ordersEntity.get(index).getUnitCost() == orders.get(index).getUnitCost());
		assertTrue(ordersEntity.get(index).getTotalRevenue() == orders.get(index).getTotalRevenue());
		assertTrue(ordersEntity.get(index).getTotalCost() == orders.get(index).getTotalCost());
		assertTrue(ordersEntity.get(index).getTotalProfit() == orders.get(index).getTotalProfit());
	    } catch (Exception e) {
		fail(e.getMessage());
	    }
	});
    }
}
