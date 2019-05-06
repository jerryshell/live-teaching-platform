package cn.jerryshell.liveteaching.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Video {
    @Id
    private String id;
    @NotBlank
    private String teacherId;
    @NotBlank
    private String courseId;
    @NotBlank
    private String majorId;
    @NotBlank
    private String name;
    @NotBlank
    private String grade;
    private String fileType;

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", majorId='" + majorId + '\'' +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
