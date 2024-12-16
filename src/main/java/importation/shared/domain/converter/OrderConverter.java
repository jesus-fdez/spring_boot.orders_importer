package importation.shared.domain.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import importation.order.application.dto.OrderDto;
import importation.shared.domain.Country;
import importation.shared.domain.Order;
import importation.shared.domain.OrderType;
import importation.shared.domain.Region;
import importation.shared.domain.SalesChannel;

@Service
public class OrderConverter
{
    @Autowired
    private AdaptedModelMapper mapper;

    public List<Order> dtoToModel(List<OrderDto> ordersDto)
    {
	List<Order> orders = new ArrayList<Order>();

	ordersDto.stream().forEach(orderDto -> {
	    Order order = new Order();
	    mapper.map(orderDto, order);
	    populateRelations(order, orderDto);
	    orders.add(order);
	});

	return orders;
    }

    private void populateRelations(Order order, OrderDto orderDto)
    {
	order.setRegion(new Region(orderDto.getRegion()));
	order.setCountry(new Country(orderDto.getCountry()));
	order.setType(new OrderType(orderDto.getItemType()));
	order.setChannel(new SalesChannel(orderDto.getSalesChannel()));
    }
}
