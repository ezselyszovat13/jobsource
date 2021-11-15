package interview.jobsource.services;

import interview.jobsource.models.Client;
import interview.jobsource.models.Role;
import interview.jobsource.repositories.ClientRepository;
import interview.jobsource.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.regex.Pattern;

@Service
@Transactional
public class ClientServiceImpl implements ClientService, UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Client saveClient(Client client) {
        Optional<Client> clientByEmail = clientRepository.findByEmail(client.getEmail());
        if(clientByEmail.isPresent()){
            throw new IllegalStateException("Email was already taken!");
        }
        String email = client.getEmail();
        if(email.length() > 100){
            throw new IllegalArgumentException("Email cannot be longer than 100 characters!");
        }
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(!email.matches(regexPattern)){
            throw new IllegalArgumentException("Not a valid email address was given!");
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
        addRoleToClient(client.getEmail(),"ROLE_USER");
        return client;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToClient(String email, String rolename) {
        Optional<Client> client = clientRepository.findByEmail(email);
        Role role = roleRepository.findByName(rolename);
        client.get().getRoles().add(role);
    }

    @Override
    public Client getClient(String email) {
        return clientRepository.findByEmail(email).orElseThrow(() -> {
            throw new IllegalStateException("No client found with given email.");
        });
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findByEmail(email);
        if(!client.isPresent()){
            throw new UsernameNotFoundException("User could not be found in the database!");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        Client actualClient = client.get();
        for (Role role : actualClient.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(actualClient.getEmail(), actualClient.getPassword(), authorities);
    }
}
