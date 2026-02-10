package com.springboot.tinyurlspringboot.repositories;
import com.springboot.tinyurlspringboot.model.shortener.Shortener;
import com.springboot.tinyurlspringboot.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShortenerRepository extends JpaRepository<Shortener,Long> {
    Shortener findByOriginalAndUser(String originalUrl,User user);
    Shortener findByShortUrlAndUser(String shortUrl, User user);
    Shortener findByShortUrl(String shortUrl);

    @Query(value = "SELECT nextval('shortener_sequence')", nativeQuery = true)
    Long getNextSeriesId();

    @Modifying
    @Transactional
    @Query("UPDATE Shortener s SET s.clicks = s.clicks + 1 WHERE s.id = :id")
    void incrementClicks(Long id);

    Page<Shortener> findByUser(User user, Pageable pageable);
}