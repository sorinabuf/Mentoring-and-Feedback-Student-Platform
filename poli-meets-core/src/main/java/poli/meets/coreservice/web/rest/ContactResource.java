package poli.meets.coreservice.web.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poli.meets.coreservice.service.ContactService;
import poli.meets.coreservice.service.dto.ContactDTO;
import poli.meets.coreservice.service.dto.EmailDTO;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ContactResource {
    private final ContactService contactService;

    @PostMapping("/contact-message")
    public ResponseEntity<Void> sendMessage(
            @RequestBody ContactDTO contactDTO) {

        contactService.sendMessage(contactDTO);
        return ResponseEntity.noContent().build();
    }
}
