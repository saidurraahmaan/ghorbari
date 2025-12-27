package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.AnnouncementDto;
import com.s4r.ghorbari.core.entity.Announcement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IAnnouncementService {

    void createAnnouncement(AnnouncementDto dto);

    List<AnnouncementDto> getAllAnnouncements();

    Optional<AnnouncementDto> getAnnouncementById(Long id);

    List<AnnouncementDto> getAnnouncementsByType(Announcement.AnnouncementType type);

    List<AnnouncementDto> getAnnouncementsByPriority(Announcement.Priority priority);

    List<AnnouncementDto> getPublishedAnnouncements();

    List<AnnouncementDto> getPinnedAnnouncements();

    List<AnnouncementDto> getActiveAnnouncements();

    List<AnnouncementDto> getAnnouncementsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    void updateAnnouncement(Long id, AnnouncementDto dto);

    void publishAnnouncement(Long id);

    void unpublishAnnouncement(Long id);

    void pinAnnouncement(Long id);

    void unpinAnnouncement(Long id);

    void deleteAnnouncement(Long id);
}
