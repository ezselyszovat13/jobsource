package interview.jobsource.controllers;

import interview.jobsource.dto.ClientRoleRequest;
import interview.jobsource.models.Client;
import interview.jobsource.models.Role;
import interview.jobsource.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClients(){
        return ResponseEntity.ok().body(clientService.getClients());
    }

    @PostMapping("/clients")
    public ResponseEntity<?> saveClient(@RequestBody Client client){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/clients").toUriString());
        try{
            clientService.saveClient(client);
        }
        catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.created(uri).body(client);
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/roles").toUriString());
        return ResponseEntity.created(uri).body(clientService.saveRole(role));
    }

    @PostMapping("/roles/addclient")
    public ResponseEntity<?> addRoleToClient(@RequestBody ClientRoleRequest clientRoleRequest){
        clientService.addRoleToClient(clientRoleRequest.getEmail(),clientRoleRequest.getRoleName());
        return ResponseEntity.ok().build();
    }
}
