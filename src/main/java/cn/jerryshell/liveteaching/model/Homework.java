package cn.jerryshell.liveteaching.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Homework {
    @Id
    private String id;
    @NotBlank
    private String studentId;
    @NotBlank
    private String videoId;
    @NotNull
    private Double score;
    @NotBlank
    private String comment;
    @NotBlank
    private String fileType;

    @Override
    public String toString() {
        return "Homework{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", videoId='" + videoId + '\'' +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
