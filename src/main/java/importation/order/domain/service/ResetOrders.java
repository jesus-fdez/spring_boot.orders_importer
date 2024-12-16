package importation.order.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import importation.order.domain.service.contract.IResetOrders;
import importation.shared.infrastructure.repository.contract.CountryRepository;
import importation.shared.infrastructure.repository.contract.OrderRepository;
import importation.shared.infrastructure.repository.contract.OrderTypeRepository;
import importation.shared.infrastructure.repository.contract.RegionRepository;
import importation.shared.infrastructure.repository.contract.SalesChannelRepository;

@Service("resetData")
public class ResetOrders implements IResetOrders
{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SalesChannelRepository salesChannelRepository;

    public void handle()
    {
	orderRepository.deleteAll();
	countryRepository.deleteAll();
	orderTypeRepository.deleteAll();
	regionRepository.deleteAll();
	salesChannelRepository.deleteAll();
    }
}
