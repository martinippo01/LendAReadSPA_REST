package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImagesDao imagesDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);

    @Autowired
    public ImageServiceImpl(final ImagesDao imagesDao) {
        this.imagesDao = imagesDao;
    }


    @Transactional(readOnly = true)
    @Override
    public Image getImage(int id) throws ImageNotFoundException {
        Optional<Image> image = imagesDao.getImage(id);
        image.orElseThrow(ImageNotFoundException::new);
        return image.get();
    }

    @Transactional
    @Override
    public Image addImage(byte[] image) {
        return imagesDao.addPhoto(image);
    }
}
