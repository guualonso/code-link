package com.codelink.repository;

import com.codelink.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByDataPostagemDesc(Pageable pageable);
    Page<Post> findByComunidadeIdOrderByDataPostagemDesc(Long comunidadeId, Pageable pageable);
    Page<Post> findByAutorIdOrderByDataPostagemDesc(Long autorId, Pageable pageable);
}
