package com.kevv.gestionale.controller;


import com.kevv.gestionale.model.LogWctxParam;
import com.kevv.gestionale.repository.LogWctxParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/logs")
@CrossOrigin("*")
public class LogWctxParamController {

    @Autowired
    private LogWctxParamRepository logWctxParamRepository;


    @GetMapping("/typeParam")
    public ResponseEntity<List<String>> getLogsByTypeParam() {
        return  ResponseEntity.ok(logWctxParamRepository.findDistinctTypeParam());
    }

    @GetMapping("/action")
        public ResponseEntity<List<String>> getLogsByAction() {
                return ResponseEntity.ok(logWctxParamRepository.findDistinctAction());
}

    @GetMapping("/webctx")
    public ResponseEntity<List<String>> getLogsByWebctx(
    ) {

        return  ResponseEntity.ok(logWctxParamRepository.findDistinctWebctx());
    }



    @PostMapping("/params")
    public ResponseEntity<List<LogWctxParam>> getLogsByParams(
            @RequestParam(required = false) String webctx,
            @RequestParam(required = false) String param,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) Time time
    ) {
        return ResponseEntity.ok(logWctxParamRepository.search(webctx, param, type, action, startDate, endDate, time));
//        return ResponseEntity.ok(logWctxParamRepository.search(webctx, param, type, action, Date, Time));
    }


}
