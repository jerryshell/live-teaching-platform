package cn.jerryshell.liveteaching.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class LiveMaterial {
    @Id
    private String id;
    @NotBlank
    private String liveId;
    @NotBlank
    private String fileType;

    @Override
    public String toString() {
        return "LiveMaterial{" +
                "id='" + id + '\'' +
                ", liveId='" + liveId + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
