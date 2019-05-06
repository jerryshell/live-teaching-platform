package cn.jerryshell.liveteaching.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Carousel {
    @Id
    private String id;
    @NotBlank
    private String videoId;
    @NotBlank
    private String imageName;

    @Override
    public String toString() {
        return "Carousel{" +
                "id='" + id + '\'' +
                ", videoId='" + videoId + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
