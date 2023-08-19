package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.mapper.AvatarMapper;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {
    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class.getName());
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final AvatarMapper avatarMapper;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository,
                         AvatarMapper avatarMapper) {
        logger.info("Constructor start ");
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarMapper = avatarMapper;
        logger.info("Constructor the ended with {}", this.avatarRepository,
                this.studentRepository, this.avatarMapper
        );
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile)
            throws IOException {
        logger.info("Method uploadAvatar is started");
        Student student = studentRepository.findById(studentId).orElseThrow();
        Path path = saveToDisk(student,avatarFile);
        saveToDb(student, avatarFile, path);
        logger.info("Method uploadAvatar is ended ");
    }

    public Avatar findAvatar(Long studentId) {
        logger.info("Start method findAvatar ");
        Avatar findingAvatar = avatarRepository.findByStudent_id(studentId).
                orElse(new Avatar());
        logger.info("The end of method findAvatar");
        return findingAvatar;
    }

    private void saveToDb(Student student, MultipartFile avatarFile,
                          Path filePath ) throws IOException {
        logger.info("Start method saveToDb");
        Avatar avatar = findAvatar(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
        logger.info("The end of the method saveToDb");
    }

    private Path saveToDisk(Student student, MultipartFile avatarFile)
            throws IOException {
        logger.info("Start the method saveToDisk");
        Path filePath = Path.of(avatarsDir, student.getId() + "."
                + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        logger.info("The end of the method saveToDisk");
        return filePath;
    }
    private String getExtensions(String fileName) {
        logger.info("Method getExtension start");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<AvatarDTO> getPaginatedAvatars(int pageNumber, int pageSize) {
        logger.info("Method getPaginated start");
        PageRequest pageable = PageRequest.of(pageNumber-1, pageSize);
        return avatarRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(avatarMapper::mapToDTO)
                .collect(Collectors.toList());

    }
}
