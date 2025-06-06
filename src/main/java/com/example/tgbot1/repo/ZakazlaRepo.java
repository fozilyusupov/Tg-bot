package com.example.tgbot1.repo;

import com.example.tgbot1.damion.Zakazla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZakazlaRepo extends JpaRepository<Zakazla, Long> {




   List<Zakazla> findByChatidAndModelname(String chat, String model);

   Zakazla findAllByChatidAndSoni(String s,int i);

   List<Zakazla>findAllByChatidAndSoniAndStatus(String s,int i,boolean b);

   List<Zakazla>findAllByChatidAndStatus(String c,boolean b);
}
