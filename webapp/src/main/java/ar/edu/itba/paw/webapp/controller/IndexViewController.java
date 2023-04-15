package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.webapp.form.SnackbarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexViewController {
    private final ViewResolver viewResolver;
    private final AssetInstanceService assetInstanceService;
    private final ImageService imageService;
    @Autowired
    public IndexViewController(@Qualifier("viewResolver")final ViewResolver vr, AssetInstanceService assetInstanceService,ImageService imageService){
        this.viewResolver = vr;
        this.assetInstanceService = assetInstanceService;
        this.imageService = imageService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", "home");
    }
    @RequestMapping( "/")
    public ModelAndView indexView(@RequestParam(required = false,name="showSnackbarSucess") boolean showSnackbarSucess,@RequestParam(required = false,name="snackbarSuccessMessage") String snackbarSuccessMessage){
        final ModelAndView mav = new ModelAndView("/views/index");
        List<AssetInstance> books = assetInstanceService.getAllAssetsInstances();
        mav.addObject("books", books);
        if(showSnackbarSucess)
            SnackbarService.displaySuccess(mav,snackbarSuccessMessage);
        return mav;
    }

    @RequestMapping( "/assetView")
    public ModelAndView assetView(){
        //El objeto ModelAndView nos deja detener el modelo y la view
        final ModelAndView mav = new ModelAndView("/views/assetView");
        return mav;
    }
    @RequestMapping( "/getImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id, HttpServletRequest request){
        byte[] array = imageService.getPhoto(Integer.parseInt(id));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", MediaType.IMAGE_JPEG_VALUE);
        headers.set("Content-Disposition","inline; filename=\"whaterver.jpg\"");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(array);
    }
}