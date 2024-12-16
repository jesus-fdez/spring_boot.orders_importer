package importation.shared.infrastructure.repository.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import importation.shared.domain.OrderType;
import jakarta.transaction.Transactional;

@Repository("orderTypeRepository")
public interface OrderTypeRepository extends JpaRepository<OrderType, Long>
{
    OrderType findByName(String name);

    @Transactional
    @Query(value = "DELETE FROM ORDER_TYPES", nativeQuery = true)
    void truncate();
}