package s3818074_s3818487.cosc2440a2.representations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import s3818074_s3818487.cosc2440a2.models.Category;
import s3818074_s3818487.cosc2440a2.models.Product;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductResponse {

    private Product productDetail;
    private String categoryName;

    public ProductResponse(Product productDetail, Category category) {
        this.productDetail = productDetail;
        this.categoryName = category.getName();
    }

    public Product getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(Product productDetail) {
        this.productDetail = productDetail;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
