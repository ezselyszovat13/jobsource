package interview.jobsource.services;

import interview.jobsource.models.Client;
import interview.jobsource.models.Role;

import java.util.List;

public interface ClientService {
    Client saveClient(Client client);
    Role saveRole(Role role);
    void addRoleToClient(String email, String rolename);
    Client getClient(String clientName);
    List<Client> getClients();
}
