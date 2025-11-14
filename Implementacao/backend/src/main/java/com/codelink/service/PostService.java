package com.codelink.service;

import com.codelink.dto.PostCreateDTO;
import com.codelink.dto.PostDTO;
import com.codelink.mapper.PostMapper;
import com.codelink.model.Comunidade;
import com.codelink.model.Post;
import com.codelink.model.Usuario;
import com.codelink.repository.ComunidadeRepository;
import com.codelink.repository.PostRepository;
import com.codelink.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunidadeRepository comunidadeRepository;

    @Autowired
    private PostMapper postMapper;

    public PostDTO createPost(PostCreateDTO dto, Long authorId) {
        Usuario autor = usuarioRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado"));

        Post post = postMapper.toEntity(dto);
        post.setAutor(autor);
        post.setDataPostagem(LocalDateTime.now());
        post.setLikes(0);

        if (dto.getComunidadeId() != null) {
            Comunidade comunidade = comunidadeRepository.findById(dto.getComunidadeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comunidade não encontrada"));
            post.setComunidade(comunidade);
        }

        Post saved = postRepository.save(post);
        return postMapper.toDTO(saved);
    }

    public Page<PostDTO> listPosts(Pageable pageable) {
        return postRepository.findAllByOrderByDataPostagemDesc(pageable)
                .map(postMapper::toDTO);
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado"));
        return postMapper.toDTO(post);
    }

    public PostDTO updatePost(Long id, PostCreateDTO dto, Long requesterId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado"));

        if (!post.getAutor().getId().equals(requesterId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas o autor pode editar o post");
        }

        post.setConteudo(dto.getConteudo());
        post.setImagem(dto.getImagem());

        Post updated = postRepository.save(post);
        return postMapper.toDTO(updated);
    }

    public void deletePost(Long id, Long requesterId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado"));

        if (!post.getAutor().getId().equals(requesterId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas o autor pode deletar o post");
        }

        postRepository.delete(post);
    }

    public PostDTO likePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado"));

        post.setLikes(post.getLikes() + 1);
        Post updated = postRepository.save(post);

        return postMapper.toDTO(updated);
    }
}
