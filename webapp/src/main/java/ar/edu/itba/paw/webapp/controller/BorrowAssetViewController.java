package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

@Controller
public class BorrowAssetViewController {
    private final ViewResolver viewResolver;

    @Autowired
    public BorrowAssetViewController(@Qualifier("viewResolver")final ViewResolver vr){
        this.viewResolver = vr;
    }

    @RequestMapping( "/borrowAssetView")
    public ModelAndView lendView(){
        final ModelAndView mav = new ModelAndView("/views/borrowAssetView");
        return mav;
    }
}
