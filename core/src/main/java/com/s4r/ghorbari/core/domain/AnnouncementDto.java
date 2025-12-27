package com.s4r.ghorbari.core.domain;

import com.s4r.ghorbari.core.entity.Announcement;

import java.time.LocalDateTime;

public class AnnouncementDto {

    private Long id;
    private String title;
    private String content;
    private Announcement.AnnouncementType type;
    private Announcement.Priority priority;
    private LocalDateTime publishedAt;
    private LocalDateTime expiresAt;
    private Boolean isPublished;
    private Boolean isPinned;
    private Long createdById;
    private String targetAudience;
    private String attachmentUrl;

    // Constructors
    public AnnouncementDto() {
    }

    public AnnouncementDto(Announcement announcement) {
        this.id = announcement.getId();
        this.title = announcement.getTitle();
        this.content = announcement.getContent();
        this.type = announcement.getType();
        this.priority = announcement.getPriority();
        this.publishedAt = announcement.getPublishedAt();
        this.expiresAt = announcement.getExpiresAt();
        this.isPublished = announcement.getIsPublished();
        this.isPinned = announcement.getIsPinned();
        this.createdById = announcement.getCreatedById();
        this.targetAudience = announcement.getTargetAudience();
        this.attachmentUrl = announcement.getAttachmentUrl();
    }

    public Announcement toEntity() {
        Announcement announcement = new Announcement();
        announcement.setId(this.id);
        announcement.setTitle(this.title);
        announcement.setContent(this.content);
        announcement.setType(this.type);
        announcement.setPriority(this.priority);
        announcement.setPublishedAt(this.publishedAt);
        announcement.setExpiresAt(this.expiresAt);
        announcement.setIsPublished(this.isPublished);
        announcement.setIsPinned(this.isPinned);
        announcement.setCreatedById(this.createdById);
        announcement.setTargetAudience(this.targetAudience);
        announcement.setAttachmentUrl(this.attachmentUrl);
        return announcement;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Announcement.AnnouncementType getType() {
        return type;
    }

    public void setType(Announcement.AnnouncementType type) {
        this.type = type;
    }

    public Announcement.Priority getPriority() {
        return priority;
    }

    public void setPriority(Announcement.Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Boolean getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Boolean isPinned) {
        this.isPinned = isPinned;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
}
