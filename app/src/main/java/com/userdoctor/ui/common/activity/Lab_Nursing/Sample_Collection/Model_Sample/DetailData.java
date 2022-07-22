
package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("test_cat_id")
    @Expose
    private String testCatId;
    @SerializedName("subcat_name")
    @Expose
    private String subcatName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestCatId() {
        return testCatId;
    }

    public void setTestCatId(String testCatId) {
        this.testCatId = testCatId;
    }

    public String getSubcatName() {
        return subcatName;
    }

    public void setSubcatName(String subcatName) {
        this.subcatName = subcatName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
