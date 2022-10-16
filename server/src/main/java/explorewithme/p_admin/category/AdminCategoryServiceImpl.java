package explorewithme.p_admin.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import explorewithme.dto.CategoryDto;
import explorewithme.lib.exception.ObjNotFoundException;
import explorewithme.model.Category;
import explorewithme.repository.CategoryRepository;

import static explorewithme.mapper.CategoryMapper.toDto;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository repository;

    @Override
    @Transactional
    public CategoryDto updateCategory(Category category) {
        repository.findById(category.getId())
                .orElseThrow(() -> new ObjNotFoundException("Объект для обновления не найден"));
        return toDto(repository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto createCategory(Category category) {
        return toDto(repository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {
        repository.deleteById(catId);
    }
}
