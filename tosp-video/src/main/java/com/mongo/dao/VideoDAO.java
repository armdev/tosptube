package com.mongo.dao;

import com.google.gson.Gson;
import com.mongo.models.FileModel;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.bson.Document;
import org.bson.conversions.Bson;

public class VideoDAO {

    private final MongoCollection<Document> collection;

    private final Gson gson = new Gson();

    public VideoDAO(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public FileModel saveVideo(FileModel data, String filepath) {
        UUID resourceId = UUID.randomUUID();
        data.setFileId(resourceId.toString());
        data.setInsertedDate(new Date(System.currentTimeMillis()));
        data.setFilepath(filepath);
        data.setContent(null);
        String json = gson.toJson(data);
        BasicDBObject document = (BasicDBObject) JSON.parse(json);
        collection.insertOne(new Document(document));
        return data;
    }

    public FileModel findById(String videoId) {
        FileModel entity = null;
        Document query = new Document();
        query.put("fileId", videoId);
        for (Document doc : collection.find(query)) {
            entity = gson.fromJson(doc.toJson(), FileModel.class);
        }
        System.out.println("Video to string " + entity.toString());
        return entity;
    }

    public List<FileModel> findAll() {
        final List<FileModel> videoList = new ArrayList<>();
        FileModel entity = null;
        String sort = "insertedDate";
        String order = "desc";
        Bson sortCriteria = new BasicDBObject(sort, "desc".equals(order) ? -1 : 1);
        Document query = new Document();
        for (Document doc : collection.find(query).sort(sortCriteria)) {
            entity = gson.fromJson(doc.toJson(), FileModel.class);
            videoList.add(entity);
        }
        return videoList;
    }

    public List<FileModel> findUserVideos(String userId) {
        final List<FileModel> videoList = new ArrayList<>();
        FileModel entity = null;
        String sort = "insertedDate";
        String order = "desc";
        Bson sortCriteria = new BasicDBObject(sort, "desc".equals(order) ? -1 : 1);
        Document query = new Document();
        query.put("userId", userId);
        for (Document doc : collection.find(query).sort(sortCriteria)) {
            entity = gson.fromJson(doc.toJson(), FileModel.class);
            videoList.add(entity);
        }
        return videoList;
    }

    public FileModel findByUserIdAndVideoId(String videoId, String userId) {
        FileModel entity = null;
        Document query = new Document();
        query.put("fileId", videoId);
        query.put("userId", userId);
        for (Document doc : collection.find(query)) {
            entity = gson.fromJson(doc.toJson(), FileModel.class);
        }
        return entity;
    }

    public Long getCount() {
        Long listCount = 0L;
        try {
            listCount = collection.count();
        } catch (Exception e) {
        }
        return listCount;
    }

}
