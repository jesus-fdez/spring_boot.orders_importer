package importation.report.domain.service.contract;

import java.util.List;

import importation.shared.domain.Order;

public interface IGetOrders
{
    List<Order> handle();
}