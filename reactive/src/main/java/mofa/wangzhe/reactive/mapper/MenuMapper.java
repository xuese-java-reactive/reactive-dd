package mofa.wangzhe.reactive.mapper;

import mofa.wangzhe.reactive.model.MenuModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author LD
 */

public interface MenuMapper extends ReactiveCrudRepository<MenuModel, String> {
}
