package io.project.app.resources;

import io.project.app.domain.YoutubeVideo;
import io.project.app.dto.VideoDTO;
import io.project.app.services.YoutubeSearchService;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/youtube")
@Slf4j
@Api(value = "Search API")
public class SearchController {

    @Autowired
    private YoutubeSearchService youtubeSearchService;

    @GetMapping(path = "/search")
    @ResponseBody
    public ResponseEntity<?> search(
            @RequestParam(value = "search", required = true) String search,
            @RequestParam(value = "items", required = false, defaultValue = "25") String items) {
        
        int max = Integer.parseInt(items);
        
        final List<YoutubeVideo> result = youtubeSearchService.youTubeSearch(search, max);
        
        log.info("Get final result with size " + result.size());
        
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.getVideoList().addAll(result);        
        return ResponseEntity.status(HttpStatus.OK).body(videoDTO);
    }

}
