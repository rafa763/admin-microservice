//package tech.xserver.adminserver.service.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import tech.xserver.adminserver.entity.CertificationEntity;
//import tech.xserver.adminserver.repo.CertificationRepo;
//import tech.xserver.adminserver.service.CertificationService;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@Service
//@Slf4j
//public class CertificationServiceImpl implements CertificationService {
//    private final CertificationRepo certificationRepository;
//
//    public CertificationServiceImpl(CertificationRepo certificationRepository) {
//        this.certificationRepository = certificationRepository;
//    }
//    @Override
//    public CertificationEntity createCertification(CertificationEntity certificationEntity) {
//        return certificationRepository.save(certificationEntity);
//    }
//
//    @Override
//    public Optional<CertificationEntity> getCertification(Long id) {
//        return certificationRepository.findById(id);
//    }
//
//    @Override
//    public List<CertificationEntity> getCertifications() {
//        return StreamSupport.stream(certificationRepository.findAll().spliterator(), false).collect(Collectors.toList());
//    }
//
////    @Override
////    public Optional<CertificationEntity> getCertificationByName(String certificationName) {
////        return certificationRepository.findByCertification(certificationName);
////    }
//    @Override
//    public Optional<CertificationEntity> updateCertification(Long id, CertificationEntity certificationEntity) {
//        if (certificationRepository.existsById(id)) {
//            certificationEntity.setId(id);
//            return Optional.of(certificationRepository.save(certificationEntity));
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public void deleteCertification(Long id) {
//        certificationRepository.deleteById(id);
//    }
//}
