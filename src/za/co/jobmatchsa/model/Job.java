package za.co.jobmatchsa.model;

public class Job {

    private String title;
    private String skills;
    private String location;
    private double salary;

    public Job(String title, String skills, String location, double salary) {
        this.title = title;
        this.skills = skills;
        this.location = location;
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public String getSkills() {
        return skills;
    }

    public String getLocation() {
        return location;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Job{" +
                "title='" + title + '\'' +
                ", skills='" + skills + '\'' +
                ", location='" + location + '\'' +
                ", salary=" + salary +
                '}';
    }
}
