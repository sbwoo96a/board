package com.example.board.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface PostRepositoryV2 extends JpaRepository<Post, Long> {

    Page<Post> findByTitleContaining(String searchKeyword, PageRequest pageRequest);

}
