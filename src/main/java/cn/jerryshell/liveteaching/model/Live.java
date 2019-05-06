package cn.jerryshell.liveteaching.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Live {
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
    @NotNull
    private Date date;
    @NotBlank
    private String startTime;
    @NotBlank
    private String length;


    @Override
    public String toString() {
        return "Live{" +
                "id='" + id + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", majorId='" + majorId + '\'' +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", date=" + date +
                ", startTime='" + startTime + '\'' +
                ", length='" + length + '\'' +
                '}';
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
