package explorewithme.p_admin.category;

import explorewithme.dto.CategoryDto;
import explorewithme.model.Category;

public interface AdminCategoryService {

    CategoryDto updateCategory(Category category);

    CategoryDto createCategory(Category category);

    void deleteCategory(long catId);
}
