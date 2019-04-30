package cn.jerryshell.liveteaching.vm;

import cn.jerryshell.liveteaching.model.*;
import cn.jerryshell.liveteaching.service.CourseService;
import cn.jerryshell.liveteaching.service.LiveMaterialService;
import cn.jerryshell.liveteaching.service.MajorService;
import cn.jerryshell.liveteaching.service.TeacherService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LiveViewModel {
    private String id;
    private Teacher teacher;
    private Course course;
    private Major major;
    private String name;
    private String grade;
    private Date date;
    private String startTime;
    private String length;
    private String pushUrl; // 推流地址
    private LiveMaterial liveMaterial;

    public static LiveViewModel loadFromLive(
            String liveServerIp,
            Live live,
            TeacherService teacherService,
            CourseService courseService,
            MajorService majorService,
            LiveMaterialService liveMaterialService
    ) {
        LiveViewModel liveVM = new LiveViewModel();

        liveVM.setId(live.getId());
        liveVM.setName(live.getName());
        liveVM.setGrade(live.getGrade());
        liveVM.setDate(live.getDate());
        liveVM.setStartTime(live.getStartTime());
        liveVM.setLength(live.getLength());

        Teacher teacher = teacherService.findById(live.getTeacherId());
        liveVM.setTeacher(teacher);

        liveVM.setPushUrl("rtmp://" + liveServerIp + "/live/" + teacher.getId());

        Course course = courseService.findById(live.getCourseId());
        liveVM.setCourse(course);

        Major major = majorService.findById(live.getMajorId());
        liveVM.setMajor(major);

        LiveMaterial liveMaterial = liveMaterialService.findByLiveId(live.getId());
        liveVM.setLiveMaterial(liveMaterial);

        return liveVM;
    }

    public static List<LiveViewModel> loadFromLiveList(
            String liveServerIp,
            List<Live> liveList,
            TeacherService teacherService,
            CourseService courseService,
            MajorService majorService,
            LiveMaterialService liveMaterialService
    ) {
        List<LiveViewModel> liveVMList = new ArrayList<>(liveList.size());
        for (Live live : liveList) {
            LiveViewModel liveVM = LiveViewModel.loadFromLive(
                    liveServerIp,
                    live,
                    teacherService,
                    courseService,
                    majorService,
                    liveMaterialService
            );
            liveVMList.add(liveVM);
        }
        return liveVMList;
    }

    @Override
    public String toString() {
        return "LiveViewModel{" +
                "id='" + id + '\'' +
                ", teacher=" + teacher +
                ", course=" + course +
                ", major=" + major +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", date=" + date +
                ", startTime='" + startTime + '\'' +
                ", length='" + length + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                ", liveMaterial=" + liveMaterial +
                '}';
    }

    public LiveMaterial getLiveMaterial() {
        return liveMaterial;
    }

    public void setLiveMaterial(LiveMaterial liveMaterial) {
        this.liveMaterial = liveMaterial;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
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

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
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
