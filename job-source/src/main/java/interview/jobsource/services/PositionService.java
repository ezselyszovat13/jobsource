package interview.jobsource.services;

import interview.jobsource.models.Client;
import interview.jobsource.models.Position;
import interview.jobsource.models.Role;

import java.util.List;
import java.util.Optional;

public interface PositionService {
    Position savePosition(Position position);
    Position getPositionById(Long positionId);
    List<Position> getAllPositions();
    List<Position> getMatches(String keyword, String location);
}
