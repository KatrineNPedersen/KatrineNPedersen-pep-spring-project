package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("")
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //1) User Registration
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        if(accountService.validUser(account.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(account);
        }

        if(account.getUsername() != "" && account.getPassword().length() >= 4){
            accountService.register(account);
            account = accountService.getAccountByUsername(account.getUsername());
            return ResponseEntity.ok().body(account);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(account);
    }

    //2) User Login
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account){
            if(accountService.login(account.getUsername(), account.getPassword())){
                account = accountService.getAccountByUsername(account.getUsername());
            return ResponseEntity.ok().body(account);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(account);
            }
    }

    //3) Create New Message

    @PostMapping("messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message){
        if(message.getMessage_text() != "" && message.getMessage_text().length() <= 255 && accountService.validUserID(message.getPosted_by())) {
            messageService.addMessage(message);
            return ResponseEntity.ok().body(message);
        }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    //4) Retrieve All Messages
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok().body(messageService.getAllMessages());
    }

    //5) Retrieve Message By ID
    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageByID(@PathVariable String message_id){
            return ResponseEntity.ok().body(messageService.getMessageByID(Integer.valueOf(message_id)));
        
    }

    //6 Delete Message By ID
    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<String> deleteMessageByID(@PathVariable String message_id){
        if(messageService.getMessageByID(Integer.valueOf(message_id)) != null){
            messageService.deleteMessageByID(Integer.valueOf(message_id));
            return ResponseEntity.ok().body("1");
        } else {
            return ResponseEntity.ok().body("");
        }
    }

    //7 Update Message By ID
    @PatchMapping("messages/{message_id}")
    public ResponseEntity<String> updateMessageByID(@PathVariable String message_id, @RequestBody Message message){
        if(messageService.getMessageByID(Integer.valueOf(message_id)) != null && message.getMessage_text().length() <= 255 && message.getMessage_text() != ""){
            messageService.updateMessageByID(message.getMessage_text(), Integer.valueOf(message_id));
            return ResponseEntity.ok().body("1");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    //8 Retrieve All Mesages Written By User
    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> findMessagesByPostedBy(@PathVariable String account_id){
        return ResponseEntity.ok().body(messageService.findAllMessagesByPostedBy(Integer.valueOf(account_id)));
        
        
    }

}
