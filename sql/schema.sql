-- Job Match SA — Database Schema
-- Run this against a fresh PostgreSQL database before running the app.
-- Column names/types below are derived directly from the DAO classes
-- (UserDAO, CandidateProfileDAO, JobDAO) so they match what the app expects.

CREATE TABLE IF NOT EXISTS users (
                                     id            SERIAL PRIMARY KEY,
                                     full_name     VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL  -- BCrypt hash, never plaintext
    );

CREATE TABLE IF NOT EXISTS candidate_profiles (
                                                  id                SERIAL PRIMARY KEY,
                                                  user_id           INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    location          VARCHAR(255) NOT NULL,
    years_experience  INTEGER NOT NULL DEFAULT 0,
    education_level   VARCHAR(255),
    skills            TEXT NOT NULL,           -- comma-separated, e.g. "Java, SQL, AWS"
    auto_apply        BOOLEAN NOT NULL DEFAULT FALSE,
    notify_email      BOOLEAN NOT NULL DEFAULT FALSE,
    notify_sms        BOOLEAN NOT NULL DEFAULT FALSE,
    notify_job_links  BOOLEAN NOT NULL DEFAULT FALSE,
    phone_number      VARCHAR(20),
    UNIQUE (user_id)  -- one profile per user (see ProfileService.profileExists)
    );

CREATE TABLE IF NOT EXISTS jobs (
                                    id               SERIAL PRIMARY KEY,
                                    title            VARCHAR(255) NOT NULL,
    company          VARCHAR(255) NOT NULL,
    location         VARCHAR(255) NOT NULL,
    description      TEXT,
    required_skills  TEXT NOT NULL,            -- comma-separated, same format as candidate skills
    source           VARCHAR(100),             -- e.g. "csv-ingest", "manual"
    source_url       VARCHAR(500),
    salary_range     VARCHAR(100),
    posted_at        TIMESTAMP NOT NULL DEFAULT now()
    );

-- Not yet wired up to a DAO in the app, but the Application model exists
-- for tracking submitted job applications once the auto-apply feature
-- (see README roadmap) is built.
CREATE TABLE IF NOT EXISTS applications (
                                            id          SERIAL PRIMARY KEY,
                                            user_id     INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    job_id      INTEGER NOT NULL REFERENCES jobs(id) ON DELETE CASCADE,
    status      VARCHAR(50) NOT NULL DEFAULT 'pending',  -- pending, submitted, failed
    method      VARCHAR(50),                             -- auto-form, email, manual
    applied_at  TIMESTAMP NOT NULL DEFAULT now()
    );

-- Sample job data for local testing / demo (optional — delete or replace
-- with your own listings)
INSERT INTO jobs (title, company, location, description, required_skills, source, salary_range)
VALUES
    ('Data Engineer', 'Takealot', 'Cape Town', 'Build and maintain ETL pipelines for e-commerce data.', 'Java, SQL, AWS, Kafka', 'manual', 'R45000-65000'),
    ('Junior Java Developer', 'Discovery', 'Johannesburg', 'Support backend services for insurance platform.', 'Java, Spring, PostgreSQL', 'manual', 'R25000-35000'),
    ('Data Analyst', 'Woolworths', 'Cape Town', 'Analyze retail sales data and build dashboards.', 'SQL, Python, Tableau', 'manual', 'R30000-40000')
    ON CONFLICT DO NOTHING;