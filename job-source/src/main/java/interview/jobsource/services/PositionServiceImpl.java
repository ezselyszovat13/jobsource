package interview.jobsource.services;

import interview.jobsource.security.PositionNotFoundException;
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
        if(position.getName().length() > 50){
            throw new IllegalArgumentException("Name of the position cannot be longer than 50 characters!");
        }
        if(position.getLocation().length() > 50){
            throw new IllegalArgumentException("Name of the location cannot be longer than 50 characters!");
        }
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
        if(keyword.length() > 50){
            throw new IllegalArgumentException("Name of the position cannot be longer than 50 characters!");
        }
        if(location.length() > 50){
            throw new IllegalArgumentException("Name of the location cannot be longer than 50 characters!");
        }
        Optional<List<Position>> positions = positionRepository.findMatchingPositions(keyword, location);
        if(positions.isEmpty()){
            throw new PositionNotFoundException("No position found with the given criteria!");
        }
        return positions.get();
    }
}
