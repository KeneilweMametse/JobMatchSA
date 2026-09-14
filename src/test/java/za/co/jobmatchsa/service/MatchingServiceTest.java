package za.co.jobmatchsa.service;

import org.junit.jupiter.api.Test;
import za.co.jobmatchsa.model.CandidateProfile;
import za.co.jobmatchsa.model.Job;

import static org.junit.jupiter.api.Assertions.*;

class MatchingServiceTest {

    private final MatchingService matchingService = new MatchingService();

    private CandidateProfile profileWithSkills(String skills) {
        return new CandidateProfile(1, "Cape Town", 2, "Degree", skills,
                false, true, false, false, null);
    }

    private Job jobRequiring(String requiredSkills) {
        Job job = new Job();
        job.setTitle("Data Engineer");
        job.setCompany("Acme");
        job.setLocation("Cape Town");
        job.setRequiredSkills(requiredSkills);
        return job;
    }

    @Test
    void fullMatchScoresOneHundred() {
        CandidateProfile profile = profileWithSkills("Java, SQL, AWS");
        Job job = jobRequiring("Java, SQL, AWS");

        MatchingService.JobMatch result = matchingService.matchAgainstJob(profile, job);

        assertEquals(100, result.matchScore);
        assertEquals(3, result.matchedSkills.size());
        assertTrue(result.missingSkills.isEmpty());
    }

    @Test
    void partialMatchCalculatesCorrectPercentage() {
        CandidateProfile profile = profileWithSkills("Java, SQL");
        Job job = jobRequiring("Java, SQL, AWS, Kafka"); // 2 of 4 required skills present

        MatchingService.JobMatch result = matchingService.matchAgainstJob(profile, job);

        assertEquals(50, result.matchScore);
        assertEquals(2, result.matchedSkills.size());
        assertEquals(2, result.missingSkills.size());
        assertTrue(result.missingSkills.contains("aws"));
        assertTrue(result.missingSkills.contains("kafka"));
    }

    @Test
    void noOverlapScoresZero() {
        CandidateProfile profile = profileWithSkills("Photoshop, Illustrator");
        Job job = jobRequiring("Java, SQL");

        MatchingService.JobMatch result = matchingService.matchAgainstJob(profile, job);

        assertEquals(0, result.matchScore);
        assertTrue(result.matchedSkills.isEmpty());
        assertEquals(2, result.missingSkills.size());
    }

    @Test
    void jobWithNoRequiredSkillsScoresZeroNotDivideByZeroError() {
        CandidateProfile profile = profileWithSkills("Java, SQL");
        Job job = jobRequiring("");

        MatchingService.JobMatch result = matchingService.matchAgainstJob(profile, job);

        assertEquals(0, result.matchScore);
    }

    @Test
    void matchingIsCaseInsensitiveAndIgnoresWhitespace() {
        CandidateProfile profile = profileWithSkills("  JAVA ,sql,  Aws  ");
        Job job = jobRequiring("java, SQL, aws");

        MatchingService.JobMatch result = matchingService.matchAgainstJob(profile, job);

        assertEquals(100, result.matchScore);
    }

    @Test
    void missingSkillsAreIdentifiedConsistentlyAcrossMultipleJobs() {
        CandidateProfile profile = profileWithSkills("Java");
        Job jobA = jobRequiring("Java, SQL");
        Job jobB = jobRequiring("Java, SQL, AWS");

        MatchingService.JobMatch matchA = matchingService.matchAgainstJob(profile, jobA);
        MatchingService.JobMatch matchB = matchingService.matchAgainstJob(profile, jobB);

        // sql is missing from both jobs, aws only from one
        assertTrue(matchA.missingSkills.contains("sql"));
        assertTrue(matchB.missingSkills.contains("sql"));
        assertTrue(matchB.missingSkills.contains("aws"));
    }
}
