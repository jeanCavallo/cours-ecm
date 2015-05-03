package fr.cmm.service;

import java.util.Iterator;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import fr.cmm.domain.Receipe;
import fr.cmm.helper.PageQuery;

@Service
public class ReceipeService {
    @Inject
    private MongoCollection receipeCollection;

    public Iterator<Receipe> findByQuery(PageQuery query) {
        return receipeCollection
                .find()
                .skip(query.skip())
                .limit(query.getSize())
                .as(Receipe.class);
    }

    public long countByQuery(PageQuery query) {
        return receipeCollection.count();
    }

    public Iterator<Receipe> findRandom(int count) {
        return receipeCollection.find("{randomLocation: {$near: [#, 0]}}", Math.random()).limit(count).as(Receipe.class);
    }

    public Receipe findById(String id) {
        return receipeCollection.findOne(new ObjectId(id)).as(Receipe.class);
    }

    public void save(Receipe receipe) {
        receipeCollection.save(receipe);
    }
}
