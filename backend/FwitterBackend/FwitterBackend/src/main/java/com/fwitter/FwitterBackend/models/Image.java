package com.fwitter.FwitterBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "image_name",unique = true)
    private String imageName;

    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_path")
    @JsonIgnore
    private String imagePath;

    @Column(name = "image_url")
    private String imageURL;

    public Image(String name, String contentType, String path, String imageURL) {
        this.imageName = name;
        this.imageType = contentType;
        this.imagePath = path;
        this.imageURL = imageURL;
    }


    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", imageName='" + imageName + '\'' +
                ", imageType='" + imageType + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
