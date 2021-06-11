package mofa.wangzhe.reactive.service;


import mofa.wangzhe.reactive.model.JurModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */
public interface JurService {

    /**
     * 保存
     *
     * @param model JurModel
     * @return Mono<Void>
     */
    Mono<Integer> save(JurModel model);

    /**
     * 删除
     *
     * @param uuid String
     * @return Mono<JurModel>
     */
    Mono<JurModel> remove(String uuid);

    /**
     * 修改
     *
     * @param model JurModel
     * @return Mono<JurModel>
     */
    Mono<JurModel> update(JurModel model);

    /**
     * list
     *
     * @param accId String
     * @return Mono<List < JurModel>>
     */
    Flux<JurModel> list(String accId);

    /**
     * 根据id获取
     *
     * @param uuid String
     * @return Mono<JurModel>
     */
    Mono<JurModel> one(String uuid);

}
