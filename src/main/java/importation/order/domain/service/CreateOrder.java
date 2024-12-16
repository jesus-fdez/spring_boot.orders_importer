package importation.order.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import importation.order.application.dto.OrderDto;
import importation.order.domain.service.contract.ICreateOrder;
import importation.shared.application.exception.CrudEntityException;
import importation.shared.domain.Country;
import importation.shared.domain.Order;
import importation.shared.domain.OrderType;
import importation.shared.domain.Region;
import importation.shared.domain.SalesChannel;
import importation.shared.domain.converter.OrderConverter;
import importation.shared.infrastructure.repository.contract.CountryRepository;
import importation.shared.infrastructure.repository.contract.IPersistRepository;
import importation.shared.infrastructure.repository.contract.OrderTypeRepository;
import importation.shared.infrastructure.repository.contract.RegionRepository;
import importation.shared.infrastructure.repository.contract.SalesChannelRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("orderCreator")
public class CreateOrder implements ICreateOrder
{
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SalesChannelRepository salesChannelRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private IPersistRepository persistRepository;

    public List<Order> handle(List<OrderDto> orderDto) throws CrudEntityException
    {
	try {
	    List<Order> orders = orderConverter.dtoToModel(orderDto);

	    orders.stream().forEach(order -> {
		handleRelations(order);
	    });

	    persistRepository.order(orders);

	    return orders;
	} catch (Exception e) {
	    throw new CrudEntityException(e);
	}
    }

    private void handleRelations(Order order)
    {
	Country country = countryRepository.findByName(order.getCountry().getName());
	if (country != null) {
	    order.getCountry().setId(country.getId());
	} else {
	    persistRepository.country(order.getCountry());
	}

	OrderType orderType = orderTypeRepository.findByName(order.getType().getName());
	if (orderType != null) {
	    order.getType().setId(orderType.getId());
	} else {
	    persistRepository.type(order.getType());
	}

	Region region = regionRepository.findByName(order.getRegion().getName());
	if (region != null) {
	    order.getRegion().setId(region.getId());
	} else {
	    persistRepository.region(order.getRegion());
	}

	SalesChannel channel = salesChannelRepository.findByName(order.getChannel().getName());
	if (channel != null) {
	    order.getChannel().setId(channel.getId());
	} else {
	    persistRepository.salesChannel(order.getChannel());
	}
    }
}
