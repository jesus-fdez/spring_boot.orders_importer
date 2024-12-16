package importation.report.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import importation.report.domain.service.contract.IGetOrders;
import importation.shared.domain.Order;
import importation.shared.infrastructure.repository.contract.OrderRepository;

@Service("getOrders")
public class GetOrders implements IGetOrders
{
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> handle()
    {
	return orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
