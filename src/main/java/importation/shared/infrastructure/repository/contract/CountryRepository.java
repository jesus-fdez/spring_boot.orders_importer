package importation.shared.infrastructure.repository.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import importation.shared.domain.Country;
import jakarta.transaction.Transactional;

@Repository("countryRepository")
public interface CountryRepository extends JpaRepository<Country, Long>
{
    Country findByName(String name);

    @Transactional
    @Query(value = "DELETE FROM COUNTRIES", nativeQuery = true)
    void truncate();
}