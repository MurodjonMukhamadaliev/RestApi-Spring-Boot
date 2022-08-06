package com.epam.esm.controller;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.link.LinkProvider;
import com.epam.esm.service.tag.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



@RestController
@RequestMapping(value = "api/v1/tags")
public class TagController {

    private final String LIMIT = "10";
    private final String OFFSET = "0";
    private final TagService tagService;
    private final LinkProvider linkProvider;
    private final ModelMapper modelMapper;

    public TagController(TagService tagService, LinkProvider linkProvider, ModelMapper modelMapper) {
        this.tagService = tagService;
        this.linkProvider = linkProvider;
        this.modelMapper = modelMapper;
    }

    /**
     * Create Tag based on the data in TagDTO
     *
     * @param tagDTO - Tag DTO
     * @return Tag DTO
     * @exception throws AlreadyExistsException if given name already exists in he DB
     */

    @PostMapping()
    public ResponseEntity<TagDTO> create(@RequestBody @Valid TagDTO tagDTO) {
        TagDTO createdTag = modelMapper.map(tagService.create(tagDTO), TagDTO.class);
        linkProvider.addLinkToTag(createdTag);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTag);
    }

    /**
     * Update Tag with given id  based on the data in TagDTO
     *
     * @param id     - ID
     * @param tagDTO - Tag DTO
     * @return Tag DTO
     * @exception throws AlreadyExistsException if given name already exists in he DB or NotFoundException
     */

    @PutMapping(value = "/{id}")
    public ResponseEntity<TagDTO> update(@PathVariable UUID id, @RequestBody @Valid TagDTO tagDTO) {
        TagDTO updatedTag = modelMapper.map(tagService.update(id, tagDTO), TagDTO.class);
        linkProvider.addLinkToTag(updatedTag);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedTag);
    }

    /**
     * Delete Tag with the given id
     *
     * @param id - ID
     * @return Void
     * @exception throws NotFoundException if tag not found with the given id
     */

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        tagService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * Get one Tag with the given id
     *
     * @param id - ID
     * @return Tag DTO
     * @exception throws NotFoundException if tag not found with the given id
     */

    @GetMapping(value = "/{id}")
    public ResponseEntity<TagDTO> get(@PathVariable UUID id) {
        TagDTO tag = modelMapper.map(tagService.get(id), TagDTO.class);
        linkProvider.addLinkToTag(tag);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tag);
    }

    /**
     * Get all Tags
     *
     * @return List of Tag DTO
     */

    @GetMapping
    public ResponseEntity<List<TagDTO>> getTags(@RequestParam(required = false, defaultValue = LIMIT) Integer limit,
                                                @RequestParam(required = false, defaultValue = OFFSET) Integer offset) {
        List<Tag> tags = tagService.getAll(limit, offset);
        List<TagDTO> tagDTOList = tags.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).collect(Collectors.toList());
        tagDTOList.forEach(linkProvider::addLinkToTag);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagDTOList);
    }

    /**
     * Get Most Used Tag that is used many times while ordering by users
     *
     * @return Tag DTO
     */

    @GetMapping(value = "/get-most-used-tag")
    public ResponseEntity<TagDTO> getMostUsedTag() {
        TagDTO mostUsedTag = modelMapper.map(tagService.getMostUsedTag(), TagDTO.class);
        linkProvider.addLinkToTag(mostUsedTag);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mostUsedTag);
    }

}
