package com.vignesh.springbootplayground.controller;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class EchoController {

    @RequestMapping(value = "/echo",method = {RequestMethod.GET,RequestMethod.POST},produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> echo(HttpServletRequest request, @RequestParam(required = false) boolean isError){
        if(isError){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Sample Reason",new Exception("Sample Exception"));
        }

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("method",request.getMethod());
        return map;
    };

    @PostMapping("/square")
    public long square(@RequestBody Long input){
        return input * input;
    }
}
