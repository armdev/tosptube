package com.mongo.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongo.models.VideoModel;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.bson.Document;
import org.bson.conversions.Bson;

public class VideoDAO {

    private final MongoCollection<Document> collection;

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public VideoDAO(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public VideoModel save(VideoModel data) {
        Date currentDate = new Date();

        UUID resourceId = UUID.randomUUID();
        data.setLastViewed(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate));
        data.setResourceId(resourceId.toString());
        data.setViewCount(1);
        String json = gson.toJson(data);
        BasicDBObject document = (BasicDBObject) JSON.parse(json);
        collection.insertOne(new Document(document));
        return data;
    }

    public boolean updateCount(VideoModel data) {
        Date currentDate = new Date();
        System.out.println(" data.getLastViewed() " + data.getLastViewed());

        System.out.println(" data.getViewCount() " + data.getViewCount());
        Document document = new Document();
        try {
            document.append("$set", new BasicDBObject()
                    .append("lastViewed", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate))
                    .append("viewCount", data.getViewCount()));
            UpdateResult update = collection.updateOne(new BasicDBObject().append("resourceId", data.getResourceId()), document);
            System.out.println("updateCount " + update.wasAcknowledged());
        } catch (Exception e) {
        }
        return true;
    }

    public VideoModel findById(String videoId) {
        VideoModel entity = null;
        Document query = new Document();
        query.put("videoId", videoId);
        for (Document doc : collection.find(query)) {
            System.out.println("doc.toJson(): " + doc.toJson());
            entity = gson.fromJson(doc.toJson(), VideoModel.class);
        }
        // System.out.println("Found: " + entity.toString());
        return entity;
    }

    public List<VideoModel> findBySearchKey(String searchKey) {
        final List<VideoModel> list = new ArrayList<>();
        @SuppressWarnings("UnusedAssignment")
        VideoModel entity = new VideoModel();
        String sort = "viewCount";
        String order = "desc";
        Bson sortCriteria = new BasicDBObject(sort, "desc".equals(order) ? -1 : 1);
        Document query = new Document();
        query.put("searchKey", java.util.regex.Pattern.compile(searchKey));//like
        for (Document doc : collection.find(query).sort(sortCriteria)) {
            entity = gson.fromJson(doc.toJson(), VideoModel.class);
            list.add(entity);
        }
        return list;
    }

    public List<VideoModel> findAll() {
        final List<VideoModel> list = new ArrayList<>();
        @SuppressWarnings("UnusedAssignment")
        VideoModel entity = new VideoModel();
        String sort = "lastViewed";
        String order = "desc";
        Bson sortCriteria = new BasicDBObject(sort, "desc".equals(order) ? -1 : 1);
        Document query = new Document();
        for (Document doc : collection.find(query).sort(sortCriteria)) {
            entity = gson.fromJson(doc.toJson(), VideoModel.class);
            list.add(entity);
        }
        return list;
    }

    public List<VideoModel> findTop200() {
        final List<VideoModel> list = new ArrayList<>();
        @SuppressWarnings("UnusedAssignment")
        VideoModel entity = new VideoModel();
        String sort = "lastViewed";
        String order = "desc";
        Bson sortCriteria = new BasicDBObject(sort, "desc".equals(order) ? -1 : 1);
        Document query = new Document();
        for (Document doc : collection.find(query).sort(sortCriteria).skip(0).limit(200)) {
            entity = gson.fromJson(doc.toJson(), VideoModel.class);
            list.add(entity);
        }
        return list;
    }

    public List<VideoModel> doAdvancedSearch(String searchString) {
        final List<VideoModel> list = new ArrayList<>();
        @SuppressWarnings("UnusedAssignment")
        VideoModel entity = new VideoModel();
        String sort = "viewCount";
        String order = "desc";
        Bson sortCriteria = new BasicDBObject(sort, "desc".equals(order) ? -1 : 1);

        for (Document doc : collection.find(new BasicDBObject("$text", new BasicDBObject("$search", searchString))).sort(sortCriteria).skip(0).limit(500)) {
            entity = gson.fromJson(doc.toJson(), VideoModel.class);
            list.add(entity);
        }
        return list;
    }

    public List<VideoModel> getRandomVideo() {
        List<VideoModel> list = new ArrayList<>();
        VideoModel entity = null;
        String sort = "viewCount";
        String order = "desc";
        Bson sortCriteria = new BasicDBObject(sort, "desc".equals(order) ? -1 : 1);
        for (Document doc : collection.find().sort(sortCriteria).skip(0).limit(500)) {
            entity = gson.fromJson(doc.toJson(), VideoModel.class);
            list.add(entity);
        }
        return list;
    }

    public List<VideoModel> getRandomVideoold() {
        List<VideoModel> list = new ArrayList<>();
        Long count = collection.count();
        Random rand = new Random();
        int limit = rand.nextInt(count.intValue() / (rand.nextInt(10) + 1)) + 1;
        int skip = rand.nextInt(count.intValue() / limit) + 1;
        if (limit == skip) {
            skip = limit + 1;
        }
        if (skip > limit) {
            limit = limit + 1;
        }
        VideoModel entity = null;
        String sort = "viewCount";
        String order = "desc";
        Bson sortCriteria = new BasicDBObject(sort, "desc".equals(order) ? -1 : 1);
        for (Document doc : collection.find().sort(sortCriteria).skip(skip).limit(limit)) {
            entity = gson.fromJson(doc.toJson(), VideoModel.class);
            list.add(entity);
        }
        return list;
    }

    public void remove() {
        collection.drop();
    }

}
