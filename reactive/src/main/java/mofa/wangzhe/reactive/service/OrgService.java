
package mofa.wangzhe.reactive.service;


import mofa.wangzhe.reactive.model.OrgModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */
public interface OrgService {

    /**
     * 保存
     *
     * @param model OrgModel
     * @return Mono<OrgModel>
     */
    Mono<OrgModel> save(OrgModel model);

    /**
     * 删除
     *
     * @param uuid String
     * @return Mono<OrgModel>
     */
    Mono<OrgModel> remove(String uuid);

    /**
     * 修改
     *
     * @param model OrgModel
     * @return Mono<OrgModel>
     */
    Mono<OrgModel> update(OrgModel model);

    /**
     * list
     *
     * @param pageSize int
     * @param pageNum  int
     * @param search   String
     * @return Flux<OrgModel>
     */
    Flux<OrgModel> page(int pageSize, int pageNum, String search);

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
     * @return Mono<OrgModel>
     */
    Mono<OrgModel> one(String uuid);

}
