package cn.jerryshell.liveteaching.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class VideoService {
    private List<String> videoNameList = new LinkedList<>();

    public List<String> getVideoNameList() {
        return videoNameList;
    }

    public boolean uploadVideo(MultipartFile uploadFile, String filename) {
        File file = new File("/home/jerry/Videos/" + filename);
        try {
            uploadFile.transferTo(file);
            videoNameList.add(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteVideo(String videoName) {
        videoNameList.remove(videoName);
        File file = new File("/home/jerry/Videos/" + videoName);
        return file.delete();
    }
}
