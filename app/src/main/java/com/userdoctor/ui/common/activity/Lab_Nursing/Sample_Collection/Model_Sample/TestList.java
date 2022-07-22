
package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("test_name")
    @Expose
    private String testName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("subcategory")
    @Expose
    private List<Subcategory> subcategory = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Subcategory> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(List<Subcategory> subcategory) {
        this.subcategory = subcategory;
    }

}
