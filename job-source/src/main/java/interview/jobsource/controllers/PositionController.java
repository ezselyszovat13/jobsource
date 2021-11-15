package interview.jobsource.controllers;

import interview.jobsource.security.PositionNotFoundException;
import interview.jobsource.dto.SearchRequest;
import interview.jobsource.models.Position;
import interview.jobsource.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @GetMapping("/positions")
    public ResponseEntity<List<Position>> getPositions(){
        return ResponseEntity.ok().body(positionService.getAllPositions());
    }

    @PostMapping("/positions")
    public ResponseEntity<?> savePosition(@RequestBody Position position){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/position/{positionId}").toUriString());
        try{
            positionService.savePosition(position);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.created(uri).body(position);
    }

    @GetMapping("/positions/{positionId}")
    public ResponseEntity<Position> getPosition(@PathVariable Long positionId){
        return ResponseEntity.ok().body(positionService.getPositionById(positionId));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getPosition(@RequestBody SearchRequest searchRequest){
        List<Position> positions;
        try{
            positions = positionService.getMatches(searchRequest.getName(),searchRequest.getLocation());
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (PositionNotFoundException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.ok().body(positions);
    }
}
