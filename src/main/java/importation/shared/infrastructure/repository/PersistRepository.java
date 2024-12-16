package importation.shared.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import importation.shared.domain.Country;
import importation.shared.domain.Order;
import importation.shared.domain.OrderType;
import importation.shared.domain.Region;
import importation.shared.domain.SalesChannel;
import importation.shared.infrastructure.repository.contract.IPersistRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@Repository("persistRepository")
public class PersistRepository implements IPersistRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void order(List<Order> orders)
    {
	orders.stream().forEach(order -> {
	    entityManager.persist(order);
	});
    }

    @Override
    public void salesChannel(SalesChannel channel)
    {
	entityManager.persist(channel);
    }

    @Override
    public void type(OrderType type)
    {
	entityManager.persist(type);
    }

    @Override
    public void region(Region region)
    {
	entityManager.persist(region);
    }

    @Override
    public void country(Country country)
    {
	entityManager.persist(country);
    }
}
