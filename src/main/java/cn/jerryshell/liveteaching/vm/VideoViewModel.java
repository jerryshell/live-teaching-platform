package cn.jerryshell.liveteaching.vm;

import cn.jerryshell.liveteaching.model.Course;
import cn.jerryshell.liveteaching.model.Teacher;
import cn.jerryshell.liveteaching.model.Video;
import cn.jerryshell.liveteaching.model.VideoMaterial;
import cn.jerryshell.liveteaching.service.CourseService;
import cn.jerryshell.liveteaching.service.TeacherService;
import cn.jerryshell.liveteaching.service.VideoMaterialService;

import java.util.ArrayList;
import java.util.List;

public class VideoViewModel {
    private String id;
    private Teacher teacher;
    private Course course;
    private String name;
    private String grade;
    private String fileType;
    private VideoMaterial videoMaterial;

    public static VideoViewModel loadFromVideo(
            Video video,
            TeacherService teacherDao,
            CourseService courseService,
            VideoMaterialService videoMaterialService
    ) {
        VideoViewModel videoVM = new VideoViewModel();
        videoVM.setId(video.getId());
        videoVM.setName(video.getName());
        videoVM.setGrade(video.getGrade());
        videoVM.setFileType(video.getFileType());

        Teacher teacher = teacherDao.findById(video.getTeacherId());
        videoVM.setTeacher(teacher);

        Course course = courseService.findById(video.getCourseId());
        videoVM.setCourse(course);

        VideoMaterial videoMaterial = videoMaterialService.findByVideoId(video.getId());
        videoVM.setVideoMaterial(videoMaterial);

        return videoVM;
    }

    public static List<VideoViewModel> loadFromVideoList(
            List<Video> videoList,
            TeacherService teacherService,
            CourseService courseService,
            VideoMaterialService videoMaterialService
    ) {
        List<VideoViewModel> videoViewModelList = new ArrayList<>(videoList.size());
        for (Video video : videoList) {
            VideoViewModel videoVM = VideoViewModel.loadFromVideo(
                    video,
                    teacherService,
                    courseService,
                    videoMaterialService
            );
            videoViewModelList.add(videoVM);
        }
        return videoViewModelList;
    }

    public VideoMaterial getVideoMaterial() {
        return videoMaterial;
    }

    public void setVideoMaterial(VideoMaterial videoMaterial) {
        this.videoMaterial = videoMaterial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
