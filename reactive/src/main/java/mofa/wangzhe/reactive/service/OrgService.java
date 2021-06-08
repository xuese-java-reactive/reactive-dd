
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
     * @param model OrgModel
     * @return Mono<OrgModel>
     */
    Mono<OrgModel> remove(OrgModel model);

    /**
     * 修改
     *
     * @param model OrgModel
     * @return Mono<OrgModel>
     */
    Mono<OrgModel> update(OrgModel model);

    /**
     * gen ju pid huo qu
     *
     * @param pid String
     * @return Flux<OrgModel>
     */
    Flux<OrgModel> findAll(String pid);

}
