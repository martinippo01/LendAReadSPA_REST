package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImagesDao imagesDao;

    @Autowired
    public ImageServiceImpl(ImagesDao imagesDao) {
        this.imagesDao = imagesDao;
    }

    @Override
    public byte[] getPhoto(int id) throws ImageNotFoundException {
        Optional<byte[]> image = imagesDao.getPhoto(id);
        if(!image.isPresent())
            throw new ImageNotFoundException("Image not found");
        return image.get();
    }
}
