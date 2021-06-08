
package mofa.wangzhe.reactive.service;


import mofa.wangzhe.reactive.model.TttModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */
public interface TttService {

    /**
     * 保存
     *
     * @param model TttModel
     * @return Mono<TttModel>
     */
    Mono<TttModel> save(TttModel model);

    /**
     * 删除
     *
     * @param uuid String
     * @return Mono<TttModel>
     */
    Mono<TttModel> remove(String uuid);

    /**
     * 修改
     *
     * @param model TttModel
     * @return Mono<TttModel>
     */
    Mono<TttModel> update(TttModel model);

    /**
     * list
     *
     * @param pageSize int
     * @param pageNum  int
     * @param search   String
     * @return Flux<TttModel>
     */
    Flux<TttModel> page(int pageSize, int pageNum, String search);

    /**
     * 总数
     *
     * @param search String
     * @return Mono<Long>
     */
    Mono<Long> count(String search);

    /**
     * 根据id获取
     * @param uuid String
     * @return Mono<TttModel>
     */
    Mono<TttModel> one(String uuid);

}