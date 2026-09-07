package za.co.jobmatchsa.model;

/**
 * Job represents a job listing sourced into the 'jobs' table.
 */
public class Job {

    private int id;
    private String title;
    private String company;
    private String location;
    private String description;
    private String requiredSkills;   // comma-separated, e.g. "Java,SQL,Python"
    private String source;           // e.g. "Indeed", "manual"
    private String sourceUrl;
    private String salaryRange;      // e.g. "R25,000 - R35,000"

    // Default constructor (used by JobDAO when mapping ResultSet rows)
    public Job() {}

    // Convenience constructor for creating a job manually
    public Job(String title, String company, String location, String description,
               String requiredSkills, String source, String sourceUrl, String salaryRange) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.source = source;
        this.sourceUrl = sourceUrl;
        this.salaryRange = salaryRange;
    }

    // Getters
    public int getId()                 { return id; }
    public String getTitle()           { return title; }
    public String getCompany()         { return company; }
    public String getLocation()        { return location; }
    public String getDescription()     { return description; }
    public String getRequiredSkills()  { return requiredSkills; }
    public String getSource()          { return source; }
    public String getSourceUrl()       { return sourceUrl; }
    public String getSalaryRange()     { return salaryRange; }

    // Setters
    public void setId(int id)                          { this.id = id; }
    public void setTitle(String title)                 { this.title = title; }
    public void setCompany(String company)              { this.company = company; }
    public void setLocation(String location)            { this.location = location; }
    public void setDescription(String description)      { this.description = description; }
    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }
    public void setSource(String source)                { this.source = source; }
    public void setSourceUrl(String sourceUrl)           { this.sourceUrl = sourceUrl; }
    public void setSalaryRange(String salaryRange)       { this.salaryRange = salaryRange; }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", requiredSkills='" + requiredSkills + '\'' +
                ", salaryRange='" + salaryRange + '\'' +
                '}';
    }
}
