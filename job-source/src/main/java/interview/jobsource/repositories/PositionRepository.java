package interview.jobsource.repositories;

import interview.jobsource.models.Client;
import interview.jobsource.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository <Position, Long> {
    @Query("FROM Position p WHERE p.name LIKE %:name% and p.location=:location")
    Optional<List<Position>> findMatchingPositions(@Param("name") String name, @Param("location") String location);
}
