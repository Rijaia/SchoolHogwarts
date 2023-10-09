package ru.hogwarts.school.mapper;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.model.Avatar;

@Component
public class AvatarMapper {

    public AvatarDTO mapToDTO(Avatar avatar) {
        return new AvatarDTO(
                avatar.getId(),
                avatar.getFilePath(),
                avatar.getFileSize(),
                avatar.getMediaType(),
                avatar.getStudent().getId());

    }
}
