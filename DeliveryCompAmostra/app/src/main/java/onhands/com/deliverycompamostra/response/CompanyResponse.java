package onhands.com.deliverycompamostra.response;

import java.util.List;

import onhands.com.deliverycompamostra.model.Company;
import onhands.com.deliverycompamostra.model.Status;

/**
 * Created by rodrigocavalcante on 5/30/16.
 */
public class CompanyResponse {

    Status status;
    List<Company> companies;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
