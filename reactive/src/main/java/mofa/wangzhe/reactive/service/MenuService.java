package mofa.wangzhe.reactive.service;

import mofa.wangzhe.reactive.model.MenuModel;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */
public interface MenuService {

    /**
     * 保存
     *
     * @param model MenuModel
     * @return ResultUtil2
     */
    Mono<MenuModel> save(MenuModel model);
}
