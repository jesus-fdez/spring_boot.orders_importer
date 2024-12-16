package importation.shared.infrastructure.repository.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import importation.shared.domain.Region;
import jakarta.transaction.Transactional;

@Repository("regionRepository")
public interface RegionRepository extends JpaRepository<Region, Long>
{
    Region findByName(String name);

    @Transactional
    @Query(value = "DELETE FROM REGIONS", nativeQuery = true)
    void truncate();
}