package silver.api;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Catalog {

    @SerializedName("name")
    String nameCatalog;
    @SerializedName("endpoints")
    List<Endpoint> listEndpoint;

    public Catalog() {
        nameCatalog = "";
        listEndpoint = new ArrayList<>();
    }

    public String getNameCatalog() {
        return nameCatalog;
    }

    public void setNameCatalog(String nameCatalog) {
        this.nameCatalog = nameCatalog;
    }

    public List<Endpoint> getListEndpoint() {
        return listEndpoint;
    }

    public void setListEndpoint(List<Endpoint> listEndpoint) {
        this.listEndpoint = listEndpoint;
    }

    @Override
    public String toString() {
        return "Catalog [nameCatalog=" + nameCatalog + ", listEndpoint=" + listEndpoint + "]";
    }
    

}
