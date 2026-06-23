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
}