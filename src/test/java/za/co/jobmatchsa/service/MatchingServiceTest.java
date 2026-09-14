package za.co.jobmatchsa.service;

import za.co.jobmatchsa.dao.JobDAO;
import za.co.jobmatchsa.model.CandidateProfile;
import za.co.jobmatchsa.model.Job;

import java.util.*;
import java.util.stream.Collectors;

public class MatchingService {

    private final JobDAO jobDAO = new JobDAO();

    public static class JobMatch {
        public final Job job;
        public final int matchScore;
        public final List<String> matchedSkills;
        public final List<String> missingSkills;

        public JobMatch(Job job, int matchScore, List<String> matchedSkills, List<String> missingSkills) {
            this.job = job;
            this.matchScore = matchScore;
            this.matchedSkills = matchedSkills;
            this.missingSkills = missingSkills;
        }
    }

    public List<JobMatch> findMatches(CandidateProfile profile) {
        List<String> candidateSkills = parseSkills(profile.getSkills());
        List<Job> allJobs = jobDAO.getAllJobs();
        List<JobMatch> matches = new ArrayList<>();

        for (Job job : allJobs) {
            List<String> requiredSkills = parseSkills(job.getRequiredSkills());

            List<String> matchedSkills = requiredSkills.stream()
                    .filter(candidateSkills::contains)
                    .collect(Collectors.toList());

            List<String> missingSkills = requiredSkills.stream()
                    .filter(skill -> !candidateSkills.contains(skill))
                    .collect(Collectors.toList());

            int score = requiredSkills.isEmpty() ? 0 :
                    (int) Math.round((matchedSkills.size() * 100.0) / requiredSkills.size());

            matches.add(new JobMatch(job, score, matchedSkills, missingSkills));
        }

        matches.sort((a, b) -> b.matchScore - a.matchScore);
        return matches;
    }

    /**
     * Scores a single candidate profile against a single job. Added as a
     * standalone method so the matching logic can be unit tested directly
     * with plain CandidateProfile/Job objects, without touching the database.
     * (findMatches() still has its own inline copy of this logic for now —
     * next commit switches it over to call this instead.)
     */
    public JobMatch matchAgainstJob(CandidateProfile profile, Job job) {
        List<String> candidateSkills = parseSkills(profile.getSkills());
        List<String> requiredSkills = parseSkills(job.getRequiredSkills());

        List<String> matchedSkills = requiredSkills.stream()
                .filter(candidateSkills::contains)
                .collect(Collectors.toList());

        List<String> missingSkills = requiredSkills.stream()
                .filter(skill -> !candidateSkills.contains(skill))
                .collect(Collectors.toList());

        int score = requiredSkills.isEmpty() ? 0 :
                (int) Math.round((matchedSkills.size() * 100.0) / requiredSkills.size());

        return new JobMatch(job, score, matchedSkills, missingSkills);
    }

    private List<String> parseSkills(String skillsString) {
        if (skillsString == null || skillsString.isBlank()) return new ArrayList<>();
        return Arrays.stream(skillsString.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}

