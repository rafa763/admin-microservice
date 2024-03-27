//package tech.xserver.adminserver.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import tech.xserver.adminserver.DTO.CertificationDto;
//import tech.xserver.adminserver.entity.CertificationEntity;
//import tech.xserver.adminserver.mappers.CertificationMapper;
//import tech.xserver.adminserver.service.CertificationService;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestController
//@RequestMapping("/certifications")
//public class CertificationController {
//     private final CertificationService certificationService;
//     private final CertificationMapper certificationMapper;
//
//     public CertificationController(CertificationService certificationService, CertificationMapper certificationMapper) {
//         this.certificationService = certificationService;
//         this.certificationMapper = certificationMapper;
//     }
//
//    @GetMapping()
//    public ResponseEntity<List<CertificationDto>> listCertifications() {
//        List<CertificationEntity> certificationEntities = certificationService.getCertifications();
//        List<CertificationDto> certificationDtos = certificationEntities.stream().map(certificationMapper::mapToCertificationDto).collect(Collectors.toList());
//        return new ResponseEntity<>(certificationDtos, HttpStatus.OK);
//    }
//
//    @GetMapping(path = "/{id}")
//    public ResponseEntity<CertificationDto> listCertification(@PathVariable Long id) {
//        Optional<CertificationEntity> foundCertification = certificationService.getCertification(id);
//        return foundCertification.map(certificationEntity -> {
//            CertificationDto certificationDto = certificationMapper.mapToCertificationDto(certificationEntity);
//            return new ResponseEntity<>(certificationDto, HttpStatus.OK);
//        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PostMapping()
//    public ResponseEntity<CertificationDto> createCertification(@RequestBody CertificationDto certificationDto) {
//        CertificationEntity certificationEntity = certificationMapper.mapToCertificationEntity(certificationDto);
//        CertificationEntity savedCertificationEntity = certificationService.createCertification(certificationEntity);
//        return new ResponseEntity<>(certificationMapper.mapToCertificationDto(savedCertificationEntity), HttpStatus.CREATED);
//    }
//
//    @PutMapping(path = "/{id}")
//    public ResponseEntity<CertificationDto> updateCertification(@PathVariable Long id, @RequestBody CertificationDto certificationDto) {
//        CertificationEntity certificationEntity = certificationMapper.mapToCertificationEntity(certificationDto);
//        Optional<CertificationEntity> updatedCertification = certificationService.updateCertification(id, certificationEntity);
//        return updatedCertification.map(CE -> {
//            CertificationDto CDTO = certificationMapper.mapToCertificationDto(CE);
//            return new ResponseEntity<>(CDTO, HttpStatus.OK);
//        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
//        certificationService.deleteCertification(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
