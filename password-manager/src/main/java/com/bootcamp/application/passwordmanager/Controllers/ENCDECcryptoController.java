package com.bootcamp.application.passwordmanager.Controllers;

import com.bootcamp.application.passwordmanager.DTOs.DecryptedDetails;
import com.bootcamp.application.passwordmanager.DTOs.PasswordFront;
import com.bootcamp.application.passwordmanager.DTOs.UpdatingDto;
import com.bootcamp.application.passwordmanager.models.Password;
import com.bootcamp.application.passwordmanager.service.PO1MService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v0")
public class ENCDECcryptoController {
    private final PO1MService po1MService;


    @PostMapping("/manage")
    public ResponseEntity<Password> encode(@RequestBody PasswordFront front)throws Exception{
        log.info("request to manage password");
        return ResponseEntity.ok(po1MService.encryptDetails(front));
    }

    @GetMapping("/details")
    public ResponseEntity<DecryptedDetails> decode(@RequestParam Long id)throws Exception{
        log.info("request to get details");
        return ResponseEntity.ok(po1MService.decrypt(id));
    }
    @PutMapping("/updateManager/{id}")
    public ResponseEntity<Password> update(@PathVariable Long id, @RequestBody UpdatingDto updatingDto) {
        log.info("Request to update the manager was received");

        try {
            Password updatedPassword = po1MService.updateDetails(id, updatingDto);
            if (updatedPassword == null) {
                // Handle case where Password with given ID is not found
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedPassword);
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
