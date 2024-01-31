package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.AssetInstanceReviewsService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.viewsContext.implementations.AssetInstanceSort;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.webapp.dto.AssetInstanceReviewDTO;
import ar.edu.itba.paw.webapp.dto.AssetsInstancesDTO;
import ar.edu.itba.paw.webapp.form.AssetInstanceForm;
import ar.edu.itba.paw.webapp.form.AssetInstancePatchForm;
import ar.edu.itba.paw.webapp.form.AssetInstanceReviewForm;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import ar.edu.itba.paw.webapp.miscellaneous.PaginatedData;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Path(EndpointsUrl.AssetInstances_URL)
public class AssetInstanceController {


    private final AssetInstanceService ais;



    private final AssetInstanceReviewsService air;

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetInstanceController.class);

    @Context
    private UriInfo uriInfo;

    @Autowired
    public AssetInstanceController(final AssetInstanceService ais,final AssetInstanceReviewsService air) {
        this.ais = ais;
        this.air = air;
    }

    @GET
    @Path("/{id}")
    @Produces(value = {Vnd.VND_ASSET_INSTANCE})
    public Response getUserAssetsInstances(@PathParam("id") final int id) throws AssetInstanceNotFoundException {
        final AssetInstance assetInstance = ais.getAssetInstance(id);
        LOGGER.info("GET assetInstances/{}",id);
        AssetsInstancesDTO assetDTO = AssetsInstancesDTO.fromAssetInstance(uriInfo,assetInstance);
        Response.ResponseBuilder response = Response.ok(assetDTO);
        return response.build();
    }




    @GET
    @Produces(value = {Vnd.VND_ASSET_INSTANCE})
    @PreAuthorize("@preAuthorizeFunctions.searchPrivateAssetInstances(#userId,#status)")
    public Response getAssetsInstances( @QueryParam("search")  @Size(min = 1, max = 100) String search,
                                         @QueryParam("physicalConditions")  List<String> physicalConditions,
                                         @QueryParam("languages")  List<String> languages,
                                         @QueryParam("sort") @Pattern(regexp = "AUTHOR_NAME|TITLE|RECENT|LANGUAGE|STATE|PHYSICAL_CONDITION",message = "{pattern.sort.AssetInstance}") String sort,
                                         @QueryParam("sortDirection") @Pattern(regexp = "ASCENDING|DESCENDING",message = "{pattern.SortDirection}") String sortDirection,
                                         @QueryParam("page")  @DefaultValue("1")  @Min(1) int currentPage,
                                         @QueryParam("status") @DefaultValue("PUBLIC") @Pattern(regexp = "PUBLIC|PRIVATE|ALL",message = "{pattern.status}") String status,
                                         @QueryParam("minRating")  @DefaultValue("1")@Min(1) @Max(5) int minRating,
                                         @QueryParam("maxRating")  @DefaultValue("5") @Min(1) @Max(5)int maxRating,
                                         @QueryParam("itemsPerPage") @DefaultValue("10") int itemsPerPage,
                                        @QueryParam("userId")  @DefaultValue("-1") int userId)  {
        AbstractPage<AssetInstance> page = ais.getAllAssetsInstances(
                currentPage, itemsPerPage,
                new SearchQueryImpl(
                        (languages != null) ? languages : new ArrayList<>(),
                        (physicalConditions != null) ? physicalConditions : new ArrayList<>(),
                        (search != null) ? search : "",
                        (sort != null) ? AssetInstanceSort.fromString(sort) : AssetInstanceSort.RECENT,
                        (sortDirection != null) ? SortDirection.fromString(sortDirection) : SortDirection.DESCENDING,
                        minRating,
                        maxRating,
                        userId,
                        AssetState.fromString(status)
                        )
        );
        List<AssetsInstancesDTO> assetsInstancesDTO = AssetsInstancesDTO.fromAssetInstanceList(uriInfo, page.getList());
        LOGGER.info("GET assetInstances/ search:{} physicalConditions:{} languages:{} sort:{} sortDirection:{} page:{} itemsPerPage:{} minRating:{} maxRating:{} userId:{}",search,physicalConditions,languages,sort,sortDirection,currentPage,itemsPerPage,minRating,maxRating,userId);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AssetsInstancesDTO>>(assetsInstancesDTO) {});
        PaginatedData.paginatedData(response, page, uriInfo);
        return response.build();
    }

    @POST
    @Consumes(Vnd.VND_ASSET_INSTANCE)
    @Produces(value = {Vnd.VND_ASSET_INSTANCE})
    public Response createAssetInstance(@Valid @NotNull final AssetInstanceForm assetInstanceForm) throws UserNotFoundException, LocationNotExistException, AssetNotExistException, ImageNotExistException, UserIsNotOwnerException {
        AssetInstance assetInstance = ais.addAssetInstance(PhysicalCondition.fromString(assetInstanceForm.getPhysicalCondition()),assetInstanceForm.getDescription(),assetInstanceForm.getMaxDays(),assetInstanceForm.getIsReservable(), AssetState.fromString(assetInstanceForm.getStatus()),assetInstanceForm.getLocationId(),assetInstanceForm.getAssetId(),assetInstanceForm.getImageId());
        LOGGER.info("POST assetInstances/ id:{}",assetInstance.getId());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(assetInstance.getId())).build();
        return Response.created(uri).entity(AssetsInstancesDTO.fromAssetInstance(uriInfo,assetInstance)).build();
    }

    @PATCH
    @Consumes(Vnd.VND_ASSET_INSTANCE)
    @Produces(value = {Vnd.VND_ASSET_INSTANCE})
    @Path("/{id}")
    public Response updateAssetInstance(@PathParam("id") final int id, @Valid @NotNull final AssetInstancePatchForm assetInstancePatchForm) throws AssetInstanceNotFoundException, LocationNotExistException, ImageNotExistException, UserNotFoundException, UserIsNotOwnerException {
        ais.changeAssetInstance(id, Optional.ofNullable(assetInstancePatchForm.getPhysicalCondition()!= null? PhysicalCondition.fromString(assetInstancePatchForm.getPhysicalCondition()):null), Optional.ofNullable(assetInstancePatchForm.getMaxDays()), Optional.ofNullable(assetInstancePatchForm.getLocationId()), Optional.ofNullable(assetInstancePatchForm.getImageId()), Optional.ofNullable(assetInstancePatchForm.getDescription()), Optional.ofNullable(assetInstancePatchForm.getIsReservable()), Optional.ofNullable(assetInstancePatchForm.getStatus()));
        LOGGER.info("PATCH assetInstances/ id:{}",id);
        return Response.noContent().build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteAssetInstance(@PathParam("id") final int id) throws AssetInstanceNotFoundException, UnableToDeleteAssetInstanceException {
        ais.removeAssetInstance(id);
        LOGGER.info("DELETE assetInstances/ id:{}",id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(value ={Vnd.VND_ASSET_INSTANCE_REVIEW})
    public Response getAssetInstanceReviews(@PathParam("id") final int id, @QueryParam("page")  @DefaultValue("1") final int page, @QueryParam("itemsPerPage") @DefaultValue("4") final int itemsPerPage) throws AssetInstanceNotFoundException {
        PagingImpl<AssetInstanceReview> reviews = air.getAssetInstanceReviewsById(page, itemsPerPage,id);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AssetInstanceReviewDTO>>(AssetInstanceReviewDTO.fromAssetInstanceReviews(reviews.getList(),uriInfo)) {});
        PaginatedData.paginatedData(response, reviews, uriInfo);
        LOGGER.info("GET assetInstances/{}/reviews page:{} itemsPerPage:{}",id,page,itemsPerPage);
        return response.build();
    }
    @POST
    @Path("/{id}/reviews")
    @PreAuthorize("@preAuthorizeFunctions.borrowerCanAssetInstanceReview(#id,#assetInstanceReviewForm)")
    @Consumes(value = {Vnd.VND_ASSET_INSTANCE_REVIEW})
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_REVIEW})
    public Response createAssetInstanceReview(@PathParam("id") final int id, @Valid @RequestBody final AssetInstanceReviewForm assetInstanceReviewForm) throws UserNotFoundException, UnableToAddReviewException {
        AssetInstanceReview review = air.addReview(id,assetInstanceReviewForm.getLendingId(),assetInstanceReviewForm.getReview(),assetInstanceReviewForm.getRating());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(review.getId())).build();
        LOGGER.info("POST assetInstances/{}/reviews id:{}",id,review.getId());
        return Response.created(uri).entity(AssetInstanceReviewDTO.fromAssetInstanceReview(review,uriInfo)).build();
    }

    @GET
    @Path("/{id}/reviews/{idReview}")
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_REVIEW})
    public Response getAssetInstanceReview(@PathParam("id") final int id, @PathParam("idReview") final int idReview) throws  AssetInstanceReviewNotFoundException {
        AssetInstanceReview review = air.getReviewById(idReview);
        LOGGER.info("GET assetInstances/{}/reviews/{}",id,idReview);
        return Response.ok(AssetInstanceReviewDTO.fromAssetInstanceReview(review,uriInfo)).build();
    }
    @DELETE
    @Path("/{id}/reviews/{idReview}")
    public Response deleteReviewById(@PathParam("id") final int id, @PathParam("idReview") final int idReview) throws AssetInstanceReviewNotFoundException {
        air.deleteReviewById(idReview);
        LOGGER.info("DELETE assetInstances/{}/reviews/{}",id,idReview);
        return Response.noContent().build();
    }
}
