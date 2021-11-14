package interview.jobsource.controllers;

import interview.jobsource.PositionNotFoundException;
import interview.jobsource.dto.ClientRoleRequest;
import interview.jobsource.dto.SearchRequest;
import interview.jobsource.models.Client;
import interview.jobsource.models.Position;
import interview.jobsource.models.Role;
import interview.jobsource.services.ClientService;
import interview.jobsource.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Position> saveClient(@RequestBody Position position){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/position/{positionId}").toUriString());
        return ResponseEntity.created(uri).body(positionService.savePosition(position));
    }

    @GetMapping("/positions/{positionId}")
    public ResponseEntity<Position> getPosition(@PathVariable Long positionId){
        return ResponseEntity.ok().body(positionService.getPositionById(positionId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Position>> getPosition(@RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok().body(positionService.getMatches(searchRequest.getName(),searchRequest.getLocation()));
    }
}
