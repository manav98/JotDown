package com.manav.JotDown.repository;

import com.manav.JotDown.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUsersForSA() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$\n"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

/*      Use Or in criteria
        Criteria criteria   = new Criteria();
        criteria.orOperator(
                Criteria.where("email").exists(true),
                Criteria.where("sentimentAnalysis").is(true));*/

        return mongoTemplate.find(query, User.class);
    }
}
