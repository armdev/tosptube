package com.mongo.dao;

import com.google.gson.Gson;
import com.mongo.models.UserModel;
import com.mongo.utils.CommonUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;

public class UserDAO {

    private final MongoCollection<Document> collection;

    private final Gson gson = new Gson();

    public UserDAO(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public UserModel save(UserModel data) {
        String json = gson.toJson(data);
        BasicDBObject document = (BasicDBObject) JSON.parse(json);
        collection.insertOne(new Document(document));
        return data;
    }

    public UserModel findById(String userId) {
        UserModel entity = new UserModel();
        Document query = new Document();
        query.put("userId", userId);
        for (Document doc : collection.find(query)) {
            entity = gson.fromJson(doc.toJson(), UserModel.class);
        }
        return entity;
    }

    public UserModel findByEmail(String email) {
        UserModel entity = new UserModel();
        Document query = new Document();
        query.put("email", email);
        for (Document doc : collection.find(query)) {
            entity = gson.fromJson(doc.toJson(), UserModel.class);
        }
        return entity;
    }

    public UserModel findUserByUsername(String username) {
        UserModel entity = new UserModel();
        Document query = new Document();
        query.put("username", username);
        for (Document doc : collection.find(query)) {
            entity = gson.fromJson(doc.toJson(), UserModel.class);
        }
        return entity;
    }

    public List<UserModel> findAll() {
        final List<UserModel> userList = new ArrayList<>();
        @SuppressWarnings("UnusedAssignment")
        UserModel entity = null;
        String sort = "registeredDate";
        String order = "desc";
        Bson sortCriteria = new BasicDBObject(sort, "desc".equals(order) ? -1 : 1);
        Document query = new Document();
        for (Document doc : collection.find(query).sort(sortCriteria)) {
            entity = gson.fromJson(doc.toJson(), UserModel.class);
            userList.add(entity);

        }
        return userList;
    }

    public boolean updatePassword(String id, String password) {
        Document document = new Document();
        document.append("$set", new BasicDBObject()
                .append("password", password));
        UpdateResult update = collection.updateOne(new BasicDBObject().append("id", id), document);
        return true;
    }

    public boolean updateProfile(String userId, UserModel entity) {
        try {
            Document document = new Document();
            document.append("$set",
                    new BasicDBObject()
                            .append("email", entity.getEmail())
                            .append("firstname", entity.getFirstname())
                            .append("lastname", entity.getLastname())
                            .append("status", entity.getStatus()));
            UpdateResult update = collection.updateOne(new BasicDBObject().append("id", userId), document);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Long getUsersCount() {
        Long listCount = 0L;
        try {
            listCount = collection.count();
        } catch (Exception e) {
        }
        return listCount;
    }

    public Optional<UserModel> login(String email, String passwd) {
        UserModel entity = null;
        Document query = new Document();
        query.put("email", email.trim());
        query.put("password", CommonUtils.hashPassword(passwd.trim()));
        for (Document doc : collection.find(query)) {
            entity = gson.fromJson(doc.toJson(), UserModel.class);
        }
        return Optional.ofNullable(entity);
    }

    public void remove() {
        collection.drop();
    }

    public boolean checkUsername(String username) {
        boolean retValue = true;
        Document query = new Document();
        query.put("username", username);
        UserModel entity = null;
        for (Document doc : collection.find(query)) {
            entity = gson.fromJson(doc.toJson(), UserModel.class);
        }
        if (entity == null) {
            return false;
        }

        return retValue;
    }

    public boolean checkEmail(String email) {
        boolean retValue = true;
        Document query = new Document();
        query.put("email", email);
        UserModel entity = null;
        for (Document doc : collection.find(query)) {
            entity = gson.fromJson(doc.toJson(), UserModel.class);
        }
        if (entity == null) {
            return false;
        }

        return retValue;
    }
}
