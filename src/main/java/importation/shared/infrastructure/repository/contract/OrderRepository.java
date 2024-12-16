package importation.shared.infrastructure.repository.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import importation.shared.domain.Order;
import jakarta.transaction.Transactional;

@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long>
{
    @Transactional
    @Query(value = "DELETE FROM ORDERS", nativeQuery = true)
    void truncate();
}