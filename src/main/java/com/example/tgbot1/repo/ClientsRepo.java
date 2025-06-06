package com.example.tgbot1.repo;

import com.example.tgbot1.damion.Clients;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientsRepo extends CrudRepository<Clients, Long> {
    List<Clients> findAllByName(String name);
    List<Clients> findAllByChatId(String s);



}
