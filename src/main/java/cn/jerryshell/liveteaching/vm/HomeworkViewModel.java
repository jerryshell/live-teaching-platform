package cn.jerryshell.liveteaching.vm;

import cn.jerryshell.liveteaching.model.Homework;
import cn.jerryshell.liveteaching.model.Student;
import cn.jerryshell.liveteaching.model.Video;
import cn.jerryshell.liveteaching.service.StudentService;
import cn.jerryshell.liveteaching.service.VideoService;

import java.util.ArrayList;
import java.util.List;

public class HomeworkViewModel {
    private String id;
    private Student student;
    private Video video;
    private Double score;
    private String comment;
    private String fileType;

    public static HomeworkViewModel loadFromHomework(
            Homework homework,
            StudentService studentService,
            VideoService videoService
    ) {
        HomeworkViewModel homeworkVM = new HomeworkViewModel();
        homeworkVM.setId(homework.getId());
        homeworkVM.setScore(homework.getScore());
        homeworkVM.setComment(homework.getComment());
        homeworkVM.setFileType(homework.getFileType());

        Student student = studentService.findById(homework.getStudentId());
        homeworkVM.setStudent(student);

        Video video = videoService.findById(homework.getVideoId());
        homeworkVM.setVideo(video);

        return homeworkVM;
    }

    public static List<HomeworkViewModel> loadFromHomeworkList(
            List<Homework> homeworkList,
            StudentService studentService,
            VideoService videoService
    ) {
        List<HomeworkViewModel> homeworkVMList = new ArrayList<>(homeworkList.size());
        for (Homework homework : homeworkList) {
            HomeworkViewModel homeworkVM = loadFromHomework(
                    homework,
                    studentService,
                    videoService
            );
            homeworkVMList.add(homeworkVM);
        }
        return homeworkVMList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
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
