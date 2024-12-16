package importation.shared.infrastructure.repository.contract;

import java.util.List;

import importation.shared.domain.Country;
import importation.shared.domain.Order;
import importation.shared.domain.OrderType;
import importation.shared.domain.Region;
import importation.shared.domain.SalesChannel;

public interface IPersistRepository
{
    void order(List<Order> orders);

    void salesChannel(SalesChannel channel);

    void type(OrderType type);

    void region(Region region);

    void country(Country country);

}