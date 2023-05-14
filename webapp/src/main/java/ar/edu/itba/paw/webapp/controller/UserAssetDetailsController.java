package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.LendingCompletionUnsuccessfulException;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
final public class UserAssetDetailsController {

    private final AssetInstanceService assetInstanceService;
    private final AssetAvailabilityService assetAvailabilityService;

    private final UserAssetInstanceService userAssetInstanceService;

    @Autowired
    public UserAssetDetailsController(final AssetInstanceService assetInstanceService, final AssetAvailabilityService assetAvailabilityService, UserAssetInstanceService userAssetInstanceService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;
        this.userAssetInstanceService = userAssetInstanceService;
    }

    @RequestMapping(value = "/userHomeReturn", method = RequestMethod.GET)
    public ModelAndView returnUserHome() {
        return new ModelAndView("redirect:/userHome");
    }
    @RequestMapping(value = "/lentBookDetails", method = RequestMethod.GET)
    public ModelAndView lentBookDetail(@RequestParam("lendingId") final int lendingId) throws AssetInstanceNotFoundException {
        return new ModelAndView("views/userHomeAssetDetail/userBookDetails")
                    .addObject("asset", userAssetInstanceService.getBorrowedAssetInstance(lendingId))
                    .addObject("table", "lended_books");
    }
    @RequestMapping(value = "/myBookDetails", method = RequestMethod.GET)
    public ModelAndView myBookDetails(@RequestParam("id") final int id) throws AssetInstanceNotFoundException {
        return new ModelAndView("views/userHomeAssetDetail/userBookDetails")
                .addObject("asset", assetInstanceService.getAssetInstance(id))
                .addObject("table", "my_books");
    }
    @RequestMapping(value = "/borrowedBookDetails", method = RequestMethod.GET)
    public ModelAndView borrowedBookDetails(@RequestParam("lendingId") final int lendingId) throws AssetInstanceNotFoundException {
        return new ModelAndView("views/userHomeAssetDetail/userBookDetails")
                .addObject("asset", userAssetInstanceService.getBorrowedAssetInstance(lendingId))
                .addObject("table", "borrowed_books");
    }

    @RequestMapping(value ="/deleteAsset/{id}", method = RequestMethod.POST)
    public ModelAndView deleteAsset(@PathVariable("id") final int id) throws AssetInstanceNotFoundException{
        assetInstanceService.removeAssetInstance(id);
        return new ModelAndView("redirect:/userHome");
    }

    @RequestMapping(value ="/returnAsset/{lendingId}", method = RequestMethod.POST)
    public ModelAndView returnAsset(@PathVariable("lendingId") final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        assetAvailabilityService.returnAsset(lendingId);
        return new ModelAndView("redirect:/userHome");
    }
    @RequestMapping(value ="/confirmAsset/{lendingId}", method = RequestMethod.POST)
    public ModelAndView confirmAsset(@PathVariable("lendingId") final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        assetAvailabilityService.confirmAsset(lendingId);
        return new ModelAndView("redirect:/lentBookDetails?lendingId=" + lendingId);
    }

    @RequestMapping(value ="/rejectAsset/{lendingId}", method = RequestMethod.POST)
    public ModelAndView rejectAsset(@PathVariable("lendingId") final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        assetAvailabilityService.rejectAsset(lendingId);
        return new ModelAndView("redirect:/lentBookDetails?lendingId=" + lendingId);
    }

    @RequestMapping(value ="/changeStatus/{id}", method = RequestMethod.POST)
    public ModelAndView changeMyBookStatus(@PathVariable("id") final int id) throws AssetInstanceNotFoundException {
        AssetInstance assetInstance = assetInstanceService.getAssetInstance(id);

        if(assetInstance.getAssetState().isPublic())
            assetAvailabilityService.setAssetPrivate(id);
        else if(assetInstance.getAssetState().isPrivate())
            assetAvailabilityService.setAssetPublic(id);

        return new ModelAndView("redirect:/myBookDetails?id=" + id);
    }

    @ModelAttribute
    public void addAttributes(final Model model) {
        model.addAttribute("path", "userHome");
    }

}