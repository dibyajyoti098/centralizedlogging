package com.log.centralizedlogging.service;

import com.log.centralizedlogging.model.LogRequest;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;

@Service
public class FileWriterService {

    public void sendLogToFile(BufferedWriter fWriter, LogRequest logRequest) {
        try {
            fWriter.write(logRequest.toString());

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

}
