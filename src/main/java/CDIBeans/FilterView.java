/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;
import record.KeepRecord;

/**
 *
 * @author ritesh
 */
@Named(value = "filterView")
@ViewScoped
public class FilterView implements Serializable {

    @Inject
    private CustomerService service;

    private List<OrderedItem> items;

    private List<CustomerIndexBean> filteredItems;

    private List<FilterMeta> filterBy;

    public FilterView() {
//        unauthorizedUser();
    }

    @PostConstruct
    public void init() {
        System.out.println("\n\nFilter view called\n\n");
        setItems(service.getOrderedItems(10));
        filterBy = new ArrayList<>();

//        filterBy.add(FilterMeta.builder()
//                .field("date")
//                .filterValue(new ArrayList<>(Arrays.asList(LocalDate.now().minusDays(28), LocalDate.now().plusDays(28))))
//                .matchMode(MatchMode.BETWEEN)
//                .build());
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);

        OrderedItem item = (OrderedItem) value;
        return item.getName().toLowerCase().contains(filterText)
                || item.getCreatedAt().toString().toLowerCase().contains(filterText);
    }

//    public void toggleGlobalFilter() {
//        setGlobalFilterOnly(!isGlobalFilterOnly());
//    }
    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public void setService(CustomerService service) {
        this.service = service;
    }

    public List<FilterMeta> getFilterBy() {
        return filterBy;
    }

    public List<OrderedItem> getItems() {
        return items;
    }

    public void setItems(List<OrderedItem> items) {
        this.items = items;
    }

    public List<CustomerIndexBean> getFilteredItems1() {
        return filteredItems;
    }

    public void setFilteredItems1(List<CustomerIndexBean> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public void unauthorizedUser() {
        if (KeepRecord.getToken() == null || !KeepRecord.getRoles().contains("Customer")) {
            try {
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect("http://localhost:8080/dropApplication/common/unauthorized.jsf");
            } catch (IOException ex) {
                System.err.println("exception: ");
                ex.printStackTrace();
            }
        } else {
            System.out.println("\n\n\n\nToken exists and role is valid-----\n\n\n\n");
        }
    }
}
