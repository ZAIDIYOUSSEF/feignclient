package ma.emsi.voiture.feign;

import ma.emsi.voiture.entities.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-client")  // Au lieu de "SERVICE_CLIENT"
public interface ClientService {
    @GetMapping(path = "/api/clients/id/{id}")
    public Client clientById(@PathVariable Long id);
}


