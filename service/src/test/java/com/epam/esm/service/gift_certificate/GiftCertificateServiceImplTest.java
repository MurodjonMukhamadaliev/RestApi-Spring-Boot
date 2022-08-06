package com.epam.esm.service.gift_certificate;

import com.epam.esm.DAO.gift_certificate.GiftCertificateDAOImpl;
import com.epam.esm.DAO.tag.TagDAOImpl;
import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.AlreadyExistsException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.NotValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

import static com.epam.esm.service.utils.GiftCertificateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDAOImpl giftCertificateDAOImpl;

    @Mock
    private TagDAOImpl tagDAOImpl;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateServiceImpl;

    private UUID uuid;
    private GiftCertificate giftCertificate;
    private GiftCertificate updatedGiftCertificate;
    private GiftCertificate updatedPriceOfGiftCertificate;
    private GiftCertificateDTO giftCertificateDTO;
    private GiftCertificateDTO updatedGiftCertificateDTO;

    @BeforeEach
    void setUp() {
        uuid = uuid();
        giftCertificate = giftCertificate();
        updatedGiftCertificate = updatedGiftCertificate();
        updatedPriceOfGiftCertificate = updatedPriceOfGiftCertificate();
        giftCertificateDTO = giftCertificateDTO();
        updatedGiftCertificateDTO = updatedGiftCertificateDTO();
    }

    @Test
    void shouldThrowAlreadyExistsExceptionWhenCreatingGiftCertificateWithExistsName() {
        when(giftCertificateDAOImpl.getByNameAndIdNotEquals("Gift", null)).thenReturn(giftCertificate);
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> giftCertificateServiceImpl.create(giftCertificateDTO));
        assertEquals("Gift Certificate already exists: name = Gift", exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenCreatingGiftCertificate() {
        when(giftCertificateDAOImpl.create(giftCertificate)).thenReturn(giftCertificate);
        when(modelMapper.map(giftCertificateDTO, GiftCertificate.class)).thenReturn(giftCertificate);
        GiftCertificate createdGiftCertificate = giftCertificateServiceImpl.create(giftCertificateDTO);
        assertNotNull(createdGiftCertificate);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUpdatingGiftCertificate() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> giftCertificateServiceImpl.update(uuid, giftCertificateDTO));
        assertEquals("Gift Certificate not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenUpdatingGiftCertificate() {
        when(giftCertificateDAOImpl.get(uuid)).thenReturn(giftCertificate);
        when(giftCertificateDAOImpl.update(any())).thenReturn(updatedGiftCertificate);
        GiftCertificate giftCertificate = giftCertificateServiceImpl.update(uuid, updatedGiftCertificateDTO);
        assertEquals("New Gift", giftCertificate.getName());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUpdatingPriceOfGiftCertificate() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> giftCertificateServiceImpl.updatePrice(uuid, 1.2));
        assertEquals("Gift Certificate not found id = " + uuid, exception.getMessage());
    }

    @Test
        void shouldThrowNotValidExceptionWhenUpdatingPriceOfGiftCertificate() {
        when(giftCertificateDAOImpl.get(uuid)).thenReturn(giftCertificate);
        NotValidException exception = assertThrows(NotValidException.class, () -> giftCertificateServiceImpl.updatePrice(uuid, -1.2));
        assertEquals("Price field must be positive", exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenUpdatingPriceOfGiftCertificate() {
        when(giftCertificateDAOImpl.get(uuid)).thenReturn(giftCertificate);
        when(giftCertificateDAOImpl.update(any())).thenReturn(updatedPriceOfGiftCertificate);
        GiftCertificate giftCertificate = giftCertificateServiceImpl.updatePrice(uuid, 1.2);
        assertEquals(1.2, giftCertificate.getPrice());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenDeletingGiftCertificate() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> giftCertificateServiceImpl.delete(uuid));
        assertEquals("Gift Certificate not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenDeletingGiftCertificate() {
        when(giftCertificateDAOImpl.get(uuid)).thenReturn(giftCertificate);
        doNothing().when(giftCertificateDAOImpl).delete(giftCertificate);
        giftCertificateServiceImpl.delete(uuid);
        when(giftCertificateDAOImpl.get(uuid)).thenReturn(null);
        assertNull(giftCertificateDAOImpl.get(uuid));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingGiftCertificate() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> giftCertificateServiceImpl.get(uuid));
        assertEquals("Gift Certificate not found id = " + uuid, exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenGettingGiftCertificate() {
        when(giftCertificateDAOImpl.get(uuid)).thenReturn(giftCertificate);
        GiftCertificate getGiftCertificate = giftCertificateServiceImpl.get(uuid);
        assertNotNull(getGiftCertificate);
    }

    @Test
    void shouldBeSuccessWhenGettingGiftCertificates() {
        when(giftCertificateDAOImpl.getAll("Gift", null, null, 10, 0)).thenReturn(List.of(giftCertificate));
        List<GiftCertificate> giftCertificates = giftCertificateServiceImpl.getAll("Gift", null, null, 10, 0);
        assertEquals(1, giftCertificates.size());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingGiftCertificatesBySeveralTags() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> giftCertificateServiceImpl.getCertificatesBySeveralTags(List.of("Tag"), 10, 0));
        assertEquals("Tag not found name = Tag", exception.getMessage());
    }

    @Test
    void shouldBeSuccessWhenGettingGiftCertificatesBySeveralTags() {
        when(tagDAOImpl.getByName("Tag")).thenReturn(new Tag("Tag"));
        when(giftCertificateDAOImpl.getCertificatesBySeveralTags(any(), anyInt(), anyInt())).thenReturn(List.of(giftCertificate));
        List<GiftCertificate> giftCertificates = giftCertificateServiceImpl.getCertificatesBySeveralTags(List.of("Tag"), 10, 0);
        assertEquals(1, giftCertificates.size());
    }
}