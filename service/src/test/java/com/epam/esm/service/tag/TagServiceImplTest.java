package com.epam.esm.service.tag;

import com.epam.esm.DAO.tag.TagDAOImpl;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.AlreadyExistsException;
import com.epam.esm.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

import static com.epam.esm.service.utils.TagUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDAOImpl tagDAOImpl;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TagServiceImpl tagServiceImpl;

    private UUID uuid;
    private TagDTO tagDTO;
    private TagDTO updatedTagDTO;
    private Tag tag;
    private Tag updatedTag;

    @BeforeEach
    void setUp() {
        uuid = uuid();
        tagDTO = tagDTO();
        updatedTagDTO = updatedTagDTO();
        tag = tag();
        updatedTag = updatedTag();
    }

    @Test
    void shouldThrowAlreadyExistsExceptionWhenCreatingTag() {
        when(tagDAOImpl.getByName("Tag")).thenReturn(tag);
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> tagServiceImpl.create(tagDTO));
        assertEquals("Tag already exists: name = Tag", exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenCreatingTag() {
        when(tagDAOImpl.create(tag)).thenReturn(tag);
        when(modelMapper.map(tagDTO, Tag.class)).thenReturn(tag);
        Tag createdTag = tagServiceImpl.create(tagDTO);
        assertNotNull(createdTag);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUpdatingTag() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> tagServiceImpl.update(uuid, tagDTO));
        assertEquals("Tag not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenUpdatingTag() {
        when(tagDAOImpl.get(uuid)).thenReturn(tag);
        when(tagDAOImpl.update(any())).thenReturn(updatedTag);
        Tag updatedTag = tagServiceImpl.update(uuid, updatedTagDTO);
        assertEquals("New Tag", updatedTag.getName());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenDeletingTag() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> tagServiceImpl.delete(uuid));
        assertEquals("Tag not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenDeletingTag() {
        when(tagDAOImpl.get(uuid)).thenReturn(tag);
        doNothing().when(tagDAOImpl).delete(tag);
        tagServiceImpl.delete(uuid);
        when(tagDAOImpl.get(uuid)).thenReturn(null);
        assertNull(tagDAOImpl.get(uuid));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingTag() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> tagServiceImpl.get(uuid));
        assertEquals("Tag not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenGettingTag() {
        when(tagDAOImpl.get(uuid)).thenReturn(tag);
        Tag getTag = tagServiceImpl.get(uuid);
        assertNotNull(getTag);
    }

    @Test
    void shouldBeSuccessWhenGettingTags() {
        when(tagDAOImpl.getAll(10, 0)).thenReturn(List.of(tag));
        List<Tag> tags = tagServiceImpl.getAll(10, 0);
        assertEquals(1, tags.size());
    }

    @Test
    void getMostUsedTag() {
        when(tagDAOImpl.getMostUsedTag()).thenReturn(tag);
        Tag getMostUserTag = tagServiceImpl.getMostUsedTag();
        assertNotNull(getMostUserTag);
    }
}