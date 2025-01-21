package com.tech.blog.service.impl;

import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.mapper.TagMapper;
import com.tech.blog.model.dto.request.TagCreateRequest;
import com.tech.blog.model.dto.response.TagResponse;
import com.tech.blog.model.entity.Tag;
import com.tech.blog.repository.TagRepository;
import com.tech.blog.service.interfaces.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponse save(TagCreateRequest request) {

        log.info("Creating tag Name: {}", request.getName());

        Tag tag = tagMapper.toEntity(request);
        Tag savedTag = tagRepository.save(tag);
        return tagMapper.toResponse(savedTag);
    }

    @Override
    public List<TagResponse> fetchAll() {

        log.info("Fetching all tags");

        return tagRepository.findAll()
                .stream()
                .map(tagMapper::toResponse)
                .toList();
    }

    @Override
    public TagResponse findById(Long id) {
        log.info("Fetching tag by id: {}", id);

        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));

        return tagMapper.toResponse(tag);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting tag by id: {}", id);

        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
        tagRepository.delete(tag);
    }
}
