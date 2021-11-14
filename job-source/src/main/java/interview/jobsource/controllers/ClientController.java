package interview.jobsource.controllers;

import interview.jobsource.dto.ClientRoleRequest;
import interview.jobsource.models.Client;
import interview.jobsource.models.Role;
import interview.jobsource.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Client> saveClient(@RequestBody Client client){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/clients").toUriString());
        return ResponseEntity.created(uri).body(clientService.saveClient(client));
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
