package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.models.userContext.implementations.User;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;

@Getter
@Setter
public class UserDTO {
    private String userName;
    private String email;

    private String image;

    private String password;

    private String telephone;

    private String role;
    private Integer rating;
    private Integer ratingAsLender;
    private Integer ratingAsBorrower;
    private String selfUrl;
    public static UserDTO fromUser(UriInfo url, User user) {
        UserDTO dto = new UserDTO();
        dto.userName = user.getName();
        dto.telephone = user.getTelephone();
        dto.email = user.getEmail();
        dto.role = user.getBehavior().toString();
        dto.rating = user.getRating();
        dto.ratingAsBorrower = user.getRatingAsBorrower();
        dto.ratingAsLender = user.getRatingAsLender();
        dto.selfUrl = reference(url, user);
        dto.image = url.getBaseUriBuilder().path("/api/users").path(String.valueOf(user.getId())).path("image").build().toString();
        return dto;
    }
    public static String reference(UriInfo url, User user) {
        return url.getBaseUriBuilder().path("/api/users").path(String.valueOf(user.getId())).build().toString();
    }

}