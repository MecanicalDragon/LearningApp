package net.medrag.microservices.jpa.egraph.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 19.03.2022
 */
@Data
public class PostDto {
    private Long id;
    private String subject;
    private List<CommentDto> comments = new ArrayList<>();
    private UserDto user;
    private List<AttachmentDto> attachments = new ArrayList<>();
}
