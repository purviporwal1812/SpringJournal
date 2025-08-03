package com.example.springJournal.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.example.springJournal.entity.User;

public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUsersForSA(){
            Query query = new Query();
            query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").exists(true));
            query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
            List<User> users = mongoTemplate.find(query, User.class);
            return users;
    }

}
