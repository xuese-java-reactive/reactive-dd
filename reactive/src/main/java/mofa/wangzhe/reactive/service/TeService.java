package mofa.wangzhe.reactive.service;


import mofa.wangzhe.reactive.model.TeModel;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

/**
 * @author LD
 */
public interface TeService {

    /**
     * 保存
     *
     * @param model TeModel
     * @return Mono<TeModel>
     */
    Mono<TeModel> save(TeModel model);

    /**
     * 删除
     *
     * @param uuid String
     * @return Mono<TeModel>
     */
    Mono<TeModel> remove(String uuid);

    /**
     * 修改
     *
     * @param model TeModel
     * @return Mono<TeModel>
     */
    Mono<TeModel> update(TeModel model);

    /**
     * list
     *
     * @param pageSize int
     * @param pageNum  int
     * @param search   String
     * @return Mono<Tuple2 < Long, List < TeModel>>>
     */
    Mono<Tuple2<Long, List<TeModel>>> page(int pageSize, int pageNum, String search);

    /**
     * 根据id获取
     *
     * @param uuid String
     * @return Mono<TeModel>
     */
    Mono<TeModel> one(String uuid);

}
