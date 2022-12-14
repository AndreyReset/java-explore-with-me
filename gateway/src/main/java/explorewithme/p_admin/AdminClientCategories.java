package explorewithme.p_admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import explorewithme.lib.client.BaseClient;
import explorewithme.lib.in.CategoryDto;
import explorewithme.lib.in.NewCategoryDto;

@Service
public class AdminClientCategories extends BaseClient {

    @Autowired
    public AdminClientCategories(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(serverUrl, builder, "/");
    }

    public ResponseEntity<Object> updateCategory(CategoryDto dto) {
        return patch("admin/categories", dto);
    }

    public ResponseEntity<Object> createCategory(NewCategoryDto dto) {
        return post("admin/categories", dto);
    }

    public ResponseEntity<Object> deleteCategory(long catId) {
        return delete("admin/categories/" + catId);
    }
}
