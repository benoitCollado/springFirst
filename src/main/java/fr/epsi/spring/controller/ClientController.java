package fr.epsi.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.epsi.spring.model.Client;
import fr.epsi.spring.service.ClientService;
import java.util.List;

import fr.epsi.spring.security.annotation.*;




@Controller
@RequestMapping("/clients")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    
    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }


    @GetMapping
    @Authenticated
    public String showLoginPage(Model model) {
        model.addAttribute("clients", clientService.findAll());
        List<Client> listeClient = clientService.findAll();
        for(Client client: listeClient){
            logger.info("l'id: {}", client.getId());
            logger.info("le nom: {}", client.getFirstName());
        }
        return "client-list";
    }

    @GetMapping("/create")
    @Authenticated
    @HasRole("USER")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        return "client-form";
    }

    @PostMapping("/save")
    @Authenticated
    @HasRole("USER")
    public String saveEmployee(@ModelAttribute Client client){
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    @Authenticated
    @HasRole("USER")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Client client = clientService.findById(id).orElseThrow();
        model.addAttribute("client", client);
        return "client-form";
    }

    @GetMapping("/delete/{id}")
    @Authenticated
    @HasRole("USER")
    public String deleteClient(@PathVariable("id") Long id){
        clientService.deleteById(id);
        return "redirect:/clients";
    }
    
    
}
