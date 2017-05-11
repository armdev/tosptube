package com.mongo.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mongo.models.SearchKeyModel;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

public class SearchDAO {

    private final MongoCollection<Document> collection;

   // private final Gson gson = new Gson();
    
    private final Gson gson = new GsonBuilder()
   .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

    public SearchDAO(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public SearchKeyModel save(SearchKeyModel data) {
        String json = gson.toJson(data);
        BasicDBObject document = (BasicDBObject) JSON.parse(json);
        collection.insertOne(new Document(document));
        return data;
    }

    public List<SearchKeyModel> getTopSearches() {
        List<SearchKeyModel> list = new ArrayList<>();
        try {

            List<Bson> aggregation = Arrays.asList(
                    Aggregates.group("$searchKey", Accumulators.sum("count", 1)),
                    Aggregates.sort(Sorts.descending("count")),
                    Aggregates.project(Projections.fields(Projections.excludeId(), Projections.computed("searchKey", "$_id"), Projections.include("count"))));
            Iterator<Document> sortedList = collection.aggregate(aggregation).iterator();
            //System.out.println("list hasNext " + sortedList.hasNext());

            while (sortedList.hasNext()) {
                Document doc = sortedList.next();
                SearchKeyModel entity = gson.fromJson(doc.toJson(), SearchKeyModel.class);
                list.add(entity);
            }
            //System.out.println("list size is " + list.size());
        } catch (JsonSyntaxException e) {
        }
        return list;
    }

}
