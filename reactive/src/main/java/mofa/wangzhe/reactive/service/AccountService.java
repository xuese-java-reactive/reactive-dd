package mofa.wangzhe.reactive.service;


import mofa.wangzhe.reactive.model.AccountModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

/**
 * @author LD
 */
public interface AccountService {

    /**
     * 保存
     *
     * @param model AccountModel
     * @return Mono<AccountModel>
     */
    Mono<AccountModel> save(AccountModel model);

    /**
     * 删除
     *
     * @param uuid String
     * @return Mono<AccountModel>
     */
    Mono<AccountModel> remove(String uuid);

    /**
     * 修改
     *
     * @param model AccountModel
     * @return Mono<AccountModel>
     */
    Mono<AccountModel> update(AccountModel model);

    /**
     * gen ju pid huo qu
     *
     * @param pageSize int
     * @param pageNum  int
     * @param search   String
     * @return Flux<AccountModel>
     */
    Mono<Tuple2<Long, List<AccountModel>>> page(int pageSize, int pageNum, String search);

    /**
     * 根据id获取
     *
     * @param uuid String
     * @return Mono<AccountModel>
     */
    Mono<AccountModel> one(String uuid);

}
