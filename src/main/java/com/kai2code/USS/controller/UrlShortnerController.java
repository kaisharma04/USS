package com.kai2code.USS.controller;

import com.kai2code.USS.service.UrlShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class UrlShortnerController {

    @Autowired
    private UrlShortnerService urlShortnerService;

    @GetMapping("/{id}")
    public ResponseEntity<HttpStatus> redirectToUrl(@PathVariable String id){
        String target_url = urlShortnerService.getUrlByHash(id);

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(target_url))
                .header(HttpHeaders.CONNECTION,"close")
                .build();
    }

    @GetMapping("/retrieveLinkByHash/{id}")
    public String getURl(@PathVariable String id){
        return urlShortnerService.getUrlByHash(id);
    }

    @PostMapping("/shortIt")
    public String createShortUrl(@RequestBody String url){
       return urlShortnerService.ShortThatUrl(url);
    }

}
