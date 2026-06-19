# Job Match SA

> Centralizing South Africa's job market data to connect talent with opportunity.

## Overview

**Job Match SA** is a data engineering project that collects, processes, and analyzes job listings from multiple South African and global job sources to help job seekers find opportunities that match their skills and experience.

The platform handles the full pipeline — ingestion, cleaning, transformation, and storage — before matching candidates to jobs, generating ATS-optimized CVs, applying on their behalf, and notifying them through their preferred channel.

## Problem Statement

Job seekers often spend hours searching across multiple platforms for relevant opportunities, leading to missed listings and wasted time. Many also struggle with CVs that are rejected by Applicant Tracking Systems before a human ever reads them. Job Match SA solves both problems by centralizing job data, generating ATS-passing CVs, automating applications, and identifying skill gaps that affect employability.

## Objectives

- Collect job data from multiple South African and global sources
- Clean and standardize job information across platforms
- Store processed data in a structured database
- Match candidate profiles with relevant job requirements
- Generate ATS-optimized CVs from user profiles or uploaded documents
- Tailor CVs per job application to match job-specific keywords
- Apply to jobs automatically on the candidate's behalf
- Notify candidates via email, SMS, or job links — their choice
- Generate actionable job market insights
- Identify skill gaps for career development

## Features

### User Accounts & Profiles

| Feature | Description | Status |
|---|---|---|
| User registration & login | Secure account creation with email/password | 📋 Planned |
| Candidate profile builder | Name, location, experience, education, skills | 📋 Planned |
| CV upload | Upload existing CV (PDF/Word) for parsing and reformatting | 📋 Planned |
| Auto-apply preference | Choose whether Job Match SA applies on your behalf | 📋 Planned |
| Notification preferences | Choose email, SMS, job links, or all three | 📋 Planned |

### ATS CV Generator

| Feature | Description | Status |
|---|---|---|
| Build from scratch | Generate a CV from profile details the user fills in | 📋 Planned |
| Upload & reformat | Parse an existing CV and restructure it to be ATS-friendly | 📋 Planned |
| Generic base CV | One strong, ATS-optimized CV for general applications | 📋 Planned |
| Tailored CV per job | CV rewritten per application to match job-specific keywords and requirements | 📋 Planned |
| PDF export | Download the generated or tailored CV as a PDF | 📋 Planned |
| ATS score & feedback | Show how well the CV matches a job listing before applying | 📋 Planned |

### Job Matching & Applications

| Feature | Description | Status |
|---|---|---|
| Job data ingestion | Collect listings from PNet, CareerJunction, Indeed SA, LinkedIn, and company career pages | 🚧 In progress |
| Data cleaning & transformation | Normalize and deduplicate job data across sources | 🚧 In progress |
| Job database | Structured storage of all processed listings | 🚧 In progress |
| Skill matching engine | Match candidate skills to job requirements | 📋 Planned |
| Auto-apply engine | Fill and submit job applications automatically; fall back to email if form submission is not possible | 📋 Planned |
| Job link delivery | Send matched job links so candidates can apply themselves | 📋 Planned |

### Notifications & Feedback

| Feature | Description | Status |
|---|---|---|
| Email notifications | Match alerts, application confirmations, and status updates | 📋 Planned |
| SMS notifications | Short match alerts and application confirmations | 📋 Planned |
| Job link delivery | Curated job links sent to the candidate for manual application | 📋 Planned |
| Application feedback | Status updates when employers respond | 📋 Planned |

### Insights & Analytics

| Feature | Description | Status |
|---|---|---|
| Skill gap analysis | Identify missing skills based on matched jobs | 📋 Planned |
| Job market insights | Trending roles, in-demand skills, salary ranges | 📋 Planned |
| Analytics & reporting | Personal application history and match statistics | 📋 Planned |

## User Flow

User registers & creates profile
        ↓
Sets preferences:
  - Auto-apply ON/OFF
  - Notifications: Email | SMS | Job Links | All
        ↓
CV Setup (choose one or both):
  ┌─────────────────────┐     ┌──────────────────────────┐
  │  Build from scratch │     │  Upload existing CV      │
  │  using profile info │     │  → parsed & reformatted  │
  └─────────────────────┘     └──────────────────────────┘
        ↓
ATS CV Generator produces:
  - Generic base CV (PDF)
  - Tailored CV per job (PDF) — keywords matched to each listing
        ↓
Job Match SA ingests listings from:
  PNet · CareerJunction · Indeed SA · LinkedIn · Company Career Pages
        ↓
Data Cleaning & Standardization
        ↓
Skill Matching Engine compares profile to listings
        ↓
        ┌──────────────────────────┐
        │     Auto-Apply ON        │
        │  Tailored CV attached    │
        │  Try form auto-submit    │
        │  → Falls back to email   │
        │    if form unavailable   │
        └──────────────────────────┘
                    +
        ┌──────────────────────────┐
        │   Notification Delivery  │
        │  Email | SMS | Job Links │
        │  (based on user choice)  │
        └──────────────────────────┘
        ↓
Feedback & Skill Gap Insights delivered to user

## Architecture

Job Sources (PNet, CareerJunction, Indeed SA, LinkedIn, Career Pages)
     ↓
Data Ingestion Module
     ↓
Data Cleaning & Transformation Module
     ↓
Data Storage (PostgreSQL / MySQL)
     ↓
Analytics Engine
     ↓
Job Matching Engine
     ↓
ATS CV Generator (base CV + tailored CV per job)
     ↓
Auto-Apply Engine (form submit → email fallback)
     ↓
Notification Service (Email / SMS / Job Links)
     ↓
User Dashboard & Recommendations

## Technologies

| Category | Tools |
|---|---|
| Language | Java |
| IDE | IntelliJ IDEA |
| Version Control | Git & GitHub |
| Data Processing | CSV Processing, OOP |
| Database | PostgreSQL / MySQL |
| CV Parsing | Apache PDFBox / Apache POI |
| PDF Generation | iText / Apache PDFBox |
| Notifications | Email (SMTP / SendGrid) · SMS (Africa's Talking / Twilio) |
| Web Automation | Selenium / Playwright (for auto-apply form submission) |

## Project Status

🚧 **In Development**

### Progress Tracker

- [x] Project planning
- [x] Repository creation
- [ ] Data za.co.jobmatchsa.model design
- [ ] User account & profile module
- [ ] Data ingestion module
- [ ] Data cleaning module
- [ ] Database integration
- [ ] ATS CV generator (build from scratch)
- [ ] ATS CV generator (upload & reformat)
- [ ] Tailored CV engine (per job keyword matching)
- [ ] Skill matching engine
- [ ] Auto-apply engine
- [ ] Notification service (email, SMS, job links)
- [ ] Analytics engine
- [ ] Skill gap analysis
- [ ] Testing
- [ ] Documentation

## Getting Started

> Setup instructions will be added as the project progresses.

### Prerequisites

- Java (JDK 17+ recommended)
- IntelliJ IDEA
- PostgreSQL or MySQL
- Git

### Installation

# Clone the repository
git clone https://github.com/<your-username>/job-match-sa.git

# Open in IntelliJ IDEA and configure your database connection

## Author

**Keneilwe Mametse**

## License

This project is currently unlicensed. A license will be added before public release.
