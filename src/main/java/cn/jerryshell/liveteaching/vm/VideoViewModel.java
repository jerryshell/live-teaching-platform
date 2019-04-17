package cn.jerryshell.liveteaching.vm;

import cn.jerryshell.liveteaching.dao.CourseDao;
import cn.jerryshell.liveteaching.dao.TeacherDao;
import cn.jerryshell.liveteaching.model.Course;
import cn.jerryshell.liveteaching.model.Teacher;
import cn.jerryshell.liveteaching.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoViewModel {
    private String id;
    private Teacher teacher;
    private Course course;
    private String name;
    private String grade;
    private String fileType;

    public static VideoViewModel loadFromVideo(
            Video video,
            TeacherDao teacherDao,
            CourseDao courseDao
    ) {
        VideoViewModel videoViewModel = new VideoViewModel();
        videoViewModel.setId(video.getId());
        videoViewModel.setName(video.getName());
        videoViewModel.setGrade(video.getGrade());
        videoViewModel.setFileType(video.getFileType());

        Teacher teacher = teacherDao.findById(video.getTeacherId()).orElse(null);
        videoViewModel.setTeacher(teacher);

        Course course = courseDao.findById(video.getCourseId()).orElse(null);
        videoViewModel.setCourse(course);
        return videoViewModel;
    }

    public static List<VideoViewModel> loadFromVideoList(List<Video> videoList, TeacherDao teacherDao, CourseDao courseDao) {
        List<VideoViewModel> videoViewModelList = new ArrayList<>(videoList.size());
        for (Video video : videoList) {
            VideoViewModel videoVM = VideoViewModel.loadFromVideo(video, teacherDao, courseDao);
            videoViewModelList.add(videoVM);
        }
        return videoViewModelList;
    }

    @Override
    public String toString() {
        return "VideoViewModel{" +
                "id='" + id + '\'' +
                ", teacher=" + teacher +
                ", course=" + course +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
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
