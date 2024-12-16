package importation.order.domain.service.contract;

import java.util.List;

import importation.order.application.dto.OrderDto;
import importation.shared.application.exception.CrudEntityException;
import importation.shared.domain.Order;

public interface ICreateOrder
{
    List<Order> handle(List<OrderDto> orderDto) throws CrudEntityException;
}
