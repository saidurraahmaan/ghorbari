package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByType(Announcement.AnnouncementType type);

    List<Announcement> findByPriority(Announcement.Priority priority);

    List<Announcement> findByIsPublished(Boolean isPublished);

    List<Announcement> findByIsPinned(Boolean isPinned);

    @Query("SELECT a FROM Announcement a WHERE a.isPublished = true AND (a.expiresAt IS NULL OR a.expiresAt > :currentTime) ORDER BY a.isPinned DESC, a.publishedAt DESC")
    List<Announcement> findActiveAnnouncements(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT a FROM Announcement a WHERE a.publishedAt BETWEEN :startDate AND :endDate ORDER BY a.publishedAt DESC")
    List<Announcement> findAnnouncementsByDateRange(@Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate);
}
