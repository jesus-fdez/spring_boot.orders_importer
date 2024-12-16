package importation.shared.infrastructure.repository.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import importation.shared.domain.SalesChannel;
import jakarta.transaction.Transactional;

@Repository("salesChannelRepository")
public interface SalesChannelRepository extends JpaRepository<SalesChannel, Long>
{
    SalesChannel findByName(String name);

    @Transactional
    @Query(value = "DELETE FROM SALE_CHANNELS", nativeQuery = true)
    void truncate();
}