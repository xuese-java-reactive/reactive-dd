
package mofa.wangzhe.reactive.service;


import mofa.wangzhe.reactive.model.TestModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */
public interface TestService {

    /**
     * 保存
     *
     * @param model TestModel
     * @return Mono<TestModel>
     */
    Mono<TestModel> save(TestModel model);

    /**
     * 删除
     *
     * @param model TestModel
     * @return Mono<TestModel>
     */
    Mono<TestModel> remove(TestModel model);

    /**
     * 修改
     *
     * @param model TestModel
     * @return Mono<TestModel>
     */
    Mono<TestModel> update(TestModel model);

    /**
     * list
     *
     * @param pageSize int
     * @param pageNum  int
     * @param search   String
     * @return Flux<TestModel>
     */
    Flux<TestModel> page(int pageSize, int pageNum, String search);

    /**
     * 总数
     *
     * @param search String
     * @return Mono<Long>
     */
    Mono<Long> count(String search);

}
