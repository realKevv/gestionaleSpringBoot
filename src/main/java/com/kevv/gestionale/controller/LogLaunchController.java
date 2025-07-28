package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.LogLaunch;
import com.kevv.gestionale.repository.LogLaunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs2")
public class LogLaunchController {

    @Autowired
    private LogLaunchRepository LogLaunchRepo;


    @GetMapping("/types")
    public ResponseEntity<List<String>> getTypes() {
        return ResponseEntity.ok(LogLaunchRepo.findDistinctTypes());
    }

    @GetMapping("/{logid}")
    public ResponseEntity<LogLaunch> getLogLaunchById(@PathVariable Integer logid) {
        Optional<LogLaunch> logLaunchOptional = LogLaunchRepo.findById(logid);
        return logLaunchOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/params")
    public ResponseEntity<List<LogLaunch>> getLogsByParams(
            @RequestParam(required = false)  String startTime,
            @RequestParam(required = false) String applCode,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String webUser,
            @RequestParam(required = false) String fromIp,
            @RequestParam(required = false) String hostUser,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) Integer fromLogid,
            @RequestParam(required = false) String endTime
    ) {
        System.out.println(startTime);

        System.out.println(endTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime start = (startTime != null) ? LocalDateTime.parse(startTime, dtf) :  LocalDateTime.of(1970,1,1,1,1,1);

        LocalDateTime end = (endTime != null) ?  LocalDateTime.parse(endTime, dtf)  : LocalDateTime.of(2100,1,1,1,1,1);

        int fromLog = (fromLogid != null) ? fromLogid : 0;



        System.out.println("startTime = " + start);
        System.out.println("endTime = " + end);




        return ResponseEntity.ok(
                LogLaunchRepo.search(
                        start, end, applCode, type, webUser,
                        fromIp, hostUser, company, year, fromLog
                )
        );
    }
}