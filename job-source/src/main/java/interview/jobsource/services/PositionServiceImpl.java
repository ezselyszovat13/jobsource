package interview.jobsource.services;

import interview.jobsource.PositionNotFoundException;
import interview.jobsource.models.Position;
import interview.jobsource.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionRepository positionRepository;

    @Override
    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public Position getPositionById(Long positionId) {
        return positionRepository.findById(positionId).get();
    }

    @Override
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    public List<Position> getMatches(String keyword, String location) {
        return positionRepository.findMatchingPositions(keyword, location).orElseThrow( () ->
                new PositionNotFoundException("No position found with a given criteria!"));
    }
}
