package com.mongo.resources;

import com.codahale.metrics.annotation.Timed;
import com.mongo.dao.VideoDAO;
import com.mongo.filestorage.FileStorage;
import com.mongo.models.FileModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.binary.Base64;

@Path("/rest")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FileResource {

    private final VideoDAO videoDAO;

    private final String fileStoragePath;

    private FileStorage fs;

    public FileResource(VideoDAO videoDAO, String fileStoragePath) {
        fs = new FileStorage(fileStoragePath);
        this.videoDAO = videoDAO;
        this.fileStoragePath = fileStoragePath;

    }

    @Path("/file/save")
    @POST
    @Timed
    public Response save(@Valid FileModel file) {
        System.out.println("#######Received file " + file.toString());
        if (file != null) {
            try {
                byte[] backToBytes = Base64.decodeBase64(file.getContent());
                System.out.println("storeFile " + backToBytes);
                String filepath = fs.storeFile(file.getTitle().replaceAll("\\s+","-").trim(), backToBytes);
                if (filepath != null) {
                    videoDAO.saveVideo(file, filepath);
                }
            } catch (Exception ex) {
                Logger.getLogger(FileResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Response.ok().entity(file).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/findone/{videoId}")
    public Response findOneVideo(@PathParam("videoId") String videoId) {
        FileModel model = videoDAO.findById(videoId);//file id
        if (model != null) {
            System.out.println(" file path: " + model.getFilepath());
            byte[] content = fs.readFile(model.getFilepath());
            String base64String = Base64.encodeBase64String(content);
            System.out.println(" base64String$$$$$$$$$$$$$$$$$$$ " + base64String);
            model.setContent(base64String);
        }
        if (model == null) {
            System.out.println(" model is null ");
            return Response.serverError().entity("\"failed\"").type(MediaType.APPLICATION_JSON).build();
        }
        return Response.ok().entity(model).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/find/all")
    public Response findAll() {
        List<FileModel> videoList = videoDAO.findAll();
        return Response.ok().entity(videoList).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @GET
    @Path("/find/user/uploaded/{userId}")
    public Response findUserUploadedVideos(@PathParam("userId") String userId) {
        List<FileModel> videoList = videoDAO.findUserVideos(userId);
        return Response.ok().entity(videoList).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @GET
    @Path("/find/user/one/{videoId}/{userId}")
    public Response findOneUserUploadedVideo(@PathParam("videoId") String videoId, @PathParam("userId") String userId) {
        System.out.println(" videoId " + videoId);
        System.out.println(" userId " + userId);
        FileModel model = videoDAO.findByUserIdAndVideoId(videoId, userId);
        if (model != null) {
            System.out.println(" file path: " + model.getFilepath());
            byte[] content = fs.readFile(model.getFilepath());
            String base64String = Base64.encodeBase64String(content);
            model.setContent(base64String);
        }
        if (model == null) {
            System.out.println(" model is null ");
            return Response.serverError().entity("\"failed\"").type(MediaType.APPLICATION_JSON).build();
        }
        return Response.ok().entity(model).type(MediaType.APPLICATION_JSON).build();
    }

}
