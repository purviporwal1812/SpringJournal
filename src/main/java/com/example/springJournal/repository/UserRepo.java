package com.example.springJournal.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.springJournal.entity.User;

public interface UserRepo extends MongoRepository<User , ObjectId> {
    User findByUsername(String username);
    void deleteByUsername(String useranme);

}
