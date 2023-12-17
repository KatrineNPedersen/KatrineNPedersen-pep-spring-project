package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {

    private MessageRepository messageRepository;
    private AccountService accountService;

    public MessageService(MessageRepository messageRepository, AccountService accountService){
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    //3) Create New Message
    public void addMessage(Message message){
        messageRepository.save(message);
    }

    //4) Retrieve All Messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //5 Get Message By ID
    public Message getMessageByID(Integer ID){
        if(messageRepository.findById(ID).isPresent()){
            return messageRepository.findById(ID).get();
        } else {
            return null;
        }
    }

    //6 Delete Message By ID
    public void deleteMessageByID(Integer ID){
        messageRepository.deleteById(ID);
    }

    //7 Update Message By ID
    public Message updateMessageByID(String message_text, Integer message_id){
        Optional<Message> messageOptional = messageRepository.findById(message_id);
        if(messageOptional.isPresent()){
            Message message = messageOptional.get();
            message.setMessage_text(message_text);
            messageRepository.save(message);
            return message;
        } else {
            return null;
        }
    }

    //8 Retrieve All Mesages Written By User
    @Transactional
    public List<Message> findAllMessagesByPostedBy(Integer account_id){
        if(accountService.validUserID(account_id)) {
            return (List<Message>) messageRepository.findAllMessagesByPostedBy(account_id);
        } else {
            return null;
        }

    }

}
