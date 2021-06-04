
package mofa.wangzhe.reactive.service;


import mofa.wangzhe.reactive.model.LoginModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */
public interface LoginService {

    /**
     * 保存
     *
     * @param model LoginModel
     * @return Mono<LoginModel>
     */
    Mono<LoginModel> save(LoginModel model);

    /**
     * 删除
     *
     * @param model LoginModel
     * @return Mono<LoginModel>
     */
    Mono<LoginModel> remove(LoginModel model);

    /**
     * 修改
     *
     * @param model LoginModel
     * @return Mono<LoginModel>
     */
    Mono<LoginModel> update(LoginModel model);

    /**
     * list
     *
     * @param pageSize int
     * @param pageNum  int
     * @param search   String
     * @return Flux<LoginModel>
     */
    Flux<LoginModel> page(int pageSize, int pageNum, String search);

    /**
     * 总数
     *
     * @param search String
     * @return Mono<Long>
     */
    Mono<Long> count(String search);

}
