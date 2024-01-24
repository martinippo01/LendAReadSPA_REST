package ar.edu.itba.paw.webapp.form;


import ar.edu.itba.paw.webapp.form.annotations.interfaces.ParamsChecker;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@Getter @Setter
@ParamsChecker(field = "assetInstanceId",secondField = "borrowerId",thirdField = "lenderId")
public class LendingGetForm {

    @QueryParam("page")
    @DefaultValue("1")
    private Integer page;
    @QueryParam("itemsPerPage")@DefaultValue("4")
    private Integer itemsPerPage;
    @QueryParam("assetInstanceId")
    private Integer assetInstanceId;
    @QueryParam("borrowerId")
    private Integer borrowerId;
    @QueryParam("sort")
    @Pattern(regexp = "TITLE|LENDDATE|DEVOLUTIONDATE|BORROWER_USER|LENDER_USER|STATE",message = "{pattern.lendingSort}")
    private String sort;
    @QueryParam("sortDirection")
    @Pattern(regexp = "ASCENDING|DESCENDING",message = "{pattern.SortDirection}")
    private String sortDirection;
    @QueryParam("state")
    @Pattern(regexp = "DELIVERED|ACTIVE|FINISHED|REJECTED|CANCELED",message = "{lending.state.invalid}")
    private String state;
    @QueryParam("lenderId")
    private Integer lenderId;
}