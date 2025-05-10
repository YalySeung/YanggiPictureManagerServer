package com.yanggi.yanggipicturemanagerserver.model.dto;

import com.yanggi.yanggipicturemanagerserver.model.entity.Photo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter @Setter
public class PhotoResponse extends Photo {
    String fileUrl;
}
