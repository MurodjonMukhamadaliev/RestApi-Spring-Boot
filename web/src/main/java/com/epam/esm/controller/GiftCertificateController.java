package com.epam.esm.controller;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.GiftUpdatePriceDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.link.LinkProvider;
import com.epam.esm.service.gift_certificate.GiftCertificateService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/certificates")
public class GiftCertificateController {

    private final String LIMIT = "10";
    private final String OFFSET = "0";

    private final GiftCertificateService giftCertificateService;
    private final LinkProvider linkProvider;
    private final ModelMapper modelMapper;

    public GiftCertificateController(GiftCertificateService giftCertificateService, LinkProvider linkProvider, ModelMapper modelMapper) {
        this.giftCertificateService = giftCertificateService;
        this.linkProvider = linkProvider;
        this.modelMapper = modelMapper;
    }

    /**
     * Create Gift Certificate based on the data of GiftCertificateDTO
     *
     * @param giftCertificateDTO - Gift Certificate DTO
     * @return GiftCertificate DTO
     * @throws throws AlreadyExistsException if name already exists in the DB
     */

    @PostMapping
    public ResponseEntity<GiftCertificateDTO> create(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO) {
        GiftCertificateDTO createdGiftCertificate = modelMapper.map(giftCertificateService.create(giftCertificateDTO), GiftCertificateDTO.class);
        linkProvider.addLinkToGiftCertificate(createdGiftCertificate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdGiftCertificate);
    }

    /**
     * Update Gift Certificate with the given id
     *
     * @param id                 - ID
     * @param giftCertificateDTO - Gift Certificate DTO
     * @return GiftCertificate DTO
     * @throws throws AlreadyExistsException if name already exists in the DB or NotFoundException if certificate not found
     */

    @PutMapping(value = "/{id}")
    public ResponseEntity<GiftCertificateDTO> update(@PathVariable UUID id, @RequestBody @Valid GiftCertificateDTO giftCertificateDTO) {
        GiftCertificateDTO updatedGiftCertificate = modelMapper.map(giftCertificateService.update(id, giftCertificateDTO), GiftCertificateDTO.class);
        linkProvider.addLinkToGiftCertificate(updatedGiftCertificate);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedGiftCertificate);
    }

    /**
     * Partially Update Gift Certificate based on the data in GiftCertificateDTO
     *
     * @param dto - price of Gift Certificate
     * @return Gift Certificate DTO
     * @throws throws AlreadyExistsException if name already exists in the DB or NotFoundException if certificate not found
     */

    @PatchMapping(value = "/{id}")
    public ResponseEntity<GiftCertificateDTO> updatePrice(@RequestBody @Valid GiftUpdatePriceDto dto) {
        GiftCertificateDTO updatedGiftCertificate = modelMapper.map(giftCertificateService.updatePrice(dto), GiftCertificateDTO.class);
        linkProvider.addLinkToGiftCertificate(updatedGiftCertificate);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedGiftCertificate);
    }

    /**
     * Delete Gift Certificate with the given id
     *
     * @param id - ID
     * @return Void
     * @throws throws NotFoundException if certificate not found
     */

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        giftCertificateService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * Get one Gift Certificate with the given id
     *
     * @param id - ID
     * @return GiftCertificate DTO
     * @throws throws NotFoundException if certificate not found
     */

    @GetMapping(value = "/{id}")
    public ResponseEntity<GiftCertificateDTO> get(@PathVariable UUID id) {
        GiftCertificateDTO giftCertificate = modelMapper.map(giftCertificateService.get(id), GiftCertificateDTO.class);
        linkProvider.addLinkToGiftCertificate(giftCertificate);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(giftCertificate);
    }

    /**
     * Get Gift Certificates
     *
     * @param name        - name
     * @param description - description
     * @param sort        - sort parameters
     * @param limit       - limit
     * @param offset      - offset
     * @return List of Gift Certificate DTO
     */

    @GetMapping
    public ResponseEntity<List<GiftCertificateDTO>> getCertificates(@RequestParam(required = false) String name,
                                                                    @RequestParam(required = false) String description,
                                                                    @RequestParam(required = false) String sort,
                                                                    @RequestParam(required = false, defaultValue = LIMIT) Integer limit,
                                                                    @RequestParam(required = false, defaultValue = OFFSET) Integer offset) {
        List<GiftCertificate> giftCertificates = giftCertificateService.getAll(name, description, sort, limit, offset);
        List<GiftCertificateDTO> giftCertificateDTOList = giftCertificates.stream().map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDTO.class)).collect(Collectors.toList());
        giftCertificateDTOList.forEach(linkProvider::addLinkToGiftCertificate);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(giftCertificateDTOList);
    }

    /**
     * Get Gift Certificate by several Tags
     *
     * @param tags   - tags
     * @param limit  - limit
     * @param offset - offset
     * @return List of Gift Certificate DTO
     * @throws throws NotFoundException if tag not found
     */

    @GetMapping(value = "/tags")
    public ResponseEntity<List<GiftCertificateDTO>> getCertificatesBySeveralTags(@RequestParam List<String> tags,
                                                                                 @RequestParam(required = false, defaultValue = LIMIT) Integer limit,
                                                                                 @RequestParam(required = false, defaultValue = OFFSET) Integer offset) {
        List<GiftCertificate> giftCertificates = giftCertificateService.getCertificatesBySeveralTags(tags, limit, offset);
        List<GiftCertificateDTO> giftCertificateDTOList = giftCertificates.stream().map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDTO.class)).collect(Collectors.toList());
        giftCertificateDTOList.forEach(linkProvider::addLinkToGiftCertificate);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(giftCertificateDTOList);
    }
}