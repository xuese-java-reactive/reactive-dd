package mofa.wangzhe.reactive.service;


import mofa.wangzhe.reactive.model.AccountModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
     * @param model AccountModel
     * @return Mono<AccountModel>
     */
    Mono<AccountModel> remove(AccountModel model);

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
    Flux<AccountModel> page(int pageSize, int pageNum, String search);

    /**
     * 统计数量
     *
     * @return Flux<Long>
     */
    Mono<Long> count(String search);

}
