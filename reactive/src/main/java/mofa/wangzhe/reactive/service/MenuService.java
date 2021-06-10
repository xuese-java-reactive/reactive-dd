package mofa.wangzhe.reactive.service;


import lombok.NonNull;
import mofa.wangzhe.reactive.model.MenuModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */
public interface MenuService {

    /**
     * 保存
     *
     * @param model MenuModel
     * @return Mono<MenuModel>
     */
    Mono<MenuModel> save(MenuModel model);

    /**
     * 删除
     *
     * @param model MenuModel
     * @return Mono<MenuModel>
     */
    Mono<MenuModel> remove(MenuModel model);

    /**
     * 修改
     *
     * @param model MenuModel
     * @return Mono<MenuModel>
     */
    Mono<MenuModel> update(MenuModel model);

    /**
     * gen ju pid huo qu
     *
     * @param pid String
     * @return Flux<MenuModel>
     */
    Flux<MenuModel> findAll(@NonNull String pid);
}
