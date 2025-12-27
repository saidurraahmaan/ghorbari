package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.context.TenantContext;
import com.s4r.ghorbari.core.domain.AnnouncementDto;
import com.s4r.ghorbari.core.entity.Announcement;
import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import com.s4r.ghorbari.core.repository.AnnouncementRepository;
import com.s4r.ghorbari.core.enums.RoleName;
import com.s4r.ghorbari.core.security.RequiresRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnnouncementService implements IAnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER, RoleName.ROLE_STAFF})
    public void createAnnouncement(AnnouncementDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }
        Announcement announcement = dto.toEntity();
        announcement.setTenantId(tenantId);
        announcementRepository.save(announcement);
    }

    @Override
    public List<AnnouncementDto> getAllAnnouncements() {
        return announcementRepository.findAll().stream()
                .map(AnnouncementDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AnnouncementDto> getAnnouncementById(Long id) {
        return announcementRepository.findById(id)
                .map(AnnouncementDto::new);
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsByType(Announcement.AnnouncementType type) {
        return announcementRepository.findByType(type).stream()
                .map(AnnouncementDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsByPriority(Announcement.Priority priority) {
        return announcementRepository.findByPriority(priority).stream()
                .map(AnnouncementDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getPublishedAnnouncements() {
        return announcementRepository.findByIsPublished(true).stream()
                .map(AnnouncementDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getPinnedAnnouncements() {
        return announcementRepository.findByIsPinned(true).stream()
                .map(AnnouncementDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getActiveAnnouncements() {
        return announcementRepository.findActiveAnnouncements(LocalDateTime.now()).stream()
                .map(AnnouncementDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return announcementRepository.findAnnouncementsByDateRange(startDate, endDate).stream()
                .map(AnnouncementDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER, RoleName.ROLE_STAFF})
    public void updateAnnouncement(Long id, AnnouncementDto dto) {
        Long tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new ServiceException(ErrorCode.TENANT_NOT_FOUND, "Tenant context not set");
        }

        announcementRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Announcement not found"));

        Announcement announcement = dto.toEntity();
        announcement.setId(id);
        announcement.setTenantId(tenantId);
        announcementRepository.save(announcement);
    }

    @Override
    public void publishAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Announcement not found"));

        announcement.setIsPublished(true);
        announcement.setPublishedAt(LocalDateTime.now());
        announcementRepository.save(announcement);
    }

    @Override
    public void unpublishAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Announcement not found"));

        announcement.setIsPublished(false);
        announcementRepository.save(announcement);
    }

    @Override
    public void pinAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Announcement not found"));

        announcement.setIsPinned(true);
        announcementRepository.save(announcement);
    }

    @Override
    public void unpinAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Announcement not found"));

        announcement.setIsPinned(false);
        announcementRepository.save(announcement);
    }

    @Override
    @RequiresRole({RoleName.ROLE_TENANT_ADMIN, RoleName.ROLE_MANAGER})
    public void deleteAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.RESOURCE_NOT_FOUND, "Announcement not found"));

        announcementRepository.delete(announcement);
    }
}
