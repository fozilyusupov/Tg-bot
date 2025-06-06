package com.example.tgbot1.repo;

import com.example.tgbot1.damion.Maxsulotlar;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MaxsulotlatRepo extends CrudRepository<Maxsulotlar, Long> {

    Maxsulotlar findAllById(long i);

    Maxsulotlar deleteById(long id);
}
