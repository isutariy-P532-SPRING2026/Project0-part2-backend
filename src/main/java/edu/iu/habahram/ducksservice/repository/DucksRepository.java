package edu.iu.habahram.ducksservice.repository;

import edu.iu.habahram.ducksservice.model.DuckData;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DucksRepository {

    private final DuckDataJpaRepository duckDataJpaRepository;

    private final String IMAGES_FOLDER_PATH = "ducks/images/";
    private final String AUDIO_FOLDER_PATH = "ducks/audio/";

    public DucksRepository(DuckDataJpaRepository duckDataJpaRepository) {
        this.duckDataJpaRepository = duckDataJpaRepository;

        File ducksImagesDirectory = new File(IMAGES_FOLDER_PATH);
        if (!ducksImagesDirectory.exists()) {
            ducksImagesDirectory.mkdirs();
        }

        File ducksAudioDirectory = new File(AUDIO_FOLDER_PATH);
        if (!ducksAudioDirectory.exists()) {
            ducksAudioDirectory.mkdirs();
        }
    }

    public int add(DuckData duckData) {
        int maxId = 0;
        List<DuckData> ducks = duckDataJpaRepository.findAll();
        for (DuckData duck : ducks) {
            if (duck.getId() > maxId) {
                maxId = duck.getId();
            }
        }

        int id = maxId + 1;
        DuckData newDuck = new DuckData(id, duckData.getName(), duckData.getType());
        duckDataJpaRepository.save(newDuck);
        return id;
    }

    public boolean updateImage(int id, MultipartFile file) throws IOException {
        String fileExtension = ".png";
        Path path = Paths.get(IMAGES_FOLDER_PATH + id + fileExtension);
        file.transferTo(path);
        return true;
    }

    public boolean updateAudio(int id, MultipartFile file) throws IOException {
        String fileExtension = ".mp3";
        Path path = Paths.get(AUDIO_FOLDER_PATH + id + fileExtension);
        file.transferTo(path);
        return true;
    }

    public byte[] getImage(int id) throws IOException {
        Path path = Paths.get(IMAGES_FOLDER_PATH + id + ".png");
        return Files.readAllBytes(path);
    }

    public byte[] getAudio(int id) throws IOException {
        Path path = Paths.get(AUDIO_FOLDER_PATH + id + ".mp3");
        return Files.readAllBytes(path);
    }

    public List<DuckData> findAll() {
        return duckDataJpaRepository.findAll();
    }

    public DuckData find(int id) {
        return duckDataJpaRepository.findById(id).orElse(null);
    }

    public List<DuckData> search(String type) {
        if (type == null || type.trim().isEmpty()) {
            return duckDataJpaRepository.findAll();
        }
        return duckDataJpaRepository.findByTypeIgnoreCase(type);
    }
}