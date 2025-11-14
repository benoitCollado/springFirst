package fr.epsi.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.epsi.spring.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
    
}
