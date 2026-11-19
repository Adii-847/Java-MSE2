package com.student.management.model;

import java.util.List;

/**
 * Request payload for updating only student scores and technical skills.
 */
public class UpdateScoresAndSkillsRequest {

    private Double programmingScore;
    private Double mathematicsScore;
    private List<String> technicalSkills;

    public UpdateScoresAndSkillsRequest() {
    }

    public UpdateScoresAndSkillsRequest(Double programmingScore, Double mathematicsScore, List<String> technicalSkills) {
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.technicalSkills = technicalSkills;
    }

    public Double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }
}
