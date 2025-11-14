package fr.epsi.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.epsi.spring.model.Client;
import fr.epsi.spring.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Transactional
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    @Transactional
    public Optional<Client> findById(Long id){
        return clientRepository.findById(id);
    }

    @Transactional
    public Client save(Client client){
        if(client.getId() != null){
            Optional<Client> existing = clientRepository.findById(client.getId());
            Client toUpdate = existing.get();
                toUpdate.setFirstName(client.getFirstName());
                toUpdate.setLastName(client.getLastName());
                toUpdate.setEmail(client.getEmail());
                return clientRepository.save(toUpdate);
        }else{
            return clientRepository.save(client);
        }
    }

    @Transactional
    public void deleteById(Long id){
        clientRepository.deleteById(id);
    }
}
