package com.fwitter.FwitterBackend.repositories;

import com.fwitter.FwitterBackend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByImageName(String imageName);

}
