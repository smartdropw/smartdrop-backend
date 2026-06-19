package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Report entity for SmartDrop platform
 */
@Entity
@Table(name = "Reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportId")
    private Integer reportId;

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "Title", length = 100)
    private String title;

    @Column(name = "Content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "GeneratedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime generatedAt;

    public Report(Integer userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.generatedAt = LocalDateTime.now();
    }
}

