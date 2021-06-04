
package mofa.wangzhe.reactive.service;


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
     * list
     *
     * @param pageSize int
     * @param pageNum  int
     * @param search   String
     * @return Flux<MenuModel>
     */
    Flux<MenuModel> page(int pageSize, int pageNum, String search);

    /**
     * 总数
     *
     * @param search String
     * @return Mono<Long>
     */
    Mono<Long> count(String search);

}
