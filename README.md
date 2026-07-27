# Job Match SA

> A Java desktop app that matches candidates to jobs based on skills, and
> generates a tailored CV — built as a Data Engineering elective project.

## Demo Video

📺 

## Overview

**Job Match SA** is a JavaFX desktop application that lets a candidate create
a profile, matches that profile against job listings stored in a Postgres
database using a skills-based scoring algorithm, and generates a base CV as
a PDF from the profile data.

The long-term vision (see [Roadmap](#roadmap) below) is a full pipeline that
ingests listings from multiple job boards, cleans and standardizes them, and
layers on auto-apply and notifications. What's built so far is the core
account/profile/matching/CV loop — the foundation the rest is designed to sit on.

## Problem Statement

Job seekers spend hours searching across multiple platforms for relevant
opportunities and often can't tell, at a glance, which skills are actually
missing from their profile for the roles they want. Job Match SA addresses
the second half of that: given a candidate's profile and a set of job
listings, it scores each match and shows exactly which required skills are
present vs. missing, then produces a CV from that same profile data.

## What's Built

| Area | What it does |
|---|---|
| **Accounts** | Registration and login, passwords hashed with BCrypt (`AuthService`, `UserDAO`) |
| **Candidate profiles** | Location, years of experience, education, skills, and notification preferences, with validation (e.g. phone number required if SMS is selected) (`ProfileService`, `CandidateProfileDAO`) |
| **Skill matching** | Compares a candidate's skills against each job's required skills, returns a match score (`matched / required * 100`), plus matched and missing skill lists, sorted by best match (`MatchingService`) |
| **CV generation** | Builds a base CV as a PDF from the candidate's profile using iText (`CvGeneratorService`) |
| **Desktop UI** | JavaFX screens for login, registration, and profile setup (`JobMatchApp`, `ProfileFormController`) |

## Roadmap

These are the pieces described in the original project vision that aren't
built yet. Listed honestly so scope is clear:

- **Job data ingestion** — nothing currently populates the `jobs` table from
  real sources; it's read-only from `JobDAO`'s side. Pulling listings from a
  public jobs API or a CSV source is the next priority, since it's the piece
  that makes this a genuine data engineering project rather than just a
  CRUD app with a matching algorithm.
- Tailored CV per job (keyword-matched), and ATS scoring/feedback
- Auto-apply engine with email fallback
- Email / SMS notification delivery
- Job market insights and skill-gap analytics across all candidates
- Automated tests beyond manual verification

## Architecture (current)

```
JavaFX UI (JobMatchApp, ProfileFormController)
        │
        ▼
Service layer (AuthService, ProfileService, MatchingService, CvGeneratorService)
        │
        ▼
DAO layer (UserDAO, CandidateProfileDAO, JobDAO)
        │
        ▼
PostgreSQL (shared connection via DatabaseConnection, credentials in .env)
```

## Tech Stack

| Category | Tools |
|---|---|
| Language | Java 17 |
| UI | JavaFX |
| Database | PostgreSQL (JDBC) |
| Password hashing | jBCrypt |
| PDF generation | iText 7 |
| Config | dotenv-java (`.env`, excluded from git) |
| Build | Maven |
| Version control | Git & GitHub |

## Getting Started

### Prerequisites

- Java JDK 17+
- Maven
- PostgreSQL
- IntelliJ IDEA (or any IDE with JavaFX support)

### Setup

```bash
git clone https://github.com/KeneilweMametse/JobMatchSA.git
cd JobMatchSA
```

Create a `.env` file in the project root with your database credentials:

```
DB_URL=jdbc:postgresql://localhost:5432/jobmatchsa
DB_USER=your_db_user
DB_PASSWORD=your_db_password
```

Run the schema for `users`, `candidate_profiles`, `applications`, and `jobs`
tables against your Postgres instance, then run `JobMatchApp` from your IDE
(or via the `javafx-maven-plugin`: `mvn javafx:run`).

## Author

**Keneilwe Mametse**

## License

This project is currently unlicensed. A license will be added before public release.
