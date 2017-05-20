package com.mongo.resources;

import com.codahale.metrics.annotation.Timed;
import com.mongo.dao.VideoDAO;
import com.mongo.models.Comment;
import com.mongo.models.VideoModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/video")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class VideoResource {

    private final VideoDAO videoDAO;

    public VideoResource(VideoDAO videoDAO) {
        this.videoDAO = videoDAO;
    }

    @Path("/addcomment")
    @POST
    @Timed
    public Response addComment(@Valid Comment data) {
        data.setInsertedDate(new Date(System.currentTimeMillis()));
        VideoModel model = videoDAO.findById(data.getResourceId());
        if (model != null) {
            List<Comment> commentList = new ArrayList<>();
            commentList.add(data);
            model.getCommentList().addAll(commentList);
            videoDAO.save(model);
        } else {
            System.out.println("####VIDEO NOT FOUND: id:=> " + data.getResourceId());
        }

        return Response.ok().entity("\"Success\"").type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();

    }

    @Path("/create")
    @POST
    @Timed
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response saveVideo(VideoModel data) {
        VideoModel model = videoDAO.findById(data.getVideoId());
        if (model != null && model.getVideoId().equals(data.getVideoId())) {
            System.out.println("Model::::: " + model.toString());
            System.out.println("####VIDEO EXIST not saving");
        }
        if (model == null) {
            videoDAO.save(data);
        }
        return Response.ok().entity("\"Success\"").type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @GET
    @Path("/find/id/{videoId}")
    @Timed
    public Response getVideoByVideoId(@PathParam("videoId") String videoId) {
        VideoModel model = videoDAO.findById(videoId.trim());
        return Response.ok(model).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @Path("/update/count/{videoId}")
    @GET
    @Timed
    public Response updateCount(@PathParam("videoId") String videoId) {
        VideoModel model = videoDAO.findById(videoId.trim());
        if (model != null) {
            model.setViewCount(model.getViewCount() + 1);
            videoDAO.updateCount(model);
        }
        return Response.ok().entity("\"updated\"").type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @GET
    @Path("/fetch/key/{searchKey}")
    public Response findBySearchKey(@PathParam("searchKey") String searchKey) {
        List<VideoModel> videoList = videoDAO.findBySearchKey(searchKey);
        return Response.ok(videoList).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @GET
    @Path("/find/all")
    public Response findAll() {
        System.out.println();
        List<VideoModel> videoList = videoDAO.findAll();
        System.out.println("list size in find all " + videoList.size());
        return Response.ok(videoList).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @GET
    @Path("/find/top")
    public Response findTop() {
        List<VideoModel> videoList = videoDAO.findTop200();
        return Response.ok(videoList).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @GET
    @Path("/find/random")
    public Response findRandom() {
        List<VideoModel> randomList = videoDAO.getRandomVideo();
        Random r = new Random();
        return Response.ok(randomList.get(r.nextInt(randomList.size()))).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @GET
    @Path("/find/random/list")
    public Response findRandomList() {
        List<VideoModel> randomList = videoDAO.getRandomVideo();
        //System.out.println("Random list");
        return Response.ok(randomList).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @GET
    @Path("/fetch/full/{searchKey}")
    public Response fullTextSearch(@PathParam("searchKey") String searchKey) {
        List<VideoModel> videoList = videoDAO.doAdvancedSearch(searchKey);
        return Response.ok(videoList).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    //full text index search
//      db.youtube.createIndex(
//   {
//     title: "text",
//      channelTitle: "text",
//description: "text",
//searchKey: "text"
//    }
//) 
}
